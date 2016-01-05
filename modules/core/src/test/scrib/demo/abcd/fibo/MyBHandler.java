package demo.abcd.fibo;

import static demo.abcd.fibo.Fibo.Fibonacci.Fibonacci.A;
import static demo.abcd.fibo.Fibo.Fibonacci.Fibonacci.B;
import static demo.abcd.fibo.Fibo.Fibonacci.Fibonacci.fibonacci;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.SessionEndpoint;

import demo.abcd.fibo.Fibo.Fibonacci.Fibonacci;
import demo.abcd.fibo.Fibo.Fibonacci.channels.B.EndSocket;
import demo.abcd.fibo.Fibo.Fibonacci.channels.B.Fibonacci_B_1;
import demo.abcd.fibo.Fibo.Fibonacci.channels.B.Fibonacci_B_1_Handler;
import demo.abcd.fibo.Fibo.Fibonacci.channels.B.Fibonacci_B_2;
import demo.abcd.fibo.Fibo.Fibonacci.ops.fibonacci;
import demo.abcd.fibo.Fibo.Fibonacci.ops.stop;
import demo.abcd.fibo.Fibo.Fibonacci.roles.B;

public class MyBHandler extends Thread implements Fibonacci_B_1_Handler
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
	public void receive(Fibonacci_B_2 s2, fibonacci op, Buf<Long> arg1) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		s2.send(A, fibonacci, (this.x += arg1.val)).branch(A, this);
	}

	@Override
	public void receive(EndSocket end, stop op) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		//System.out.println("B done: " + this.x);
	}
}	
//*/
