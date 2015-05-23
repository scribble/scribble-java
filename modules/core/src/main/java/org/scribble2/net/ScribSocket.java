package org.scribble2.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.scribble2.net.session.SessionEndpoint;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleRuntimeException;


// LinearSocket
public abstract class ScribSocket implements AutoCloseable
{
	protected SessionEndpoint ep;
	
	private boolean used = false;
	
	protected ScribSocket(SessionEndpoint ep)
	{
		this.ep = ep;
	}

	public void connect(Role role, String host, int port) throws ScribbleRuntimeException, UnknownHostException, IOException
	{
		Socket s = new Socket(host, port);
		this.ep.register(role, new SocketWrapper(s));
	}

	public void accept(ScribServerSocket ss, Role role) throws IOException, ScribbleRuntimeException
	{
		this.ep.register(role, ss.accept());
	}
	
	protected boolean isUsed()
	{
		return this.used;
	}
	
	protected void use() throws ScribbleRuntimeException
	{
		if (this.used)
		{
			throw new ScribbleRuntimeException("Socket already used: ");
		}
		this.used = true;
	}
	
	/*public void connect(Role role, String host, int port) throws UnknownHostException, IOException
	{
		Socket s = new Socket(host, port);
		this.ep.register(role, new SocketWrapper(s, true));
	}

	public ScribServerSocket open(int port) throws IOException
	{
		return new ScribServerSocket(port);
	}

	public void accept(ScribServerSocket ss, Role role) throws IOException
	{
		this.ep.register(role, ss.accept());
	}
	
	protected void send(Role role, Object o) throws IOException
	{
		this.ep.getSocketEndpoint(role).writeObjectAndFlush(o);
	}

	protected Object receive(Role role) throws ClassNotFoundException, IOException
	{
		return this.ep.getSocketEndpoint(role).readObject();
	}*/

	// Only triggered by autoclose or explicit close
	@Override
	public void close() throws ScribbleRuntimeException
	{
		System.out.println("c1: " + this.ep.self + ", " + this.getClass());

		//this.sessep.close();
		if (!this.used)
		//if (!this.ep.isCompleted())
		{
			System.out.println("c2: ");
			
			//throw new ScribbleRuntimeException("Socket not used: ");
			this.ep.close();
		}
	}
	
	/*protected void setCompleted()
	{
		this.ep.setCompleted();
	}*/
}
