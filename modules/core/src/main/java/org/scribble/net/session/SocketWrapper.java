package org.scribble.net.session;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

// FIXME: should be IO wrapper: should abstract sockets, shared memory, RMI, AMQP, etc etc
@Deprecated
public class SocketWrapper
{
	protected final Socket sock;
	protected final DataOutputStream dos;
	protected final DataInputStream dis;
	
	public SocketWrapper(Socket s) throws IOException
	{
		this.sock = s;
		this.dos = new DataOutputStream(s.getOutputStream());
		this.dis = new DataInputStream(s.getInputStream());
	}

	public void close() throws IOException
	{
		try
		{
			this.dis.close();
		}
		finally
		{
			try
			{
				this.dos.close();
			}
			finally
			{
				this.sock.close();
			}
		}
	}
	
	public Socket getSocket()
	{
		return this.sock;
	}
}
