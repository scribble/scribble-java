package org.scribble2.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.scribble2.net.session.SessionEndpoint;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleRuntimeException;

public abstract class InitSocket extends ScribSocket implements AutoCloseable
{
	protected InitSocket(SessionEndpoint ep)
	{
		super(ep);
	}

	public void connect(Role role, String host, int port) throws ScribbleRuntimeException, UnknownHostException, IOException
	{
		if (isUsed())
		{
			throw new ScribbleRuntimeException("Socket already initialised: " + this.getClass());
		}
		Socket s = new Socket(host, port);
		this.ep.register(role, new SocketWrapper(s));
	}

	public void accept(ScribServerSocket ss, Role role) throws IOException, ScribbleRuntimeException
	{
		if (isUsed())
		{
			throw new ScribbleRuntimeException("Socket already initialised: " + this.getClass());
		}
		this.ep.register(role, ss.accept());
	}
	
	@Override
	public void close() throws ScribbleRuntimeException
	{
		if (!this.ep.isCompleted())  // Subsumes use -- must be used for sess to be completed
		{
			this.ep.close();
			throw new ScribbleRuntimeException("Session not completed: " + this.ep.self);
		}
	}
}
