package demo.fib;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buff;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.ScribServerSocket;
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
				SessionEndpoint se = foo.project(Adder.AddServer, new ObjectStreamFormatter());
				Adder_AddServer_0 init = new Adder_AddServer_0(se);
				init.accept(ss, Adder.AddClient);

				try (Adder_AddServer_0 s0 = init)
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
	
	private static Adder_AddServer_3 X(Adder_AddServer_1 s1, Buff<Integer> i1, Buff<Integer> i2) throws ClassNotFoundException, ScribbleRuntimeException, IOException
	{
		Adder_AddServer_4 s4 = s1.branch();
		switch (s4.op)
		{
			case BYE:
			{
				return s4.receive(Adder.BYE);
			}
			case ADD:
			{
				return X(s4.receive(Adder.ADD, i1, i2).send(Adder.AddClient, Adder.RES, i1.val + i2.val), i1, i2);
			}
			default:
			{
				throw new RuntimeException("Won't get here: ");
			}
		}
	}
}
