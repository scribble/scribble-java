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
package fib;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import fib.Fib.Adder.Adder;
import fib.Fib.Adder.channels.C.Adder_C_1;
import fib.Fib.Adder.channels.C.Adder_C_2;
import fib.Fib.Adder.channels.C.Adder_C_3;
import fib.Fib.Adder.roles.C;

public class FibClient
{
	public static void main(String[] args) throws UnknownHostException, ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	{
		Buf<Integer> i1 = new Buf<>(0);
		Buf<Integer> i2 = new Buf<>(1);
		
		Adder adder = new Adder();
		try (MPSTEndpoint<Adder, C> se = new MPSTEndpoint<>(adder, Adder.C, new ObjectStreamFormatter()))
		{
			se.connect(Adder.S, SocketChannelEndpoint::new, "localhost", 8888);

			Adder_C_1 s1 = new Adder_C_1(se);

			fib(s1, i1, i2, 0).receive(Adder.S, Adder.BYE);
		}
	}

	private static Adder_C_3 fib(Adder_C_1 s1, Buf<Integer> i1, Buf<Integer> i2, int i) throws ClassNotFoundException, ScribbleRuntimeException, IOException, ExecutionException, InterruptedException
	{
		return (i < 20)
			//? fib(side(s1.send(Adder.S, Adder.ADD, i1.val, i2.val), i1, i2).receive(Adder.RES, i2), i1, i2, i + 1)
			? fib(side(s1.send(Adder.S, Adder.ADD, i1.val, i1.val = i2.val), i1, i2).receive(Adder.S, Adder.RES, i2), i1, i2, i + 1)
			: s1.send(Adder.S, Adder.BYE);
	}
	
	private static Adder_C_2 side(Adder_C_2 s2, Buf<Integer> i1, Buf<Integer> i2)
	{
		System.out.print(i1.val + " ");
		//i1.val = i2.val;
		return s2;
	}
}
