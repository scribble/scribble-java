package org.scribble.net.scribsock;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

public class SocketChannelServer extends ScribServerSocket
{
	private ServerSocketChannel ss;
	
	public SocketChannelServer(int port) throws IOException
	{
		super(port);
		this.ss = ServerSocketChannel.open();
		this.ss.socket().bind(new InetSocketAddress(port));
	}

	@Override
	//public synchronized SocketChannelEndpoint accept(MPSTEndpoint<?, ?> se) throws IOException
	public synchronized SocketChannelEndpoint accept(SessionEndpoint<?, ?> se) throws IOException
	{
		return new SocketChannelEndpoint(se, this.ss.accept());
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
