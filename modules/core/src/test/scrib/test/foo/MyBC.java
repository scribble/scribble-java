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
package test.foo;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.MPSTEndpoint;

import test.foo.Foo.Foo.Foo;
import test.foo.Foo.Foo.channels.B.Foo_B_1;
import test.foo.Foo.Foo.channels.B.Foo_B_1_Cases;
import test.foo.Foo.Foo.channels.C.Foo_C_1;
import test.foo.Foo.Foo.channels.C.Foo_C_1_Cases;
import test.foo.Foo.Foo.roles.B;
import test.foo.Foo.Foo.roles.C;

public class MyBC
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException, InterruptedException
	{
		try (ScribServerSocket ss_B = new SocketChannelServer(8888);
				 ScribServerSocket ss_C = new SocketChannelServer(9999))
		{
			//Buff<String> s = new Buff<>();

			while (true)
			{
				Foo foo = new Foo();
				
				Thread B = new Thread()
				{
					public void run()
					{
						try (MPSTEndpoint<Foo, B> se_B = new MPSTEndpoint<>(foo, Foo.B, new ObjectStreamFormatter()))
						{
							se_B.accept(ss_B, Foo.A);
							Foo_B_1 s1_B = new Foo_B_1(se_B);

							Foo_B_1_Cases s1cases_B = s1_B.branch(Foo.A);
							switch (s1cases_B.op)
							{
								case _1:
								{
									s1cases_B.receive(Foo._1);
									System.out.println("B first!");
									break;
								}
								case _2:
								{
									s1cases_B.receive(Foo._2);
									break;
								}
							}
						}
						catch (ScribbleRuntimeException | IOException | ClassNotFoundException e)
						{
							e.printStackTrace();
						}
					}
				};

				Thread C = new Thread()
				{
					public void run()
					{
						try (MPSTEndpoint<Foo, C> se_C = new MPSTEndpoint<>(foo, Foo.C, new ObjectStreamFormatter()))
						{
							se_C.accept(ss_C, Foo.A);

							Foo_C_1 s1_C = new Foo_C_1(se_C);
							Foo_C_1_Cases s1cases_C = s1_C.branch(Foo.A);
							switch (s1cases_C.op)
							{
								case _1:
								{
									s1cases_C.receive(Foo._1);
									System.out.println("C first!");
									break;
								}
								case _2:
								{
									s1cases_C.receive(Foo._2);
									break;
								}
							}
						}
						catch (ScribbleRuntimeException | IOException | ClassNotFoundException e)
						{
							e.printStackTrace();
						}
					}
				};
				
				B.start();
				C.start();
				B.join();
				C.join();
			}
		}
	}
}
