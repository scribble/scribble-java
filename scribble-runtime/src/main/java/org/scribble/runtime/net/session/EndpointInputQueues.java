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
package org.scribble.runtime.net.session;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


import org.scribble.main.RuntimeScribbleException;
import org.scribble.runtime.net.ScribInterrupt;
import org.scribble.runtime.net.ScribMessage;
import org.scribble.sesstype.name.Role;

@Deprecated
public class EndpointInputQueues
{
	// FIXME: factor out individual role queues (with counts and tickets, and bounded cache size?)
	
	private final Map<Role, List<ScribMessage>> queues = new HashMap<>();
	//private final List<Object> list = new LinkedList<>();
	
	private final Map<Role, Integer> counts = new HashMap<>();  // How many ScribMessages read so far
	private final Map<Role, Integer> tickets = new HashMap<>();  // Index of the next expected ScribMessage

	public EndpointInputQueues()
	{

	}
	
	public synchronized int getTicket(Role peer)
	{
		int next = this.tickets.get(peer) + 1;
		this.tickets.put(peer, next);
		return next;
	}
	
	public synchronized void register(Role peer)
	{
		this.queues.put(peer, new LinkedList<>());
		this.counts.put(peer, 0);
		this.tickets.put(peer, 0);
	}

	public synchronized void enqueue(Role peer, ScribMessage o)
	{
		this.queues.get(peer).add(o);
		//this.list.add(o);
		this.counts.put(peer, this.counts.get(peer) + 1);
		notifyAll();  // FIXME: notify just for each peer
	}
	
	public synchronized void interrupt(Role peer, Throwable t)
	{
		enqueue(peer, new ScribInterrupt(t));
	}
	
	public synchronized boolean isEmpty(Role peer)
	{
		return this.queues.get(peer).isEmpty();
	}

	/*public synchronized Object peek(Role src)
	{
		return this.queues.get(src).get(0);
	}*/
	
	// Default CompletableFuture executed by common forkjoin pool -- so all messages that are received/async'd will eventually be pulled from the queue (no manual GC necessary)
	public synchronized CompletableFuture<ScribMessage> getFuture(Role peer)
	{
		// FIXME: better exception handling (integrate with Future interface?)
		return CompletableFuture.supplyAsync(() ->
				{
					try
					{
						ScribMessage m = dequeue(peer, getTicket(peer));
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

	protected synchronized ScribMessage dequeue(Role peer, int ticket) throws IOException
	{
		try
		{
			while (this.counts.get(peer) < ticket)
			{
				wait();
			}
			List<ScribMessage> queue = this.queues.get(peer);
			while (queue.isEmpty())
			{
				wait();
			}
			ScribMessage m = queue.remove(0);
			//this.list.remove(o);
			return m;
		}
		catch (InterruptedException e)
		{
			throw new IOException(e);
		}
	}

	/*public boolean isEmpty()
	{
		return this.list.isEmpty();
	}*/

	/*public Object dequeue()
	{
		Object o = this.list.remove(0);
		for (List<Object> os : this.global.values())
		{
			if (os.contains(o))
			{
				os.remove(o);
				break;
			}
		}
		return o;
	}*/
	
	/*// Synchronized against usage in SocketEndpoint
	public synchronized Object readObject() throws IOException
	{
		synchronized (this)
		{
			while (isEmpty())
			{
				try
				{
					this.wait();
				}
				catch (InterruptedException e)
				{
					throw new IOException(e);
				}
			}
			return dequeue();
		}
	}*/
	
	/*public ScribbleInterrupt checkForInterrupt()
	{
		synchronized (this)
		{
			for (List<Object> queue : this.global.values()) 
			{
				if (!queue.isEmpty() && (queue.get(0) instanceof ScribbleInterrupt))
				{
					return (ScribbleInterrupt) queue.remove(0);
				}
			}
		}
		return null;
	}*/
}
