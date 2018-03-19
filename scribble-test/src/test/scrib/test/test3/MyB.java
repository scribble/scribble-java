package test.test3;

import static test.test3.Test3.Proto1.Proto1.A;
import static test.test3.Test3.Proto1.Proto1.B;
import static test.test3.Test3.Proto1.Proto1.C;

import java.io.IOException;
import java.util.concurrent.Future;

import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.ScribServerSocket;
import org.scribble.runtime.net.SocketChannelEndpoint;
import org.scribble.runtime.net.SocketChannelServer;

import test.test3.Test3.Proto1.Proto1;
import test.test3.Test3.Proto1.callbacks.B.Proto1_B;
import test.test3.Test3.Proto1.callbacks.B.Proto1_B_1_Branch;
import test.test3.Test3.Proto1.callbacks.B.states.Proto1_B_1;
import test.test3.Test3.Proto1.callbacks.B.states.Proto1_B_2;
import test.test3.Test3.Proto1.callbacks.B.states.Proto1_B_3;
import test.test3.Test3.Proto1.ops._1;
import test.test3.Test3.Proto1.ops._3;
import test.test3.Test3.Proto1.roles.A;

public class MyB
{
	public static void main(String[] args) throws IOException
	{
		class MyHandler extends Proto1_B_1_Branch<int[]>
		{
			@Override
			public void receive(int[] data, A peer, _1 op, Integer x)
			{
				data[0]++;
				System.out.println("(B) received 1: " + data[0] + ", " + x);
			}

			@Override
			public void receive(int[] data, A peer, _3 op, String x)
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

					b.icallback(Proto1_B_1.id, new MyHandler())
					 .icallback(Proto1_B_2.id, x -> new Proto1_B_2.C._2(456))
					 .icallback(Proto1_B_3.id, x -> new Proto1_B_3.C._4("def"));
					
					//b.icallback(Proto1_B_2.id, x -> new Proto1_B_3.C._4("def"));

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
