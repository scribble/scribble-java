package test.test1;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buff;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.ScribServerSocket;
import org.scribble.net.session.SessionEndpoint;

public class MyS
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException
	{
		try (ScribServerSocket ss = new ScribServerSocket(8888))
		{
			Buff<Integer> i1 = new Buff<>();
			Buff<Integer> i2 = new Buff<>();

			while (true)
			{
				Proto1 foo = new Proto1();
				SessionEndpoint se = foo.project(Proto1.S, new ObjectStreamFormatter());
				Proto1_S_0 init = new Proto1_S_0(se);
				init.accept(ss, Proto1.C);

				try (Proto1_S_0 s0 = init)
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
	
	private static Proto1_S_3 X(Proto1_S_1 s1, Buff<Integer> i1, Buff<Integer> i2) throws ClassNotFoundException, ScribbleRuntimeException, IOException
	{
		Proto1_S_4 s4 = s1.branch();
		switch (s4.op)
		{
			case BYE:
			{
				return s4.receive(Proto1.BYE);
			}
			case ADD:
			{
				return X(s4.receive(Proto1.ADD, i1, i2).send(Proto1.C, Proto1.RES, i1.val + i2.val), i1, i2);
			}
			default:
			{
				throw new RuntimeException("Won't get here: ");
			}
		}
	}
}
