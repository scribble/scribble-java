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
package test.test6;

import static test.test6.Test6.Proto1.Proto1.A;
import static test.test6.Test6.Proto1.Proto1.B;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.function.Function;

import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.ScribServerSocket;
import org.scribble.runtime.net.SocketChannelServer;

import test.test6.Test6.Proto1.Proto1;
import test.test6.Test6.Proto1.callbacks.B.Proto1_B;
import test.test6.Test6.Proto1.callbacks.B.Proto1_B_3_Branch;
import test.test6.Test6.Proto1.callbacks.B.states.Proto1_B_1;
import test.test6.Test6.Proto1.callbacks.B.states.Proto1_B_2;
import test.test6.Test6.Proto1.callbacks.B.states.Proto1_B_3;
import test.test6.Test6.Proto1.callbacks.B.states.Proto1_B_4;

public class MyB
{
	public static void main(String[] args) throws IOException
	{
		class MyHandler extends Proto1_B_3_Branch<String[]>
		{
			@Override
			public void receive(String[] data, test.test6.Test6.Proto1.roles.A peer, test.test6.Test6.Proto1.ops._1 op, Integer arg1)
			{
				System.out.println("(B) received 1: " + arg1 + ", " + (data[0] += "a"));
				
			}
		}
		
		try (ScribServerSocket ss = new SocketChannelServer(8888))
		{
			while (true)
			{
				Proto1 P1 = new Proto1();
				String[] data = new String[] { "" };
				try (Proto1_B<String[]> b = new Proto1_B<>(P1, B, new ObjectStreamFormatter(), data))
				{
					b.accept(ss, A);

					MyHandler h1 = new MyHandler();
					Function<String[], Proto1_B_4.Message> h2 = x -> new Proto1_B_4.A._2(x[0] += "a");
					
					b.icallback(Proto1_B_1.id, h1)
					 .icallback(Proto1_B_2.id, h2)
					 .icallback(Proto1_B_3.id, h1)
					 .icallback(Proto1_B_4.id, h2);

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
