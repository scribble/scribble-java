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
package test.test2;

import static test.test2.Test2.Proto1.Proto1.A;
import static test.test2.Test2.Proto1.Proto1.B;

import java.io.IOException;
import java.util.concurrent.Future;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.ScribServerSocket;
import org.scribble.runtime.net.SocketChannelServer;
import org.scribble.runtime.session.MPSTEndpoint;
import org.scribble.runtime.util.Buf;

import test.test2.Test2.Proto1.Proto1;
import test.test2.Test2.Proto1.callbacks.B.Proto1_B;
import test.test2.Test2.Proto1.callbacks.B.Proto1_B_1_Branch;
import test.test2.Test2.Proto1.callbacks.B.states.Proto1_B_1;
import test.test2.Test2.Proto1.ops._1;
import test.test2.Test2.Proto1.ops._2;
import test.test2.Test2.Proto1.roles.A;
import test.test2.Test2.Proto1.roles.B;
import test.test2.Test2.Proto1.statechans.B.EndSocket;
import test.test2.Test2.Proto1.statechans.B.Proto1_B_1_Handler;

public class MyB
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException
	{
		foo3();
		//foo1();
	}

	public static void foo3() throws IOException
	{
		class MyHandler extends Proto1_B_1_Branch<Void>
		{
			@Override
			public void receive(Void data, A peer, _1 op, Integer arg1)
			{
				System.out.println("(B) received 1: " + arg1);
			}

			@Override
			public void receive(Void data, A peer, _2 op, String arg1)
			{
				System.out.println("(B) received 2: " + arg1);
			}
		}
		
		try (ScribServerSocket ss = new SocketChannelServer(8888))
		{
			while (true)
			{
				Proto1 P1 = new Proto1();
				try (Proto1_B<Void> b = new Proto1_B<>(P1, B, new ObjectStreamFormatter(), null))
				{
					b.accept(ss, A);

					b.icallback(Proto1_B_1.id, new MyHandler());

					Future<Void> f = b.run();
					f.get();
					
					System.out.println("(B) end");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	/*public static void foo2() throws IOException, ScribbleRuntimeException
	{
		try (ScribServerSocket ss = new SocketChannelServer(8888))
		{
			while (true)
			{
				Proto1 P1 = new Proto1();
				try (Proto1_B b = new Proto1_B(P1, B, new ObjectStreamFormatter()))
				{
					b.accept(ss, A);
					b.register(Proto1_B_10.id,
							(op, sess) -> { System.out.println("Done 1"); return null; },
							(op, sess) -> { System.out.println("Done 2"); return null; });
					// FIXME: branch handler objects and payloads

					Future<Void> f = b.run();
					f.get();
					
					System.out.println("B done");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}*/

	public static void foo1() throws IOException, ScribbleRuntimeException
	{
		class MyHandler implements Proto1_B_1_Handler
		{
			@Override
			public void receive(EndSocket schan, _1 op, Buf<Integer> arg1) throws ScribbleRuntimeException, IOException, ClassNotFoundException
			{
				System.out.println("Done 1");
			}

			@Override
			public void receive(EndSocket schan, _2 op, Buf<String> arg1) throws ScribbleRuntimeException, IOException, ClassNotFoundException
			{
				System.out.println("Done 2");
			}
		}

		try (ScribServerSocket ss = new SocketChannelServer(8888))
		{
			while (true)
			{
				Proto1 P1 = new Proto1();
				try (MPSTEndpoint<Proto1, B> b = new MPSTEndpoint<>(P1, B, new ObjectStreamFormatter()))
				{
					b.accept(ss, A);

					new test.test2.Test2.Proto1.statechans.B.Proto1_B_1(b).branch(A, new MyHandler());
				}
				catch (Exception e)//ScribbleRuntimeException | IOException | ExecutionException | InterruptedException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
