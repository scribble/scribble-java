package demo.abcd.fibo;

import static demo.abcd.fibo.Fibo.Fibonacci.Fibonacci.A;
import static demo.abcd.fibo.Fibo.Fibonacci.Fibonacci.B;
import static demo.abcd.fibo.Fibo.Fibonacci.Fibonacci.fibonacci;
import static demo.abcd.fibo.Fibo.Fibonacci.Fibonacci.stop;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.abcd.fibo.Fibo.Fibonacci.Fibonacci;
import demo.abcd.fibo.Fibo.Fibonacci.channels.A.Fibonacci_A_1;
import demo.abcd.fibo.Fibo.Fibonacci.channels.A.ioifaces.In_B_fibonacci_Long;
import demo.abcd.fibo.Fibo.Fibonacci.channels.A.ioifaces.Out_B_fibonacci_Long;
import demo.abcd.fibo.Fibo.Fibonacci.channels.A.ioifaces.Out_B_stop;
import demo.abcd.fibo.Fibo.Fibonacci.channels.A.ioifaces.Succ_In_B_fibonacci_Long;
import demo.abcd.fibo.Fibo.Fibonacci.channels.A.ioifaces.Succ_Out_B_fibonacci_Long;
import demo.abcd.fibo.Fibo.Fibonacci.channels.A.ioifaces.Succ_Out_B_stop;
import demo.abcd.fibo.Fibo.Fibonacci.channels.B.EndSocket;
import demo.abcd.fibo.Fibo.Fibonacci.channels.B.Fibonacci_B_1;
import demo.abcd.fibo.Fibo.Fibonacci.channels.B.Fibonacci_B_1_Cases;
import demo.abcd.fibo.Fibo.Fibonacci.channels.B.Fibonacci_B_1_Handler;
import demo.abcd.fibo.Fibo.Fibonacci.channels.B.Fibonacci_B_2;
import demo.abcd.fibo.Fibo.Fibonacci.ops.fibonacci;
import demo.abcd.fibo.Fibo.Fibonacci.ops.stop;
import demo.abcd.fibo.Fibo.Fibonacci.roles.A;
import demo.abcd.fibo.Fibo.Fibonacci.roles.B;

public class FiboMain
{
	public static void main(String[] args) throws Exception
	{
		Fibonacci fib = new Fibonacci();
		new MyB(fib).start();
		//new MyBHandler(fib).start();
		new MyA(fib).start();
	}
}

class MyA extends Thread
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

	private demo.abcd.fibo.Fibo.Fibonacci.channels.A.EndSocket
			run(Fibonacci_A_1 s, int todo) throws Exception
	{
		return (todo > 0)
				? run(
						next(this.b.val,
							s.send(B, fibonacci, this.b.val)
							 .receive(B, fibonacci, this.b), todo), 
						todo - 2)
				: s.send(Fibonacci.B, stop);
	}
	
	private
	<
		S extends
				Out_B_fibonacci_Long<S1> & Out_B_stop<demo.abcd.fibo.Fibo.Fibonacci.channels.A.EndSocket>
			& Succ_In_B_fibonacci_Long,
		S1 extends
				In_B_fibonacci_Long<S>
			& Succ_Out_B_fibonacci_Long
		//E extends Succ_Out_B_stop
	>
		demo.abcd.fibo.Fibo.Fibonacci.channels.A.EndSocket run1(S s, int todo) throws Exception
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

class MyB extends Thread
{
	private final Fibonacci fib;
	private Buf<Long> b = new Buf<>(1L);
	
	public MyB(Fibonacci fib)
	{
		this.fib = fib;
	}
	
	@Override
	public void run()
	{
		try (
			ScribServerSocket ss = new SocketChannelServer(8888);
			SessionEndpoint<Fibonacci, B> se = new SessionEndpoint<>(this.fib, B, new ObjectStreamFormatter()))
		{
			se.accept(ss, A);
			run(new Fibonacci_B_1(se));
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
	
	private EndSocket run(Fibonacci_B_1 s) throws Exception
	{
		/*
		... s.branch(A);
		switch (...)  // Value-switch
		{
			
		}
	}
}
/*/
		Fibonacci_B_1_Cases c = s.branch(A);
		switch (c.op)
		{
			case fibonacci:
				long prev = this.b.val;
				return run(c.receive(fibonacci, this.b).send(A, fibonacci, this.b.val += prev));
			case stop:
				return c.receive(stop);
			default: throw new RuntimeException();
		}
	}
}
//*/
	
class MyBHandler extends Thread implements Fibonacci_B_1_Handler
{
	private final Fibonacci fib;
	private long x = 1;
	
	public MyBHandler(Fibonacci fib)
	{
		this.fib = fib;
	}

	@Override
	public void run()
	{
		try (
			ScribServerSocket ss = new SocketChannelServer(8888);
			SessionEndpoint<Fibonacci, B> se = new SessionEndpoint<>(this.fib, B, new ObjectStreamFormatter()))
		{
			se.accept(ss, A);
			new Fibonacci_B_1(se).branch(A, this);
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
/*
}
/*/
	@Override
	public void receive(Fibonacci_B_2 s, fibonacci op, Buf<Long> arg1) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		s.send(A, fibonacci, (this.x += arg1.val)).branch(A, this);
	}

	@Override
	public void receive(EndSocket schan, stop op) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		//System.out.println("B done: " + this.x);
	}
}	
//*/
