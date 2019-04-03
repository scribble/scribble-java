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
package test.test8;

import static test.test8.Test8.Proto1.Proto1.A;
import static test.test8.Test8.Proto1.Proto1.B;

import java.io.IOException;
import java.util.concurrent.Future;

import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.ScribServerSocket;
import org.scribble.runtime.net.SocketChannelServer;

import test.test8.Test8.Proto1.Proto1;
import test.test8.Test8.Proto1.callbacks.B.Proto1_B;
import test.test8.Test8.Proto1.callbacks.B.Proto1_B_1_Branch;
import test.test8.Test8.Proto1.callbacks.B.states.Proto1_B_1;
import test.test8.Test8.Proto1.callbacks.B.states.Proto1_B_2;
import test.test8.Test8.Proto1.ops._1;
import test.test8.Test8.Proto1.ops._3;
import test.test8.Test8.Proto1.ops._4;
import test.test8.Test8.Proto1.roles.A;

public class MyB
{
	public static void main(String[] args) throws IOException
	{
		foo();
	}

	public static void foo() throws IOException
	{
		class MyHandler extends Proto1_B_1_Branch<Void>
		{
			@Override
			public void receive(Void data, A peer, _1 op, Integer arg1)
			{
				System.out.println("(B) received 1: " + arg1);
			}

			@Override
			public void receive(Void data, A peer, _3 op, Integer arg1)
			{
				System.out.println("(B) received 3: " + arg1);
			}

			@Override
			public void receive(Void data, A peer, _4 op, String arg1)
			{
				System.out.println("(B) received 4: " + arg1);
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

					MyHandler h = new MyHandler();
					b.icallback(Proto1_B_1.id, h);
					//b.callback(Proto1_B_2.id, h);
					b.icallback(Proto1_B_2.id, h);

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
}
