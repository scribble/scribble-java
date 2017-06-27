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
package coco.fibo;

import static coco.fibo.Fibo.Fibonacci.Fibonacci.A;
import static coco.fibo.Fibo.Fibonacci.Fibonacci.B;
import static coco.fibo.Fibo.Fibonacci.Fibonacci.fibonacci;
import static coco.fibo.Fibo.Fibonacci.Fibonacci.stop;

import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import coco.fibo.Fibo.Fibonacci.Fibonacci;
import coco.fibo.Fibo.Fibonacci.channels.A.EndSocket;
import coco.fibo.Fibo.Fibonacci.channels.A.Fibonacci_A_1;
import coco.fibo.Fibo.Fibonacci.channels.A.ioifaces.In_B_fibonacci_Long;
import coco.fibo.Fibo.Fibonacci.channels.A.ioifaces.Out_B_fibonacci_Long;
import coco.fibo.Fibo.Fibonacci.channels.A.ioifaces.Out_B_stop;
import coco.fibo.Fibo.Fibonacci.channels.A.ioifaces.Succ_In_B_fibonacci_Long;
import coco.fibo.Fibo.Fibonacci.channels.A.ioifaces.Succ_Out_B_fibonacci_Long;
import coco.fibo.Fibo.Fibonacci.roles.A;

public class FiboA extends Thread
{
	private final Fibonacci fib;
	private Buf<Long> b = new Buf<>(0L);
	
	public FiboA(Fibonacci fib)
	{
		this.fib = fib;
	}
	
	@Override
	public void run()
	{
		int n = 19;
		try (MPSTEndpoint<Fibonacci, A> se = new MPSTEndpoint<>(this.fib, A, new ObjectStreamFormatter()))
		{
			se.connect(B, SocketChannelEndpoint::new, "localhost", 8888);
			run(new Fibonacci_A_1(se), n);  // 4184
			System.out.println("A #" + n + ": " + this.b.val);
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}

	private EndSocket run(Fibonacci_A_1 s, int todo) throws Exception
	{
		return run1(s, todo);
	}
	
	private
	<
	//*/
		T1 extends
				Out_B_fibonacci_Long<T2> & Out_B_stop<EndSocket>  // Action I/f's
			& Succ_In_B_fibonacci_Long,                         // Successor I/f's
		T2 extends
				In_B_fibonacci_Long<T1>
			& Succ_Out_B_fibonacci_Long                         //, E extends Succ_Out_B_stop
	/*/
		T1 extends Select_A_B_fibonacci_Long__B_stop<T2, EndSocket>,
		T2 extends Receive_A_B_fibonacci_Long<T1>
	//*/
	>
	EndSocket run1(T1 s, int todo) throws Exception
	{
		return (todo > 0)
				? run1(
						next(this.b.val,
							s.send(B, fibonacci, this.b.val)
							 .receive(B, fibonacci, this.b), todo), 
						todo - 2)
				: s.send(Fibonacci.B, stop);
	}
	
	//private Fibonacci_A_1 next(long prev, Fibonacci_A_1 s, int todo)
	private <S> S next(long prev, S s, int todo)
	{
		//System.out.println("A: " + prev);
		if (todo > 1)
			this.b.val += prev;
		return s;
	}
}