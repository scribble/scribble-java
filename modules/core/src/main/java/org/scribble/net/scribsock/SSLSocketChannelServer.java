package org.scribble.net.scribsock;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;

import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

// FIXME:
public class SSLSocketChannelServer extends ScribServerSocket
{
	private ServerSocketChannel ss;
	
	public SSLSocketChannelServer(int port) throws IOException
	{
		super(port);
		throw new RuntimeException("TODO");
		/*this.ss = ServerSocketChannel.open();
		this.ss.socket().bind(new InetSocketAddress(port));*/
	}

	@Override
	public synchronized SocketChannelEndpoint accept(SessionEndpoint<?, ?> se) throws IOException
	{
		return new SocketChannelEndpoint(se, ss.accept());
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
