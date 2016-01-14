package org.scribble.net.scribsock;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.session.BinaryChannelEndpoint;
import org.scribble.net.session.SessionEndpoint;

public abstract class ScribServerSocket implements AutoCloseable
{
	public final int port;

	private boolean reg = false;
	
	public ScribServerSocket(int port) throws IOException
	{
		this.port = port;
	}
	
	public abstract BinaryChannelEndpoint accept(SessionEndpoint<?, ?> se) throws IOException;  // synchronize
	
	public synchronized void bind() throws ScribbleRuntimeException
	{
		if (this.reg)
		{
			throw new ScribbleRuntimeException("Server socket already registered.");
		}
		this.reg = true;
	}
	
	public synchronized void unbind()
	{
		this.reg = false;
	}

	@Override
	public abstract void close();
}
