package test.test7;

import static test.test7.Test7.Proto1.Proto1.A;
import static test.test7.Test7.Proto1.Proto1.B;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.function.Function;

import org.scribble.runtime.net.ObjectStreamFormatter;
import org.scribble.runtime.net.session.SocketChannelEndpoint;

import test.test7.Test7.Proto1.Proto1;
import test.test7.Test7.Proto1.handlers.A.Proto1_A;
import test.test7.Test7.Proto1.handlers.states.A.Proto1_A_6;
import test.test7.Test7.Proto1.handlers.states.A.Proto1_A_8;
import test.test7.Test7.Proto1.handlers.states.A.messages.Proto1_A_6_Message;
import test.test7.Test7.Proto1.handlers.states.A.messages.Proto1_A_6__2;;

public class MyA
{
	public static void main(String[] args) throws IOException
	{
		Proto1 P1 = new Proto1();
		String[] data = new String[] { "" };
		try (Proto1_A<String[]> a = new Proto1_A<>(P1, A, new ObjectStreamFormatter(), data))
		{
			a.request(B, SocketChannelEndpoint::new, "localhost", 8888);

			Function<String[], Proto1_A_6_Message> h = x -> new Proto1_A_6__2(B, (x[0] += "a"));

			a.icallback(Proto1_A_6.id, h)
			 .icallback(Proto1_A_8.id, h);
			
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
