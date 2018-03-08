package test.test5;

import static test.test5.Test5.Proto1.Proto1.A;
import static test.test5.Test5.Proto1.Proto1.B;
import static test.test5.Test5.Proto1.Proto1.C;

import java.io.IOException;
import java.util.concurrent.Future;

import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.SocketChannelEndpoint;

import test.test5.Test5.Proto1.Proto1;
import test.test5.Test5.Proto1.handlers.A.Proto1_A;
import test.test5.Test5.Proto1.handlers.states.A.Proto1_A_10;
import test.test5.Test5.Proto1.handlers.states.A.Proto1_A_11;
import test.test5.Test5.Proto1.handlers.states.A.Proto1_A_8;
import test.test5.Test5.Proto1.handlers.states.A.messages.Proto1_A_10__2;
import test.test5.Test5.Proto1.handlers.states.A.messages.Proto1_A_11__4;
import test.test5.Test5.Proto1.handlers.states.A.messages.Proto1_A_8__1;
import test.test5.Test5.Proto1.handlers.states.A.messages.Proto1_A_8__3;;

public class MyA
{
	public static void main(String[] args) throws IOException
	{
		Proto1 P1 = new Proto1();
		try (Proto1_A<int[]> a = new Proto1_A<>(P1, A, new ObjectStreamFormatter(), new int[1]))
		{
			a.request(B, SocketChannelEndpoint::new, "localhost", 8888);
			a.request(C, SocketChannelEndpoint::new, "localhost", 9999);

			a.icallback(Proto1_A_8.id,  x -> (x[0]++ < 3) ? new Proto1_A_8__1(B, 123) : new Proto1_A_8__3(B, "abc"))
			 .icallback(Proto1_A_10.id, x -> new Proto1_A_10__2(C, 456))
			 .icallback(Proto1_A_11.id, x -> new Proto1_A_11__4(C, "def"));
			
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
