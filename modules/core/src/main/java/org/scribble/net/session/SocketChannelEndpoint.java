package org.scribble.net.session;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelEndpoint extends BinaryChannelEndpoint
{
	//private final SocketChannel s;
	
	//private final ByteBuffer bb = ByteBuffer.allocate(16921);  // FIXME: size
	
	// Server side
	public SocketChannelEndpoint(SessionEndpoint<?, ?> se, SocketChannel s) throws IOException
	{
		super(se, s);
	}

	// Client side
	public SocketChannelEndpoint()
	{
		
	}
	
	@Override
	public void initClient(SessionEndpoint<?, ?> se, String host, int port) throws IOException
	{
		SocketChannel s = SocketChannel.open(new InetSocketAddress(host, port));
		super.init(se, s);
	}

	@Override
	public SocketChannel getSelectableChannel()
	{
		return (SocketChannel) super.getSelectableChannel();
	}
	
	public void writeBytes(byte[] bs) throws IOException
	{
		getSelectableChannel().write(ByteBuffer.wrap(bs));  // Currently does not depend on SocketChannel
		// flush not supported -- async: manually check if written yet if needed
	}

	@Override
	public synchronized void readBytesIntoBuffer() throws IOException
	{
		/*getSelectableChannel().read(this.bb);  // Pre: bb:put
				// FIXME: what if bb is full?
		ScribMessage m = this.se.smf.fromBytes(this.bb);  // Post: bb:put
		if (m != null)
		{
			enqueue(m);
		}*/
		ByteBuffer bb = (ByteBuffer) getBuffer();
		getSelectableChannel().read(bb);  // Currently does not depend on SocketChannel
		//bb.compact();  // Post: bb:put
	}
	
	@Override
	public void close()
	{
		try
		{
			super.close();
			getSelectableChannel().close();
		}
		catch (IOException e)
		{
			// FIXME: (BinaryChannelEndpoint read will throw exception)
			e.printStackTrace();
		}
	}
}
