package demo.fib;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buff;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.session.SessionEndpoint;

public class AdderServer
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException
	{
		try (ScribServerSocket ss = new ScribServerSocket(8888))
		{
			Buff<Integer> i1 = new Buff<>();
			Buff<Integer> i2 = new Buff<>();

			while (true)
			{
				Adder foo = new Adder();
				SessionEndpoint se = foo.project(Adder.S, new ObjectStreamFormatter());
				Adder_S_0 init = new Adder_S_0(se);
				init.accept(ss, Adder.C);

				try (Adder_S_0 s0 = init)
				{
					X(s0.init(), i1, i2).end();
				}
				catch (ScribbleRuntimeException | IOException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	private static Adder_S_3 X(Adder_S_1 s1, Buff<Integer> i1, Buff<Integer> i2) throws ClassNotFoundException, ScribbleRuntimeException, IOException
	{
		Adder_S_4 s4 = s1.branch();
		switch (s4.op)
		{
			case BYE:
			{
				return s4.receive(Adder.BYE);
			}
			case ADD:
			{
				return X(s4.receive(Adder.ADD, i1, i2).send(Adder.C, Adder.RES, i1.val + i2.val), i1, i2);
			}
			default:
			{
				throw new RuntimeException("Won't get here: ");
			}
		}
	}
}
