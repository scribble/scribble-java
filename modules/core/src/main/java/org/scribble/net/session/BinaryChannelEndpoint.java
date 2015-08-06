package org.scribble.net.session;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.scribble.main.RuntimeScribbleException;
import org.scribble.net.ScribInterrupt;
import org.scribble.net.ScribMessage;

public abstract class BinaryChannelEndpoint
{
	protected final SessionEndpoint se;

	//private final SocketChannel;
	protected final List<ScribMessage> msgs = new LinkedList<>();
	
	private boolean isClosed = false;

	private int count = 0;  // How many ScribMessages read so far
	private int ticket = 0;  // Index of the next expected ScribMessage
	
	public synchronized int getTicket()
	{
		return ++this.ticket;
	}

	public BinaryChannelEndpoint(SessionEndpoint se)
	{
		this.se = se;
	}

	public void write(ScribMessage m) throws IOException
	{
		writeBytes(this.se.smf.toBytes(m));
	}

	// Default CompletableFuture executed by common forkjoin pool -- so all messages that are received/async'd will eventually be pulled from the queue (no manual GC necessary)
	public synchronized CompletableFuture<ScribMessage> getFuture()
	{
		// FIXME: better exception handling (integrate with Future interface?)
		return CompletableFuture.supplyAsync(() ->
				{
					try
					{
						ScribMessage m = read(getTicket());
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
				});
	}
	
	private synchronized ScribMessage read(int ticket) throws IOException
	{
		try
		{
			while (this.count < ticket && !this.isClosed)
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
			return this.msgs.remove(0);
		}
		catch (InterruptedException e)
		{
			throw new IOException(e);
		}
	}
	
	protected synchronized void enqueue(ScribMessage m)
	{
		this.msgs.add(m);
		this.count++;
		notify();
	}

	public abstract void writeBytes(byte[] bs) throws IOException;
	public abstract void readBytes() throws IOException, ClassNotFoundException;  // synchronized (against read)  // bytes ready for reading: try to deserialize and then enqueue, or else cache for later
	
	public synchronized void close() throws IOException
	{
		this.isClosed = true;
		notify();
	}
}
