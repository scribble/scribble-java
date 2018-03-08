/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.runtime.net;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.spi.AbstractSelectableChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.scribble.main.RuntimeScribbleException;
import org.scribble.runtime.message.ScribInterrupt;
import org.scribble.runtime.message.ScribMessage;
import org.scribble.runtime.session.SessionEndpoint;

public abstract class BinaryChannelEndpoint
{
	//protected MPSTEndpoint<?, ?> se;
	protected SessionEndpoint<?, ?> se;
	
	//protected BinaryChannelEndpoint parent;
	private AbstractSelectableChannel c;
	private ByteBuffer bb;

	//private final SocketChannel;
	protected final List<ScribMessage> msgs = new LinkedList<>();
	
	private boolean isClosed = false;

	private int count = 0;  // How many ScribMessages read so far  // volatile?
	private int ticket = 0;  // Index of the next expected ScribMessage
	
	private final List<CompletableFuture<ScribMessage>> pending = new LinkedList<>();

	// Server side
	//protected BinaryChannelEndpoint(MPSTEndpoint<?, ?> se, AbstractSelectableChannel c) throws IOException
	protected BinaryChannelEndpoint(SessionEndpoint<?, ?> se, AbstractSelectableChannel c) throws IOException
	{
		this.bb = ByteBuffer.allocate(16921);  // FIXME: size  // Use put mode as default
		init(se, c);
	}

	// Client side
	protected BinaryChannelEndpoint()
	{
		this.bb = ByteBuffer.allocate(16921);  // FIXME: size  // Use put mode as default
	}
	
	//public abstract void initClient(MPSTEndpoint<?, ?> se, String host, int port) throws IOException;
	public abstract void initClient(SessionEndpoint<?, ?> se, String host, int port) throws IOException;
	
	//protected void init(MPSTEndpoint<?, ?> se, AbstractSelectableChannel c) throws IOException
	protected void init(SessionEndpoint<?, ?> se, AbstractSelectableChannel c) throws IOException
	{
		this.se = se;
		this.c = c;
		this.c.configureBlocking(false);
	}

	// Pre: selector is paused
	//protected BinaryChannelEndpoint(BinaryChannelEndpoint c)
	public void wrapChannel(BinaryChannelEndpoint c) throws IOException
	{
		this.se = c.se;
		//this.msgs.addAll(c.msgs);  // Guaranteed to be empty/0 for reconnect?
		//this.count = c.count;
		//this.ticket = c.ticket;
		//this.parent = c;
		this.c = c.c;
		this.bb = c.bb;
		
		//FIXME: complete all pending futures on parent chan -- no: not enough by itself, that is just reading the already-deserialized cache
		//FIXME: pull all pending data out of parent chan (due to selector not handling it yet -- in send states, we just need to clear all expected messages up to this point)
		//  -- so that wrapper handshake is starting clean
		// --- futures must be completed before here, since selector is paused
	}
	
	public AbstractSelectableChannel getSelectableChannel()  // For asynchrony (via nio Selector) -- maybe implement/extend instead
	{
		return this.c;
	}
	
	public void write(ScribMessage m) throws IOException
	{
		writeBytes(this.se.smf.toBytes(m));
	}

	// Default CompletableFuture executed by common forkjoin pool -- so all messages that are received/async'd will eventually be pulled from the queue (no manual GC necessary)
	public synchronized CompletableFuture<ScribMessage> getFuture()
	{
		// FIXME: better exception handling (integrate with Future interface?)
		final int ticket = getTicket();
		CompletableFuture<ScribMessage> fut = CompletableFuture.supplyAsync(() ->
				{
					try
					{
						ScribMessage m = read(ticket);
						if (m instanceof ScribInterrupt)  // FIXME: hacked in
						{
							throw new RuntimeScribbleException((Throwable) ((ScribInterrupt) m).payload[0]);
						}
						return m;
					}
					catch(IOException e)
					{
						throw new RuntimeScribbleException(e);
					}
					finally
					{
						this.pending.remove(0);  // Safe?
					}
				});
		synchronized (this.pending)
		{
			this.pending.add(fut);
		}
		return fut;
	}
	
	public void sync() throws IOException  // Hacky
	{
		try
		{
			synchronized (this.pending)
			{
				if (!this.pending.isEmpty())
				{
					this.pending.get(this.pending.size() - 1).get();
				}
			}
		}
		catch (InterruptedException | ExecutionException e)
		{
			throw new IOException(e);
		}
	}
	
	private synchronized ScribMessage read(int ticket) throws IOException
	{
		try
		{
			while (this.count < ticket && !this.isClosed)  // "Chain" futures directly? i.e. each future syncs on predecessor?
			{
				wait();
			}
			while (this.msgs.isEmpty() && !this.isClosed)
			{
				wait();
			}
			if (this.isClosed)
			{
				throw new IOException("Channel closed");
			}
			this.count++;
			ScribMessage m = this.msgs.remove(0);
			notifyAll();  // A later future might have gone first and blocked, so wake up (with the message already enqueued, so no notify coming from there)  // finally?
			return m;
		}
		catch (InterruptedException e)
		{
			throw new IOException(e);
		}
	}
	
	protected synchronized void enqueue(ScribMessage m)
	{
		this.msgs.add(m);
		//this.count++;
		notifyAll();
	}

	public abstract void writeBytes(byte[] bs) throws IOException;
	protected abstract void readBytesIntoBuffer() throws IOException;  // synchronized (against read)  // bytes ready for reading: try to deserialize and then enqueue, or else cache for later
	
	public synchronized void readAndEnqueueMessages() throws ClassNotFoundException, IOException  // Here for synchronisation
	{
		readBytesIntoBuffer();
		ScribMessage m;
		while ((m = this.se.smf.fromBytes(this.bb)) != null)
		{
			enqueue(m);
		}
	}
	
	public synchronized void close() throws IOException
	{
		this.isClosed = true;
		notifyAll();
	}
	
	public synchronized int getTicket()
	{
		//return ++this.ticket;
		return this.ticket++;
	}
	
	// post: bb:put
	public ByteBuffer getBuffer()
	{
		return this.bb;
	}
}
