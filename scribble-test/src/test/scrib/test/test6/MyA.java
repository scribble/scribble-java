package test.test6;

import static test.test6.Test6.Proto1.Proto1.A;
import static test.test6.Test6.Proto1.Proto1.B;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.function.Function;

import org.scribble.runtime.net.ObjectStreamFormatter;
import org.scribble.runtime.net.session.SocketChannelEndpoint;

import test.test6.Test6.Proto1.Proto1;
import test.test6.Test6.Proto1.handlers.A.Proto1_A;
import test.test6.Test6.Proto1.handlers.A.Proto1_A_10_Branch;
import test.test6.Test6.Proto1.handlers.states.A.Proto1_A_10;
import test.test6.Test6.Proto1.handlers.states.A.Proto1_A_11;
import test.test6.Test6.Proto1.handlers.states.A.Proto1_A_12;
import test.test6.Test6.Proto1.handlers.states.A.Proto1_A_8;
import test.test6.Test6.Proto1.handlers.states.A.messages.Proto1_A_8_Message;
import test.test6.Test6.Proto1.handlers.states.A.messages.Proto1_A_8__1;
import test.test6.Test6.Proto1.ops._2;
import test.test6.Test6.Proto1.roles.B;;

public class MyA
{
	public static void main(String[] args) throws IOException
	{
		class MyHandler extends Proto1_A_10_Branch<int[]>
		{
			@Override
			public void receive(int[] data, B peer, _2 op, String arg1)
			{
				System.out.println("(A) received 2: " + arg1 + ", " + data[0]++);
			}
		}
		
		Proto1 P1 = new Proto1();
		try (Proto1_A<int[]> a = new Proto1_A<>(P1, A, new ObjectStreamFormatter(), new int[1]))
		{
			a.request(B, SocketChannelEndpoint::new, "localhost", 8888);

			Function<int[], Proto1_A_8_Message> h1 = x -> new Proto1_A_8__1(B, x[0]++);
			MyHandler h2 = new MyHandler();
			
			a.icallback(Proto1_A_8.id, h1)
			 .icallback(Proto1_A_10.id, h2)
			 .icallback(Proto1_A_11.id, h1)
			 .icallback(Proto1_A_12.id, h2);
			
			Future<Void> f = a.run();
			f.get();

			System.out.println("(A) end");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
