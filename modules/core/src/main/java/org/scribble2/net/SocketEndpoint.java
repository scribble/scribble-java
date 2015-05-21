package org.scribble2.net;

import java.io.IOException;

// Read/write Objects on the binary connection to an endpoint in the session
public class SocketEndpoint
{
	/*// Used by SelfSocketEndpoint
	protected final Role src;
	//protected final SessionInputQueue inputq;*/

	private final SocketWrapper sw;
	//private final ReceiverThread recthread;

	//public SocketEndpoint(Role src, SessionInputQueue inputq, SocketWrapper sw) //throws IOException
	public SocketEndpoint(SocketWrapper sw) //throws IOException
	{
		/*this.src = src;
		this.inputq = inputq;*/
		this.sw = sw;

		/*inputq.register(src);
		this.recthread = new ReceiverThread(sw.ois, inputq, src);
		this.recthread.start();*/
	}

	/*// For SelfSocketEndpoint only
	protected SocketEndpoint(Role src, SessionInputQueue inputq)
	{
		this.src = src;
		this.inputq = inputq;
		this.sw = null;
		this.recthread = null;
	}*/

	public void writeObject(Object o) throws IOException
	{
		this.sw.oos.writeObject(o);
	}
	
	public Object readObject() throws IOException, ClassNotFoundException
	{
		//return this.inputq.readObject(this.src);
		return this.sw.ois.readObject();
	}

	public void flush() throws IOException
	{
		this.sw.oos.flush();
	}

	public void writeObjectAndFlush(Object o) throws IOException
	{
		writeObject(o);
		flush();
	}

	public void close() throws IOException
	{
		this.sw.close();
	}

	@Override
	public String toString()
	{
		return this.sw.sock.getInetAddress().toString() + ":" + this.sw.sock.getPort();
	}
}

// Could move to SessionInputQueue
/*class ReceiverThread extends Thread
{
	private final ObjectInputStream ois;
	private final SessionInputQueue inputq;
	private final Role src;
	private Exception fail;

	public ReceiverThread(ObjectInputStream ois, SessionInputQueue inputq, Role src)
	{
		this.ois = ois;
		this.inputq = inputq;
		this.src = src;
	}

	public void run()
	{
		try
		{
			while (true)
			{
				this.inputq.enqueue(this.src, ois.readObject());
			}
		}
		catch (ClassNotFoundException | IOException e)
		{
			this.fail = e;
		}
		finally
		{
			try
			{
				ois.close();  // Maybe just rely on main SocketEndpoint.close
			}
			catch (IOException e)
			{
				if (this.fail == null)
				{
					this.fail = e;
				}
			}
			finally
			{
				if (this.fail != null)
				{
					this.inputq.signalException(this.src, this.fail);
				}
				this.inputq.signalReceiverThreadTermination(this.src);
			}
		}
	}
}*/
