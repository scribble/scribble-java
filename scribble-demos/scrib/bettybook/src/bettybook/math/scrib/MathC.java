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

import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import bettybook.math.scrib.Math.MathService.MathService;
import bettybook.math.scrib.Math.MathService.channels.C.EndSocket;
import bettybook.math.scrib.Math.MathService.channels.C.MathService_C_1;
import bettybook.math.scrib.Math.MathService.roles.C;

public class MathC
{
	public MathC() throws Exception
	{
		facto(5);
		fibo(10);
	}
	
	private int facto(int n) throws Exception
	{
		Buf<Integer> i = new Buf<>(n), res = new Buf<>(i.val);
		MathService sess = new MathService();
		try (MPSTEndpoint<MathService, C> se = new MPSTEndpoint<>(sess, C, new ObjectStreamFormatter()))
		{
			se.connect(S, SocketChannelEndpoint::new, "localhost", 8888);

			MathService_C_1 s1 = new MathService_C_1(se);

			//facto(s1, i, res);
			facto(s1, res).send(S, Bye);

			System.out.println("Facto " + n + ": " + res.val);
			
			return res.val;
		}
	}
	
	private static void facto(MathService_C_1 s1, Buf<Integer> i, Buf<Integer> res) throws Exception
	{
		while (i.val > 1)
		{
			s1 = sub1(s1, i).send(S, Val, res.val).send(S, Mult, i.val).receive(S, Prod, res);
		}
		s1.send(S, Bye);
	}

	// Pre: b.val >= 1
	private static MathService_C_1 facto(MathService_C_1 s1, Buf<Integer> b) throws Exception
	{
		if (b.val == 1)
		{
			return s1;
		}
		Buf<Integer> tmp = new Buf<>(b.val);
		return facto(sub1(s1, tmp), tmp).send(S, Val, b.val).send(S, Mult, tmp.val).receive(S, Prod, b);
	}

	private int fibo(int i) throws Exception
	{
		Buf<Integer> i1 = new Buf<>(0);
		Buf<Integer> i2 = new Buf<>(1);
		MathService sess = new MathService();
		try (MPSTEndpoint<MathService, C> se = new MPSTEndpoint<>(sess, C, new ObjectStreamFormatter()))
		{
			se.connect(S, SocketChannelEndpoint::new, "localhost", 8888);

			fibo(new MathService_C_1(se), i1, i2, new Buf<>(i));

			System.out.println("Fibo " + i + ": " + i1.val);
			
			return i1.val;
		}
	}
	
	// Pre: i >= 0
	// Post: i1.val is the i-th Fibonacci number
	private static EndSocket fibo(MathService_C_1 s1, Buf<Integer> i1, Buf<Integer> i2, Buf<Integer> i) throws Exception
	{
		return (i.val > 0)
			? fibo(
					sub1(s1, i).send(S, Val, i1.val).send(S, Add, i1.val=i2.val).receive(S, Sum, i2),
					i1, i2, i)
			: s1.send(S, Bye);
	}
	
	private static MathService_C_1 sub1(MathService_C_1 s1, Buf<Integer> b) throws Exception
	{
		return s1.send(S, Val, b.val).send(S, Add, -1).receive(S, Sum, b);
	}

	public static void main(String[] args) throws Exception
	{
		new MathC();
	}
}
