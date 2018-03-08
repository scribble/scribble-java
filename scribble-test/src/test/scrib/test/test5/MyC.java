package test.test5;

import static test.test5.Test5.Proto1.Proto1.A;
import static test.test5.Test5.Proto1.Proto1.C;

import java.io.IOException;
import java.util.concurrent.Future;

import org.scribble.runtime.net.ObjectStreamFormatter;
import org.scribble.runtime.net.scribsock.ScribServerSocket;
import org.scribble.runtime.net.scribsock.SocketChannelServer;

import test.test5.Test5.Proto1.Proto1;
import test.test5.Test5.Proto1.handlers.C.Proto1_C;
import test.test5.Test5.Proto1.handlers.C.Proto1_C_22_Branch;
import test.test5.Test5.Proto1.handlers.states.C.Proto1_C_22;
import test.test5.Test5.Proto1.ops._2;
import test.test5.Test5.Proto1.ops._4;
import test.test5.Test5.Proto1.roles.A;

public class MyC
{
	public static void main(String[] args) throws IOException
	{
		class MyHandler extends Proto1_C_22_Branch<Void>
		{
			@Override
			public void receive(Void data, A peer, _2 op, Integer x)
			{
				System.out.println("(C) received 2: " + x);
			}

			@Override
			public void receive(Void data, A peer, _4 op, String x)
			{
				System.out.println("(C) received 4: " + x);
			}
		}
		
		try (ScribServerSocket ss = new SocketChannelServer(9999))
		{
			while (true)
			{
				Proto1 P1 = new Proto1();
				try (Proto1_C<Void> b = new Proto1_C<>(P1, C, new ObjectStreamFormatter(), null))
				{
					b.accept(ss, A);

					b.icallback(Proto1_C_22.id, new MyHandler());

					Future<Void> f = b.run();
					f.get();
					
					System.out.println("(C) end");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
