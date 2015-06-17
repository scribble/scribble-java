package org.scribble.net;

import java.io.IOException;
import java.net.ServerSocket;

public class ScribServerSocket implements AutoCloseable
{
	//private int port;
	private ServerSocket ss;
	
	public ScribServerSocket(int port) throws IOException
	{
		//this.port = port;
		this.ss = new ServerSocket(port);
	}

	protected SocketWrapper accept() throws IOException
	{
		return new SocketWrapper(this.ss.accept());
	}

	@Override
	public void close()
	{
		try
		{
			this.ss.close();
		}
		catch (IOException e)
		{
			// FIXME:
		}
	}
}
