package org.scribble.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketWrapper
{
	public final Socket sock;
	public final DataOutputStream dos;
	public final DataInputStream dis;

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
}
