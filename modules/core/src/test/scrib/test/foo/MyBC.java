package test.foo;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.SessionEndpoint;

public class MyBC
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException
	{
		try (ScribServerSocket ss_B = new SocketChannelServer(8888);
				 ScribServerSocket ss_C = new SocketChannelServer(9999))
		{
			//Buff<String> s = new Buff<>();

			while (true)
			{
				Foo foo = new Foo();
				SessionEndpoint se_B = foo.project(Foo.B, ss_B, new ObjectStreamFormatter());
				SessionEndpoint se_C = foo.project(Foo.C, ss_C, new ObjectStreamFormatter());
				Foo_B_0 init_B = new Foo_B_0(se_B);
				Foo_C_0 init_C = new Foo_C_0(se_C);
				init_B.accept(Foo.A);
				init_C.accept(Foo.A);
				
				new Thread()
				{
					public void run()
					{
						try (Foo_B_0 s0_B = init_B)
						{
							Foo_B_1 s1_B = s0_B.init();

							Foo_B_2 s2_B = s1_B.branch();
							switch (s2_B.op)
							{
								case _1:
								{
									s2_B.receive(Foo._1);
									System.out.println("B first!");
									break;
								}
								case _2:
								{
									s2_B.receive(Foo._2);
									break;
								}
							}
						}
						catch (ScribbleRuntimeException | IOException | ClassNotFoundException | ExecutionException | InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				}.start();

				new Thread()
				{
					public void run()
					{
						try (Foo_C_0 s0_C = init_C)
						{
							Foo_C_1 s1_C = s0_C.init();

							Foo_C_2 s2_C = s1_C.branch();
							switch (s2_C.op)
							{
								case _1:
								{
									s2_C.receive(Foo._1);
									System.out.println("C first!");
									break;
								}
								case _2:
								{
									s2_C.receive(Foo._2);
									break;
								}
							}
						}
						catch (ScribbleRuntimeException | IOException | ClassNotFoundException | ExecutionException | InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				}.start();
			}
		}
	}
}
