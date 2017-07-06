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
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.runtime.net.Buf;
import org.scribble.runtime.net.ObjectStreamFormatter;
import org.scribble.runtime.net.scribsock.ScribServerSocket;
import org.scribble.runtime.net.scribsock.SocketChannelServer;
import org.scribble.runtime.net.session.MPSTEndpoint;

import fib.Fib.Adder.Adder;
import fib.Fib.Adder.channels.S.Adder_S_1;
import fib.Fib.Adder.channels.S.Adder_S_1_Cases;
import fib.Fib.Adder.channels.S.Adder_S_3;
import fib.Fib.Adder.roles.S;

public class AdderServer
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException, ExecutionException, InterruptedException
	{
		try (ScribServerSocket ss = new SocketChannelServer(8888))
		{
			Buf<Integer> i1 = new Buf<>();
			Buf<Integer> i2 = new Buf<>();

			while (true)
			{
				Adder foo = new Adder();
				try (MPSTEndpoint<Adder, S> se = new MPSTEndpoint<>(foo, Adder.S, new ObjectStreamFormatter()))
				{
					se.accept(ss, Adder.C);

					X(new Adder_S_1(se), i1, i2).send(Adder.C, Adder.BYE);
				}
				catch (ScribbleRuntimeException | IOException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	private static Adder_S_3 X(Adder_S_1 s1, Buf<Integer> i1, Buf<Integer> i2) throws ClassNotFoundException, ScribbleRuntimeException, IOException, ExecutionException, InterruptedException
	{
		Adder_S_1_Cases cases = s1.branch(Adder.C);
		switch (cases.op)
		{
			case BYE:
			{
				return cases.receive(Adder.BYE);
			}
			case ADD:
			{
				return X(cases.receive(Adder.ADD, i1, i2).send(Adder.C, Adder.RES, i1.val + i2.val), i1, i2);
			}
			default:
			{
				throw new RuntimeException("Won't get here: ");
			}
		}
	}
}
