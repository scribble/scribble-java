package test.test3;

import static test.test3.Test3.Proto1.Proto1.B;
import static test.test3.Test3.Proto1.Proto1.C;

import java.io.IOException;
import java.util.concurrent.Future;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.runtime.net.ObjectStreamFormatter;
import org.scribble.runtime.net.scribsock.ScribServerSocket;
import org.scribble.runtime.net.scribsock.SocketChannelServer;

import test.test3.Test3.Proto1.Proto1;
import test.test3.Test3.Proto1.handlers.C.Proto1_C;
import test.test3.Test3.Proto1.handlers.C.Proto1_C_22_Branch;
import test.test3.Test3.Proto1.handlers.states.C.Proto1_C_22;
import test.test3.Test3.Proto1.ops._2;
import test.test3.Test3.Proto1.ops._4;

public class MyC
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException
	{
		class MyHandler extends Proto1_C_22_Branch
		{
			@Override
			public void receive(Object data, _2 op, Integer x)
			{
				System.out.println("(C) received 2: " + x);
			}

			@Override
			public void receive(Object data, _4 op, String x)
			{
				System.out.println("(C) received 4: " + x);
			}
		}
		
		try (ScribServerSocket ss = new SocketChannelServer(9999))
		{
			while (true)
			{
				Proto1 P1 = new Proto1();
				try (Proto1_C b = new Proto1_C(P1, C, new ObjectStreamFormatter(), null))
				{
					b.accept(ss, B);
					b.register(Proto1_C_22.id, new MyHandler());

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
