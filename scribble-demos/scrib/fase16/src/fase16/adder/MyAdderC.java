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
package fase16.adder;

import static fase16.adder.Adder.Adder.Adder.Add;
import static fase16.adder.Adder.Adder.Adder.Bye;
import static fase16.adder.Adder.Adder.Adder.C;
import static fase16.adder.Adder.Adder.Adder.Res;
import static fase16.adder.Adder.Adder.Adder.S;

import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.SocketChannelEndpoint;
import org.scribble.runtime.session.MPSTEndpoint;
import org.scribble.runtime.util.Buf;

import fase16.adder.Adder.Adder.Adder;
import fase16.adder.Adder.Adder.roles.C;
import fase16.adder.Adder.Adder.statechans.C.Adder_C_1;
import fase16.adder.Adder.Adder.statechans.C.Adder_C_3;

public class MyAdderC
{
	public static void main(String[] args) throws Exception
	{
		Adder adder = new Adder();
		try (MPSTEndpoint<Adder, C> se = new MPSTEndpoint<>(adder, C,
				new ObjectStreamFormatter()))
		{
			se.request(S, SocketChannelEndpoint::new, "localhost", 8888);

			
			Buf<Integer> i = new Buf<>();
			
			Adder_C_1 c1 = new Adder_C_1(se);
			
			for (int j = 0; j<3; j++)
			{
				c1 = c1.send(S, Add, 1, 2).receive(S, Res, i);
			}
			c1.send(S, Bye).receive(S, Bye);

		}
	}
		
	private static final int N = 10;

	private static Adder_C_3 fib(Adder_C_1 s1, Buf<Integer> i1, Buf<Integer> i2,
			int i) throws Exception
	{
		return null;
	}
}
