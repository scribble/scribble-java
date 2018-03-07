package test.test4;

import static test.test4.Test4.Proto1.Proto1.A;
import static test.test4.Test4.Proto1.Proto1.B;

import java.io.IOException;
import java.util.concurrent.Future;

import org.scribble.runtime.net.scribsock.ScribServerSocket;
import org.scribble.runtime.net.scribsock.SocketChannelServer;

import test.test4.Test4.Proto1.Proto1;
import test.test4.Test4.Proto1.handlers.B.Proto1_B;
import test.test4.Test4.Proto1.handlers.B.Proto1_B_12_Branch;
import test.test4.Test4.Proto1.handlers.states.B.Proto1_B_12;
import test.test4.sig.Bar;
import test.test4.sig.Foo;
import test.test4.sig.Test4Formatter;

public class MyB
{
	public static void main(String[] args) throws IOException
	{
		class MyHandler extends Proto1_B_12_Branch<int[]>
		{
			@Override
			public void receive(int[] data, Foo m)
			{
				data[0]++;
				System.out.println("(B) received Foo: " + m.getBody() + ", " + data[0]);
			}

			@Override
			public void receive(int[] data, Bar m)
			{
				data[0]++;
				System.out.println("(B) received Bar: " + m.getBody() + ", " + data[0]);
			}
		}
		
		try (ScribServerSocket ss = new SocketChannelServer(8888))
		{
			while (true)
			{
				Proto1 P1 = new Proto1();
				try (Proto1_B<int[]> b = new Proto1_B<>(P1, B, new Test4Formatter(), new int[1]))
				{
					b.accept(ss, A);
					b.register(Proto1_B_12.id, new MyHandler());

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
