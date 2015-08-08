package org.scribble.net.session;

import java.io.IOException;
import java.nio.ByteBuffer;

public abstract class BinaryChannelWrapper extends BinaryChannelEndpoint
{
	// Use this in readUnwrappedBytesIntoBuffer (super.getBuffer used for read-but-still-wrapped -- decoded into here by unwrap)
	private final ByteBuffer wrapped = ByteBuffer.allocate(16921);  // FIXME: size  // Use put mode as default
	
	protected BinaryChannelWrapper(BinaryChannelEndpoint c) throws IOException
	{
		super(c);
	}

	public abstract byte[] wrap(byte[] bs) throws IOException;
	public abstract void unwrap() throws IOException;  // Decode from this.wrapped (this.getUnwrapped()) into this.bb
	
	protected abstract void writeWrappedBytes(byte[] bs) throws IOException;
	protected abstract void readWrappedBytesIntoBuffer() throws IOException;  // Read wrapped data into this.wrapped
	
	@Override
	public void writeBytes(byte[] bs) throws IOException
	{
		writeWrappedBytes(wrap(bs));
	}

	@Override
	protected void readBytesIntoBuffer() throws IOException
	{
		readWrappedBytesIntoBuffer();
		unwrap();
	}
	
	protected ByteBuffer getWrapped()
	{
		return this.wrapped;
	}
}
