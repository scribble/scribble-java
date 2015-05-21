package org.scribble2.net;

import org.scribble2.net.session.ScribbleRuntimeException;
import org.scribble2.net.session.SessionEndpoint;

// LinearSocket
public abstract class ScribSocket implements AutoCloseable
{
	protected SessionEndpoint ep;
	
	private boolean used = false;
	
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

	@Override
	public void close() throws ScribbleRuntimeException
	{
		//this.sessep.close();
		if (!this.used)
		{
			throw new ScribbleRuntimeException("Socket not used: ");
		}
	}
}
