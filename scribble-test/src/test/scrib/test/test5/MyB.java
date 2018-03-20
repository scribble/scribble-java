package test.test5;

import static test.test5.Test5.Proto1.Proto1.A;
import static test.test5.Test5.Proto1.Proto1.B;

import java.io.IOException;
import java.util.concurrent.Future;

import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.ScribServerSocket;
import org.scribble.runtime.net.SocketChannelServer;

import test.test5.Test5.Proto1.Proto1;
import test.test5.Test5.Proto1.callbacks.B.Proto1_B;
import test.test5.Test5.Proto1.callbacks.B.Proto1_B_1_Branch;
import test.test5.Test5.Proto1.callbacks.B.states.Proto1_B_1;
import test.test5.Test5.Proto1.ops._1;
import test.test5.Test5.Proto1.ops._4;
import test.test5.Test5.Proto1.roles.A;

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
			public void receive(int[] data, A peer, _4 op, String x)
			{
				data[0]++;
				System.out.println("(B) received 4: " + data[0] + ", " + x);
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

					b.icallback(Proto1_B_1.id, new MyHandler());

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
