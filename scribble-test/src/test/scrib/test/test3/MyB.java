package test.test3;

import static test.test3.Test3.Proto1.Proto1.A;
import static test.test3.Test3.Proto1.Proto1.B;
import static test.test3.Test3.Proto1.Proto1.C;

import java.io.IOException;
import java.util.concurrent.Future;

import org.scribble.runtime.net.ObjectStreamFormatter;
import org.scribble.runtime.net.scribsock.ScribServerSocket;
import org.scribble.runtime.net.scribsock.SocketChannelServer;
import org.scribble.runtime.net.session.SocketChannelEndpoint;

import test.test3.Test3.Proto1.Proto1;
import test.test3.Test3.Proto1.handlers.B.Proto1_B;
import test.test3.Test3.Proto1.handlers.B.Proto1_B_14_Branch;
import test.test3.Test3.Proto1.handlers.states.B.Proto1_B_14;
import test.test3.Test3.Proto1.handlers.states.B.Proto1_B_16;
import test.test3.Test3.Proto1.handlers.states.B.Proto1_B_17;
import test.test3.Test3.Proto1.handlers.states.B.messages.Proto1_B_16__2;
import test.test3.Test3.Proto1.handlers.states.B.messages.Proto1_B_17__4;
import test.test3.Test3.Proto1.ops._1;
import test.test3.Test3.Proto1.ops._3;

public class MyB
{
	public static void main(String[] args) throws IOException
	{
		class MyHandler extends Proto1_B_14_Branch<int[]>
		{
			@Override
			public void receive(int[] data, _1 op, Integer x)
			{
				data[0]++;
				System.out.println("(B) received 1: " + data[0] + ", " + x);
			}

			@Override
			public void receive(int[] data, _3 op, String x)
			{
				data[0]++;
				System.out.println("(B) received 3: " + data[0] + ", " + x);
			}
		}
		
		try (ScribServerSocket ss = new SocketChannelServer(8888))
		{
			while (true)
			{
				Proto1 P1 = new Proto1();
				try (Proto1_B<int[]> b = new Proto1_B<>(P1, B, new ObjectStreamFormatter(), new int[1]))
				{
					b.accept(ss, A);
					b.request(C, SocketChannelEndpoint::new, "localhost", 9999);
					b.register(Proto1_B_14.id, new MyHandler());
					b.register(Proto1_B_16.id, x -> new Proto1_B_16__2(C, 456));
					b.register(Proto1_B_17.id, x -> new Proto1_B_17__4(C, "def"));

					Future<Void> f = b.run();
					f.get();
					
					System.out.println("(B) end");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
