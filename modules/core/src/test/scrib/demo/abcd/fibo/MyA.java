package demo.abcd.fibo;

import static demo.abcd.fibo.Fibo.Fibonacci.Fibonacci.A;
import static demo.abcd.fibo.Fibo.Fibonacci.Fibonacci.B;
import static demo.abcd.fibo.Fibo.Fibonacci.Fibonacci.fibonacci;
import static demo.abcd.fibo.Fibo.Fibonacci.Fibonacci.stop;

import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.abcd.fibo.Fibo.Fibonacci.Fibonacci;
import demo.abcd.fibo.Fibo.Fibonacci.channels.A.EndSocket;
import demo.abcd.fibo.Fibo.Fibonacci.channels.A.Fibonacci_A_1;
import demo.abcd.fibo.Fibo.Fibonacci.channels.A.ioifaces.In_B_fibonacci_Long;
import demo.abcd.fibo.Fibo.Fibonacci.channels.A.ioifaces.Out_B_fibonacci_Long;
import demo.abcd.fibo.Fibo.Fibonacci.channels.A.ioifaces.Out_B_stop;
import demo.abcd.fibo.Fibo.Fibonacci.channels.A.ioifaces.Succ_In_B_fibonacci_Long;
import demo.abcd.fibo.Fibo.Fibonacci.channels.A.ioifaces.Succ_Out_B_fibonacci_Long;
import demo.abcd.fibo.Fibo.Fibonacci.channels.A.ioifaces.Succ_Out_B_stop;
import demo.abcd.fibo.Fibo.Fibonacci.roles.A;

public class MyA extends Thread
{
	private final Fibonacci fib;
	private Buf<Long> b = new Buf<>(0L);
	
	public MyA(Fibonacci fib)
	{
		this.fib = fib;
	}
	
	@Override
	public void run()
	{
		try (SessionEndpoint<Fibonacci, A> se = new SessionEndpoint<>(this.fib, A, new ObjectStreamFormatter()))
		{
			se.connect(B, SocketChannelEndpoint::new, "localhost", 8888);
			run(new Fibonacci_A_1(se), 19);  // 4184
			//run1(new Fibonacci_A_1(se), 19);
			System.out.println("A done: " + this.b.val);
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}

	private EndSocket run(Fibonacci_A_1 s, int todo) throws Exception
	{
		return (todo > 0)
				? run(
						next(this.b.val,
							s.send(B, fibonacci, this.b.val)
							 .receive(B, fibonacci, this.b), todo), 
						todo - 2)
				: s.send(Fibonacci.B, stop);
	}
	
	//*/
	private
	<
		S1 extends
				Out_B_fibonacci_Long<S2> & Out_B_stop<EndSocket>
			& Succ_In_B_fibonacci_Long,
		S2 extends
				In_B_fibonacci_Long<S1>
			& Succ_Out_B_fibonacci_Long//, E extends Succ_Out_B_stop
	>
	EndSocket run1(S1 s, int todo) throws Exception
	/*/
	private
	<
		S1 extends Select_A_B_fibonacci_Long__B_stop<S2, EndSocket>,
		S2 extends Receive_A_B_fibonacci_Long<S1>
	>
	EndSocket run1(S1 s, int todo) throws Exception
	//*/
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