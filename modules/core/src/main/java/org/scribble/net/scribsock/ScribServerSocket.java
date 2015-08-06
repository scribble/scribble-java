package org.scribble.net.scribsock;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.scribble.main.ScribbleRuntimeException;

public class ScribServerSocket implements AutoCloseable
{
	private boolean reg = false;
	//private int port;
	//private ServerSocket ss;
	private ServerSocketChannel ss;
	
	public ScribServerSocket(int port) throws IOException
	{
		//this.port = port;
		//this.ss = new ServerSocket(port);
		this.ss = ServerSocketChannel.open();
		this.ss.socket().bind(new InetSocketAddress(port));
	}
	
	public ServerSocketChannel getServerSocketChannel()
	{
		return this.ss;
	}

	protected SocketChannel accept() throws IOException
	{
		//return new SocketWrapper(this.ss.accept());
		return ss.accept();
	}
	
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
	public void close()
	{
		try
		{
			this.ss.close();
		}
		catch (IOException e)
		{
			// FIXME
			e.printStackTrace();
		}
	}
}
