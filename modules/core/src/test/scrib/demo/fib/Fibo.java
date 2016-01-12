//$ bin/scribblec.sh modules/core/src/test/scrib/demo/fib/Fib.scr -ip modules/core/src/test/scrib/ -d modules/core/src/test/scrib/ -api Fibonacci A -api Fibonacci B

package demo.fib;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.fib.Fib.Fibonacci.Fibonacci;
import demo.fib.Fib.Fibonacci.channels.A.Fibonacci_A_1;
import demo.fib.Fib.Fibonacci.channels.B.EndSocket;
import demo.fib.Fib.Fibonacci.channels.B.Fibonacci_B_1;
import demo.fib.Fib.Fibonacci.channels.B.Fibonacci_B_1_Handler;
import demo.fib.Fib.Fibonacci.channels.B.Fibonacci_B_2;
import demo.fib.Fib.Fibonacci.ops.end;
import demo.fib.Fib.Fibonacci.ops.fibonacci;
import demo.fib.Fib.Fibonacci.roles.A;
import demo.fib.Fib.Fibonacci.roles.B;

public class Fibo
{
	public static void main(String[] args) throws Exception
	{
		Fibonacci fib = new Fibonacci();
		new MyB(fib).start();
		new MyA(fib).start();
	}
}
		
class MyB extends Thread implements Fibonacci_B_1_Handler
{
	private final Fibonacci fib;
	private long x = 1;
	
	public MyB(Fibonacci fib)
	{
		this.fib = fib;
	}
	
	@Override
	public void run()
	{
		try (
			ScribServerSocket ss = new SocketChannelServer(8888);
			SessionEndpoint<Fibonacci, B> se = new SessionEndpoint<>(this.fib, Fibonacci.B, new ObjectStreamFormatter()))
		{
			se.accept(ss, Fibonacci.A);
			new Fibonacci_B_1(se).branch(Fibonacci.A, this);
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}
	
	@Override
	public void receive(Fibonacci_B_2 s, fibonacci op, Buf<Long> arg1) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		s.send(Fibonacci.A, Fibonacci.fibonacci, (this.x += arg1.val)).branch(Fibonacci.A, this);
	}

	@Override
	public void receive(EndSocket schan, end op) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		//System.out.println("B done: " + this.x);
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
		try (SessionEndpoint<Fibonacci, A> se = new SessionEndpoint<>(this.fib, Fibonacci.A, new ObjectStreamFormatter()))
		{
			se.connect(Fibonacci.B, SocketChannelEndpoint::new, "localhost", 8888);
			run(new Fibonacci_A_1(se), 19);  // 4184
			System.out.println("A done: " + this.b.val);
		}
		catch (Exception x)
		{
			x.printStackTrace();
		}
	}

	private demo.fib.Fib.Fibonacci.channels.A.EndSocket run(Fibonacci_A_1 s, int todo) throws Exception
	{
		return (todo > 0)
				? run(
						next(this.b.val,
							s.send(Fibonacci.B, Fibonacci.fibonacci, this.b.val)
							 .receive(Fibonacci.B, Fibonacci.fibonacci, this.b), todo), 
						todo - 2)
				: s.send(Fibonacci.B, Fibonacci.end);
	}
	
	private Fibonacci_A_1 next(long prev, Fibonacci_A_1 s, int todo)
	{
		//System.out.println("A: " + prev);
		if (todo > 1)
			this.b.val += prev;
		return s;
	}
}
