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
package test.test7;

import static test.test7.Test7.Proto1.Proto1.A;
import static test.test7.Test7.Proto1.Proto1.B;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.function.Function;

import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.SocketChannelEndpoint;

import test.test7.Test7.Proto1.Proto1;
import test.test7.Test7.Proto1.callbacks.A.Proto1_A;
import test.test7.Test7.Proto1.callbacks.A.states.Proto1_A_1;
import test.test7.Test7.Proto1.callbacks.A.states.Proto1_A_2;

public class MyA
{
	public static void main(String[] args) throws IOException
	{
		Proto1 P1 = new Proto1();
		String[] data = new String[] { "" };
		try (Proto1_A<String[]> a = new Proto1_A<>(P1, A, new ObjectStreamFormatter(), data))
		{
			a.request(B, SocketChannelEndpoint::new, "localhost", 8888);

			Function<String[], Proto1_A_1.Message> h = x -> new Proto1_A_1.B._2((x[0] += "a"));

			a.icallback(Proto1_A_1.id, h)
			 .icallback(Proto1_A_2.id, h);
			
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
