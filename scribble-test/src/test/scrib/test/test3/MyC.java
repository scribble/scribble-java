package test.test3;

import static test.test3.Test3.Proto1.Proto1.B;
import static test.test3.Test3.Proto1.Proto1.C;

import java.io.IOException;
import java.util.concurrent.Future;

import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.ScribServerSocket;
import org.scribble.runtime.net.SocketChannelServer;

import test.test3.Test3.Proto1.Proto1;
import test.test3.Test3.Proto1.callbacks.C.Proto1_C;
import test.test3.Test3.Proto1.callbacks.C.Proto1_C_1_Branch;
import test.test3.Test3.Proto1.callbacks.C.states.Proto1_C_1;
import test.test3.Test3.Proto1.ops._2;
import test.test3.Test3.Proto1.ops._4;
import test.test3.Test3.Proto1.roles.B;

public class MyC
{
	public static void main(String[] args) throws IOException
	{
		class MyHandler extends Proto1_C_1_Branch<Void>
		{
			@Override
			public void receive(Void data, B peer, _2 op, Integer x)
			{
				System.out.println("(C) received 2: " + x);
			}

			@Override
			public void receive(Void data, B peer, _4 op, String x)
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
					b.accept(ss, B);

					b.icallback(Proto1_C_1.id, new MyHandler());

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
