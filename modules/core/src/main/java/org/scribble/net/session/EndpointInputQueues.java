package org.scribble.net.session;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.scribble.net.ScribMessage;
import org.scribble.sesstype.name.Role;

public class EndpointInputQueues
{
	private final Map<Role, List<ScribMessage>> queues = new HashMap<>();
	//private final List<Object> list = new LinkedList<>();

	public EndpointInputQueues()
	{

	}
	
	public synchronized void register(Role peer)
	{
		this.queues.put(peer, new LinkedList<>());
	}

	public synchronized void enqueue(Role src, ScribMessage o)
	{
		this.queues.get(src).add(o);
		//this.list.add(o);
		this.notifyAll();  // FIXME: notify just for each peer
	}
	
	public synchronized boolean isEmpty(Role src)
	{
		return this.queues.get(src).isEmpty();
	}

	/*public synchronized Object peek(Role src)
	{
		return this.queues.get(src).get(0);
	}*/

	public synchronized ScribMessage dequeue(Role src) throws IOException
	{
		List<ScribMessage> queue = this.queues.get(src);
		while (queue.isEmpty())
		{
			try
			{
				wait();
			}
			catch (InterruptedException e)
			{
				throw new IOException(e);
			}
		}
		ScribMessage m = queue.remove(0);
		//this.list.remove(o);
		return m;
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
