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
