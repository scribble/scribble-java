package org.scribble.net.session;

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
	
	public SSLSocketChannelWrapper(SocketChannelEndpoint s) throws IOException, NoSuchAlgorithmException, KeyManagementException
	{
		super(s);

		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, null, null);
		InetSocketAddress addr = ((InetSocketAddress) s.getSelectableChannel().getRemoteAddress());
		SSLEngine engine = sslContext.createSSLEngine(addr.getHostName(), addr.getPort());
		engine.setUseClientMode(true);
		SSLSession session = engine.getSession();

		this.myAppData = ByteBuffer.allocate(session.getApplicationBufferSize()); // 16916
		this.myNetData = ByteBuffer.allocate(session.getPacketBufferSize()); // 16921

		//this.peerAppData = ByteBuffer.allocate(16916);  // Hacked constant
		//this.peerNetData = ByteBuffer.allocate(session.getPacketBufferSize());
		
		doHandshake(s.getSelectableChannel(), engine, myNetData);
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
		myAppData.put(bs);
		myAppData.flip();
		myNetData.clear();
		while (myAppData.hasRemaining())
		{
			SSLEngineResult res = engine.wrap(myAppData, myNetData);
			if (res.getStatus() == SSLEngineResult.Status.OK)
			{
				myAppData.compact();
				myAppData.flip();
			}
			else
			{
				// Handle other status: BUFFER_OVERFLOW (write some first), BUFFER_UNDERFLOW, CLOSED
				throw new RuntimeException("TODO: " + res.getStatus());
			}
		}
		myAppData.compact();  // Should be same as clear here (i.e. empty)
		myNetData.flip();
		byte[] res = new byte[myNetData.remaining()];
		System.arraycopy(myNetData.array(), myNetData.position(), res, 0, res.length);
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
			else
			{
				// Handle other status: BUFFER_OVERFLOW, CLOSED
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

	private void doShutdown(SSLEngine engine, SocketChannel s, ByteBuffer myNetData) throws SSLException, IOException
	{
		engine.closeOutbound();
		myNetData.clear();
		while (!engine.isOutboundDone())
		{
			SSLEngineResult res = engine.wrap(EMPTY, myNetData);
			if (res.getStatus() != SSLEngineResult.Status.CLOSED)  // FIXME: is this the correct state to check for? or OK? (or both)
			{
				throw new RuntimeException("TODO: " + res.getStatus());
			}
			myNetData.flip();
			while (myNetData.hasRemaining())
			{
				int num1 = s.write(myNetData);
				if (num1 == -1)
				{
					throw new RuntimeException("TODO: ");
				}
				myNetData.compact();
				myNetData.flip();
			}
		}

		//s1.close();
	}

	@Override
	public synchronized void close() throws IOException
	{
		doShutdown(this.engine, getSelectableChannel(), this.myNetData);
		super.close();
	}

	@Override
	public void initClient(SessionEndpoint se, String host, int port) throws IOException
	{
		throw new RuntimeException("TODO");
	}
}
