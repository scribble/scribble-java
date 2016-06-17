package demo.fib;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.SessionEndpoint;

import demo.fib.Fib.Adder.Adder;
import demo.fib.Fib.Adder.channels.S.Adder_S_1;
import demo.fib.Fib.Adder.channels.S.Adder_S_1_Cases;
import demo.fib.Fib.Adder.channels.S.Adder_S_3;
import demo.fib.Fib.Adder.roles.S;

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
				try (SessionEndpoint<Adder, S> se = new SessionEndpoint<>(foo, Adder.S, new ObjectStreamFormatter()))
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
