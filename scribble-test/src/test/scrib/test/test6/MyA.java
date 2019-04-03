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
import org.scribble.runtime.net.SocketChannelEndpoint;

import test.test6.Test6.Proto1.Proto1;
import test.test6.Test6.Proto1.callbacks.A.Proto1_A;
import test.test6.Test6.Proto1.callbacks.A.Proto1_A_2_Branch;
import test.test6.Test6.Proto1.callbacks.A.states.Proto1_A_1;
import test.test6.Test6.Proto1.callbacks.A.states.Proto1_A_2;
import test.test6.Test6.Proto1.callbacks.A.states.Proto1_A_3;
import test.test6.Test6.Proto1.callbacks.A.states.Proto1_A_4;
import test.test6.Test6.Proto1.ops._2;
import test.test6.Test6.Proto1.roles.B;;

public class MyA
{
	public static void main(String[] args) throws IOException
	{
		class MyHandler extends Proto1_A_2_Branch<int[]>
		{
			@Override
			public void receive(int[] data, B peer, _2 op, String arg1)
			{
				System.out.println("(A) received 2: " + arg1 + ", " + data[0]++);
			}
		}
		
		Proto1 P1 = new Proto1();
		try (Proto1_A<int[]> a = new Proto1_A<>(P1, A, new ObjectStreamFormatter(), new int[1]))
		{
			a.request(B, SocketChannelEndpoint::new, "localhost", 8888);

			Function<int[], Proto1_A_1.Message> h1 = x -> new Proto1_A_1.B._1(x[0]++);
			MyHandler h2 = new MyHandler();
			
			a.icallback(Proto1_A_1.id, h1)
			 .icallback(Proto1_A_2.id, h2)
			 .icallback(Proto1_A_3.id, h1)
			 .icallback(Proto1_A_4.id, h2);
			
			Future<Void> f = a.run();
			f.get();

			System.out.println("(A) end");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
