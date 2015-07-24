package org.scribble.net.scribsock;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketWrapper;
import org.scribble.sesstype.name.Role;

// Establishing transport connections handled in here and wrapped up in SocketWrapper
public abstract class InitSocket extends LinearSocket implements AutoCloseable
{
	protected InitSocket(SessionEndpoint ep)
	{
		super(ep);
	}

	public void connect(Role role, String host, int port) throws ScribbleRuntimeException, UnknownHostException, IOException
	{
		// Can connect unlimited, as long as not already used via init
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
