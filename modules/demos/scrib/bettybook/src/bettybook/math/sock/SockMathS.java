package bettybook.math.sock;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SockMathS
{
	public static void main(String[] args) throws Exception
	{
		try (ServerSocket ss = new ServerSocket(8888))
		{
			while (true)
			{
				try (Socket s = ss.accept())
				{
					try (ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
							 ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream()))
					{
						Loop: while (true)
						{
							Object msg = ois.readObject();
							if (msg instanceof Val)
							{
								int x = ((Val) msg).val;
								msg = ois.readObject();
								if (msg instanceof Add)
								{
									int y = ((Add) msg).val;
									oos.writeObject(new Sum(x + y));
								}
								else if (msg instanceof Mult)
								{
									int y = ((Mult) msg).val;
									oos.writeObject(new Prod(x * y));
								}
								else
								{
									throw new Exception("Bad message: " + msg.getClass());
								}
								oos.flush();
							}
							else if (msg instanceof Bye)
							{
								break Loop;
							}
							else
							{
								throw new Exception("Bad message: " + msg.getClass());
							}
						}
					}
				}
				catch (Exception x)
				{
					x.printStackTrace();
				}
			}
		}
	}
}
