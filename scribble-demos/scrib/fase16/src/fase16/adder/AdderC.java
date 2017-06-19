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

import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import fase16.adder.Adder.Adder.Adder;
import fase16.adder.Adder.Adder.channels.C.Adder_C_1;
import fase16.adder.Adder.Adder.channels.C.Adder_C_3;
import fase16.adder.Adder.Adder.roles.C;

public class AdderC
{
	private static final int N = 10;

	public static void main(String[] args) throws Exception
	{
		//adder();
		fib();
	}
	
	private static void adder() throws Exception
	{
		Adder adder = new Adder();
		try (MPSTEndpoint<Adder, C> se = new MPSTEndpoint<>(adder, C, new ObjectStreamFormatter()))
		{	
			se.connect(S, SocketChannelEndpoint::new, "localhost", 8888);

			Adder_C_1 s1 = new Adder_C_1(se);
			Buf<Integer> i = new Buf<>(1);
			//*
			for (int j = 0; j < N; j++)
			{
				s1 = s1.send(S, Add, i.val, i.val).receive(S, Res, i);
			}
			s1.send(S, Bye).receive(S, Bye);
			/*/
			s1.send(S, Add, i.val, i.val)
			  .receive(S, Res, i)
        .send(S, Add, i.val, i.val)
			  .receive(S, Res, i)
			  //...
			  .send(S, Bye).receive(S, Bye);
			//*/
			
			System.out.println("C: " + i.val);
		}
	}
		
	private static void fib() throws Exception
	{
		Adder adder = new Adder();
		try (MPSTEndpoint<Adder, C> se = new MPSTEndpoint<>(adder, C, new ObjectStreamFormatter()))
		{	
			se.connect(S, SocketChannelEndpoint::new, "localhost", 8888);

			Adder_C_1 s1 = new Adder_C_1(se);
			Buf<Integer> i = new Buf<>(0);
			fib(s1, i, new Buf<Integer>(1), 0).receive(S, Bye);

			System.out.println("Fib: " + i.val);
		}
	}

	private static Adder_C_3 fib(Adder_C_1 s1, Buf<Integer> i1, Buf<Integer> i2, int i) throws Exception
	{
		return (i < N)
				? fib(s1.send(S, Add, i1.val, i1.val=i2.val).receive(S, Res, i2), i1, i2, i+1)
				: s1.send(S, Bye);
	}
}
