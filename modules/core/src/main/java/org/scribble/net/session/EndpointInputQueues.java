package org.scribble.net.session;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.scribble.net.ScribMessage;
import org.scribble.sesstype.name.Role;

public class EndpointInputQueues
{
	private final Map<Role, List<ScribMessage>> queues = new HashMap<>();
	//private final List<Object> list = new LinkedList<>();
	
	private final Map<Role, Integer> counts = new HashMap<>();
	private final Map<Role, Integer> tickets = new HashMap<>();

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
		this.notifyAll();  // FIXME: notify just for each peer
	}
	
	public synchronized boolean isEmpty(Role peer)
	{
		return this.queues.get(peer).isEmpty();
	}

	/*public synchronized Object peek(Role src)
	{
		return this.queues.get(src).get(0);
	}*/
	
	public synchronized CompletableFuture<ScribMessage> getFuture(Role peer)
	{
		// FIXME: cast
		// FIXME: exception handling
		return CompletableFuture.supplyAsync(() -> { try { return dequeue(peer, getTicket(peer)); } catch(IOException e) { throw new RuntimeException(e); } });
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
