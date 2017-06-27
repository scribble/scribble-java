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
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;

public class SSLSocketChannelWrapper extends BinaryChannelWrapper
{
	private SSLEngine engine;

	private ByteBuffer EMPTY = ByteBuffer.allocate(0);

	private ByteBuffer myAppData;
	private ByteBuffer myNetData;

	//private ByteBuffer peerAppData = ByteBuffer.allocate(16916);  // Hacked constant
	//private ByteBuffer peerNetData;
	
	public SSLSocketChannelWrapper()
	{

	}

	private void init(InetSocketAddress addr) throws NoSuchAlgorithmException, KeyManagementException
	{
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, null, null);
		this.engine = sslContext.createSSLEngine(addr.getHostName(), addr.getPort());
	}

	@Override
	public void clientHandshake() throws IOException, KeyManagementException, NoSuchAlgorithmException
	{
		SocketChannel s = getSelectableChannel();
		init((InetSocketAddress) s.getRemoteAddress());
		this.engine.setUseClientMode(true);
		SSLSession session = this.engine.getSession();
		this.myAppData = ByteBuffer.allocate(session.getApplicationBufferSize()); // 16916
		this.myNetData = ByteBuffer.allocate(session.getPacketBufferSize()); // 16921
		//this.peerAppData = ByteBuffer.allocate(16916);  // Hacked constant
		//this.peerNetData = ByteBuffer.allocate(session.getPacketBufferSize());
		doHandshake(s, this.engine, this.myNetData);
	}
	
	@Override
	public void serverHandshake() throws IOException, KeyManagementException, NoSuchAlgorithmException
	{
		SocketChannel s = getSelectableChannel();
		init((InetSocketAddress) s.getRemoteAddress());
		throw new RuntimeException("TODO");
	}
	
	@Override
	public SocketChannel getSelectableChannel()
	{
		return (SocketChannel) super.getSelectableChannel();
	}

	@Override
	protected void writeWrappedBytes(byte[] bs) throws IOException
	{
		getSelectableChannel().write(ByteBuffer.wrap(bs));
	}

	@Override
	protected void readWrappedBytesIntoBuffer() throws IOException
	{
		ByteBuffer bb = getWrapped();
		getSelectableChannel().read(bb);
	}

	@Override
	public byte[] wrap(byte[] bs) throws IOException
	{
		this.myAppData.put(bs);
		this.myAppData.flip();
		this.myNetData.clear();
		while (this.myAppData.hasRemaining())
		{
			SSLEngineResult res = this.engine.wrap(this.myAppData, this.myNetData);
			if (res.getStatus() == SSLEngineResult.Status.OK)
			{
				this.myAppData.compact();
				this.myAppData.flip();
			}
			else
			{
				// Handle other status: BUFFER_OVERFLOW (write some first), BUFFER_UNDERFLOW, CLOSED
				throw new RuntimeException("TODO: " + res.getStatus());
			}
		}
		this.myAppData.compact();  // Should be same as clear here (i.e. empty)
		this.myNetData.flip();
		byte[] res = new byte[this.myNetData.remaining()];
		System.arraycopy(this.myNetData.array(), this.myNetData.position(), res, 0, res.length);
		return res;
	}

	// Decode from this.wrapped (this.getUnwrapped()) into this.bb
	@Override
	public void unwrap() throws IOException  // Decode from this.wrapped (this.getUnwrapped()) into this.bb
	{
		ByteBuffer peerNetData = getWrapped();
		ByteBuffer peerAppData = getBuffer();
		//for (boolean done = false; !done; )
		{
			//s.read(peerNetData);  // Assumes there is something to read (or else blocked)
			peerNetData.flip();
			SSLEngineResult res = this.engine.unwrap(peerNetData, peerAppData);
			peerNetData.compact();
			if (res.getStatus() == SSLEngineResult.Status.OK)
			{
				//done = true;
			}
			else if (res.getStatus() == SSLEngineResult.Status.BUFFER_UNDERFLOW)
			{
				//peerNetData.flip();
				//return;
			}

			else if (res.getStatus() == SSLEngineResult.Status.CLOSED)
			{
				// FIXME: check if closed is OK or not (if not, throw exception)
			}

			else
			{
				// Handle other status: BUFFER_OVERFLOW
				throw new RuntimeException("TODO: " + res.getStatus());
			}
		}
	}

	private void doHandshake(SocketChannel s, SSLEngine engine, ByteBuffer myNetData) throws IOException
	{
		ByteBuffer peerNetData = ByteBuffer.allocate(engine.getSession().getPacketBufferSize());
		ByteBuffer peerAppData = ByteBuffer.allocate(engine.getSession().getApplicationBufferSize());

		engine.beginHandshake(); // Not explicitly needed for initial handshake

		SSLEngineResult.HandshakeStatus hs = engine.getHandshakeStatus();
		while (hs != SSLEngineResult.HandshakeStatus.FINISHED && hs != SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING)
		{
			switch (hs)
			{
				case NEED_UNWRAP:
				{
					for (boolean done = false; !done; )
					{
						peerNetData.flip();
						SSLEngineResult res = engine.unwrap(peerNetData, peerAppData);
						peerNetData.compact();

						hs = res.getHandshakeStatus();
						switch (res.getStatus())
						{
							case OK:
							{
								done = true;
								break;
							}
							case BUFFER_UNDERFLOW:
							{
								if (s.read(peerNetData) < 0)
								{
									throw new RuntimeException("TODO: ");
								}
								break;
							}
							default:
							{
								// Handle other status: BUFFER_OVERFLOW, CLOSED
								throw new RuntimeException("TODO: " + res.getStatus());
							}
						}
					}
					break;
				}
				case NEED_WRAP:
				{
					myNetData.clear();
					SSLEngineResult res = engine.wrap(EMPTY, myNetData);

					hs = res.getHandshakeStatus();
					switch (res.getStatus())
					{
						case OK:
						{
							myNetData.flip();
							while (myNetData.hasRemaining())
							{
								if (s.write(myNetData) < 0)
								{
									throw new RuntimeException("TODO: ");
								}
							}
							myNetData.compact();
							break;
						}
						default:
						{
							// Handle other status: BUFFER_OVERFLOW (write some first to free up space), BUFFER_UNDERFLOW, CLOSED
							throw new RuntimeException("TODO: " + hs);
						}
					}
					break;
				}
				case NEED_TASK:
				{
					engine.getDelegatedTask().run();
					hs = engine.getHandshakeStatus();
					break;
				}
				default:
				{
					// Handle other status: // FINISHED or NOT_HANDSHAKING
					throw new RuntimeException("TODO: " + hs);
				}
			}
		}
	}

	private void doShutdown() throws SSLException, IOException
	{
		SocketChannel s = getSelectableChannel();
		this.engine.closeOutbound();
		myNetData.clear();
		while (!this.engine.isOutboundDone())
		{
			SSLEngineResult res = this.engine.wrap(EMPTY, myNetData);
			if (res.getStatus() != SSLEngineResult.Status.CLOSED)  // FIXME: is this the correct state to check for? or OK? (or both)
			{
				throw new RuntimeException("TODO: " + res.getStatus());
			}
			this.myNetData.flip();
			while (this.myNetData.hasRemaining())
			{
				int num1 = s.write(this.myNetData);
				if (num1 == -1)
				{
					throw new RuntimeException("TODO: ");
				}
				this.myNetData.compact();
				this.myNetData.flip();
			}
		}

		//s1.close();
	}

	@Override
	public synchronized void close() throws IOException
	{
		doShutdown();
		super.close();
	}
}
