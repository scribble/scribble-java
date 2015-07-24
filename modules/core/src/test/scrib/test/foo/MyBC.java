package test.foo;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.session.SessionEndpoint;

public class MyBC
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException
	{
		try (ScribServerSocket ss_B = new ScribServerSocket(8888);
				 ScribServerSocket ss_C = new ScribServerSocket(9999))
		{
			//Buff<String> s = new Buff<>();

			while (true)
			{
				Foo foo = new Foo();
				SessionEndpoint se_B = foo.project(Foo.B, new ObjectStreamFormatter());
				SessionEndpoint se_C = foo.project(Foo.C, new ObjectStreamFormatter());
				Foo_B_0 init_B = new Foo_B_0(se_B);
				Foo_C_0 init_C = new Foo_C_0(se_C);
				init_B.accept(ss_B, Foo.A);
				init_C.accept(ss_C, Foo.A);
				
				new Thread()
				{
					public void run()
					{
						try (Foo_B_0 s0_B = init_B)
						{
							Foo_B_1 s1_B = s0_B.init();

							Foo_B_3 s3_B = s1_B.branch();
							switch (s3_B.op)
							{
								case _1:
								{
									s3_B.receive(Foo._1).end();;
									System.out.println("B first!");
									break;
								}
								case _2:
								{
									s3_B.receive(Foo._2).end();;
									break;
								}
							}
						}
						catch (ScribbleRuntimeException | IOException | ClassNotFoundException e)
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

							Foo_C_3 s3_C = s1_C.branch();
							switch (s3_C.op)
							{
								case _1:
								{
									s3_C.receive(Foo._1).end();;
									System.out.println("C first!");
									break;
								}
								case _2:
								{
									s3_C.receive(Foo._2).end();;
									break;
								}
							}
						}
						catch (ScribbleRuntimeException | IOException | ClassNotFoundException e)
						{
							e.printStackTrace();
						}
					}
				}.start();
			}
		}
	}
}
