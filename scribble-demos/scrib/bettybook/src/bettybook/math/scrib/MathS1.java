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
package bettybook.math.scrib;

import static bettybook.math.scrib.Math.MathService.MathService.Add;
import static bettybook.math.scrib.Math.MathService.MathService.Bye;
import static bettybook.math.scrib.Math.MathService.MathService.C;
import static bettybook.math.scrib.Math.MathService.MathService.Mult;
import static bettybook.math.scrib.Math.MathService.MathService.Prod;
import static bettybook.math.scrib.Math.MathService.MathService.S;
import static bettybook.math.scrib.Math.MathService.MathService.Sum;
import static bettybook.math.scrib.Math.MathService.MathService.Val;

import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.ScribServerSocket;
import org.scribble.runtime.net.SocketChannelServer;
import org.scribble.runtime.session.MPSTEndpoint;
import org.scribble.runtime.util.Buf;

import bettybook.math.scrib.Math.MathService.MathService;
import bettybook.math.scrib.Math.MathService.roles.S;
import bettybook.math.scrib.Math.MathService.statechans.S.MathService_S_1;
import bettybook.math.scrib.Math.MathService.statechans.S.MathService_S_1_Cases;
import bettybook.math.scrib.Math.MathService.statechans.S.MathService_S_2_Cases;

public class MathS1
{
	public MathS1() throws Exception
	{
		run();
	}

	private void run() throws Exception
	{
		try (ScribServerSocket ss = new SocketChannelServer(8888))
		{
			while (true)
			{
				MathService sess = new MathService();
				try (MPSTEndpoint<MathService, S> se = new MPSTEndpoint<>(sess, S,
						new ObjectStreamFormatter())) {
					se.accept(ss, C);
					Buf<Integer> b1 = new Buf<>();
					Buf<Integer> b2 = new Buf<>();

					MathService_S_1 s1 = new MathService_S_1(se);
					Loop: while (true)
					{
						MathService_S_1_Cases c1 = s1.branch(C);
						switch (c1.op)
						{
							case Bye: c1.receive(Bye); break Loop;
							case Val:
							{
								MathService_S_2_Cases c2 = c1.receive(Val, b1).branch(C);
								switch (c2.op)
								{
									case Add:  s1 = c2.receive(Add, b2).send(C, Sum, b1.val + b2.val); break;
									case Mult: s1 = c2.receive(Mult, b2).send(C, Prod, b1.val * b2.val); break;
								}
								break;
							}
						}
					}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception
	{
		new MathS1();
	}
}
