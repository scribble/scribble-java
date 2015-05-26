package test.fib;

import java.io.IOException;

import org.scribble2.net.Buff;
import org.scribble2.net.ObjectStreamFormatter;
import org.scribble2.net.ScribServerSocket;
import org.scribble2.net.session.SessionEndpoint;
import org.scribble2.util.ScribbleException;
import org.scribble2.util.ScribbleRuntimeException;

public class AdderServer
{
	public static void main(String[] args) throws ScribbleException, IOException
	{
		Adder foo = new Adder();
		SessionEndpoint se = foo.project(Adder.AddServer, new ObjectStreamFormatter());
		try (ScribServerSocket ss = new ScribServerSocket(8888))
		{
			//Buff<String> s = new Buff<>();
			Buff<Integer> i1 = new Buff<>();
			Buff<Integer> i2 = new Buff<>();

			while (true)
			{
				try (Adder_AddServer_0 init = new Adder_AddServer_0(se))
				{
					init.accept(ss, Adder.AddClient);
					Adder_AddServer_1 s1 = init.init();

					X(s1, i1, i2).end();
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
				return X(s4.receive(Adder.ADD, i1, i2).send(Adder.RES, i1.val + i2.val), i1, i2);
			}
			default:
			{
				throw new RuntimeException("Won't get here: ");
			}
		}
	}
}
