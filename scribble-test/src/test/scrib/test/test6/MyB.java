package test.test6;

import static test.test6.Test6.Proto1.Proto1.A;
import static test.test6.Test6.Proto1.Proto1.B;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.function.Function;

import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.ScribServerSocket;
import org.scribble.runtime.net.SocketChannelServer;

import test.test6.Test6.Proto1.Proto1;
import test.test6.Test6.Proto1.handlers.B.Proto1_B;
import test.test6.Test6.Proto1.handlers.B.Proto1_B_19_Branch;
import test.test6.Test6.Proto1.handlers.states.B.Proto1_B_19;
import test.test6.Test6.Proto1.handlers.states.B.Proto1_B_21;
import test.test6.Test6.Proto1.handlers.states.B.Proto1_B_22;
import test.test6.Test6.Proto1.handlers.states.B.Proto1_B_23;
import test.test6.Test6.Proto1.handlers.states.B.messages.Proto1_B_21_Message;
import test.test6.Test6.Proto1.handlers.states.B.messages.Proto1_B_21__2;

public class MyB
{
	public static void main(String[] args) throws IOException
	{
		class MyHandler extends Proto1_B_19_Branch<String[]>
		{
			@Override
			public void receive(String[] data, test.test6.Test6.Proto1.roles.A peer, test.test6.Test6.Proto1.ops._1 op, Integer arg1)
			{
				System.out.println("(B) received 1: " + arg1 + ", " + (data[0] += "a"));
				
			}
		}
		
		try (ScribServerSocket ss = new SocketChannelServer(8888))
		{
			while (true)
			{
				Proto1 P1 = new Proto1();
				String[] data = new String[] { "" };
				try (Proto1_B<String[]> b = new Proto1_B<>(P1, B, new ObjectStreamFormatter(), data))
				{
					b.accept(ss, A);

					MyHandler h1 = new MyHandler();
					Function<String[], Proto1_B_21_Message> h2 = x -> new Proto1_B_21__2(A, x[0] += "a");
					
					b.icallback(Proto1_B_19.id, h1)
					 .icallback(Proto1_B_21.id, h2)
					 .icallback(Proto1_B_22.id, h1)
					 .icallback(Proto1_B_23.id, h2);

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
