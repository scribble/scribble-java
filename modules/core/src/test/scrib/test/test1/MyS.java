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

			//while (true)
			{
				Proto1 foo = new Proto1();
				SessionEndpoint se = foo.project(Proto1.S, ss, new ObjectStreamFormatter());
				System.out.println("s0: ");
				//se.open();  // FIXME: reuse open server socket -- server socket should be longer lived than the se
				System.out.println("s1: ");
				Proto1_S_0 init = new Proto1_S_0(se);
				System.out.println("s2: ");
				init.accept(null, Proto1.C);
				System.out.println("s3: ");

				try (Proto1_S_0 s0 = init)
				{
					Proto1_S_1 s1 = s0.init();
					
					//s1.send(Proto1.C, Proto1._1).receive(Proto1._2);
					
					Proto1_S_4 s4 = s1.send(Proto1.C, Proto1._1, 1);

					Thread.sleep(2000);

					s4.send(Proto1.C, Proto1._2, 2)
					  .receive(Proto1._3, new Buff<>());
				}
				catch (Exception e)//ScribbleRuntimeException | IOException | ExecutionException | InterruptedException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
