package test.test1;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buff;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.ScribFuture;
import org.scribble.net.scribsock.ScribServerSocket;
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
					Proto1_S_1 s1 = s0.init();
					
					Thread.sleep(2000);

					s1.send(Proto1.C, Proto1._1)
					  .send(Proto1.C, Proto1._2, 2)
					  .receive(Proto1._3, new Buff<>());
				}
				catch (ScribbleRuntimeException | IOException | ExecutionException | InterruptedException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
