/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.runtime.net.session;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public abstract class BinaryChannelWrapper extends BinaryChannelEndpoint
{
	// Use this in readUnwrappedBytesIntoBuffer (super.getBuffer used for read-but-still-wrapped -- decoded into here by unwrap)
	private final ByteBuffer wrapped = ByteBuffer.allocate(16921);  // FIXME: size  // Use put mode as default
	
	protected BinaryChannelWrapper()
	{

	}

	@Override
	//public void initClient(MPSTEndpoint<?, ?> se, String host, int port) throws IOException
	public void initClient(SessionEndpoint<?, ?> se, String host, int port) throws IOException
	{
		throw new RuntimeException("Shouldn't get in here: ");
	}
	
	public abstract void clientHandshake() throws IOException, KeyManagementException, NoSuchAlgorithmException; // FIXME: name
	public abstract void serverHandshake() throws IOException, KeyManagementException, NoSuchAlgorithmException; // FIXME: name

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
