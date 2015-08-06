package org.scribble.net.session;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import org.scribble.net.ScribMessage;

public class SocketChannelEndpoint extends BinaryChannelEndpoint
{
	private final SocketChannel s;
	
	private final ByteBuffer bb = ByteBuffer.allocate(1024);  // FIXME: size

	public SocketChannelEndpoint(SessionEndpoint ep, SocketChannel s)
	{
		super(ep);
		this.s = s;
	}
	
	public void writeBytes(byte[] bs) throws IOException
	{
		this.s.write(ByteBuffer.wrap(bs));
		// flush not supported -- async: manually check if written yet if needed
	}

	@Override
	public synchronized void readBytes() throws IOException, ClassNotFoundException
	{
		this.s.read(this.bb);  // Pre: bb in write mode
				// FIXME: what if bb is full?
		ScribMessage m = this.se.smf.fromBytes(this.bb);  // Post: bb in write mode
		if (m != null)
		{
			enqueue(m);
		}
	}
	
	@Override
	public void close()
	{
		try
		{
			super.close();
			this.s.close();
		}
		catch (IOException e)
		{
			// FIXME: (BinaryChannelEndpoint read will throw exception)
		}
	}
}
