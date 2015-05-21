package org.scribble2.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SocketWrapper
{
	public final Socket sock;
	public final ObjectOutputStream oos;
	public final ObjectInputStream ois;

	public SocketWrapper(Socket s, boolean client) throws IOException
	{
		this.sock = s;
		if (client)
		{
			this.oos = new ObjectOutputStream(s.getOutputStream());
			this.ois = new ObjectInputStream(s.getInputStream());
		}
		else
		{
			this.ois = new ObjectInputStream(s.getInputStream());
			this.oos = new ObjectOutputStream(s.getOutputStream());
		}
	}

	public void close() throws IOException
	{
		try
		{
			this.ois.close();  // FIXME: may already be closed by receiver thread
		}
		finally
		{
			try
			{
				this.oos.close();
			}
			finally
			{
				this.sock.close();
			}
		}
	}
}
