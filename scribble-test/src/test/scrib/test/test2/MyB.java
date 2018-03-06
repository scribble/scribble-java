package test.test2;

import static test.test2.Test2.Proto1.Proto1.A;
import static test.test2.Test2.Proto1.Proto1.B;
import static test.test2.Test2.Proto1.Proto1._2;

import java.io.IOException;
import java.util.concurrent.Future;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.runtime.net.ObjectStreamFormatter;
import org.scribble.runtime.net.scribsock.ScribServerSocket;
import org.scribble.runtime.net.scribsock.SocketChannelServer;
import org.scribble.runtime.net.session.MPSTEndpoint;

import test.test2.Test2.Proto1.Proto1;
import test.test2.Test2.Proto1.channels.B.EndSocket;
import test.test2.Test2.Proto1.channels.B.Proto1_B_1;
import test.test2.Test2.Proto1.channels.B.Proto1_B_1_Handler;
import test.test2.Test2.Proto1.handlers.B.Proto1_B;
import test.test2.Test2.Proto1.ops._1;
import test.test2.Test2.Proto1.ops._2;
import test.test2.Test2.Proto1.roles.B;
import test.test2.Test2.Proto1.states.B.Proto1_B_10;

public class MyB
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException
	{
		foo();
	}

	public static void foo1() throws IOException, ScribbleRuntimeException
	{
		try (ScribServerSocket ss = new SocketChannelServer(8888))
		{
			while (true)
			{
				Proto1 P1 = new Proto1();
				try (MPSTEndpoint<Proto1, B> b = new MPSTEndpoint<>(P1, B, new ObjectStreamFormatter()))
				{
					b.accept(ss, A);

					new Proto1_B_1(b).branch(A, new MyHandler());
				}
				catch (Exception e)//ScribbleRuntimeException | IOException | ExecutionException | InterruptedException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public static void foo() throws IOException, ScribbleRuntimeException
	{
		try (ScribServerSocket ss = new SocketChannelServer(8888))
		{
			while (true)
			{
				Proto1 P1 = new Proto1();
				try (Proto1_B b = new Proto1_B(P1, B, new ObjectStreamFormatter()))
				{
					b.accept(ss, A);
					b.register(Proto1_B_10.id,
							(op, sess) -> { System.out.println("Done 1"); return null; },
							(op, sess) -> { System.out.println("Done 2"); return null; });

					Future<Void> f = b.run();
					f.get();
					
					System.out.println("B done");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}

class MyHandler implements Proto1_B_1_Handler
{
	@Override
	public void receive(EndSocket schan, _1 op) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException
	{
		System.out.println("Done 1");
	}

	@Override
	public void receive(EndSocket schan, _2 op) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException
	{
		System.out.println("Done 1");
	}
}
