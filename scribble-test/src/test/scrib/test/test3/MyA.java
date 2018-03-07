package test.test3;

import static test.test3.Test3.Proto1.Proto1.A;
import static test.test3.Test3.Proto1.Proto1.B;

import java.io.IOException;
import java.util.concurrent.Future;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.runtime.net.ObjectStreamFormatter;
import org.scribble.runtime.net.session.SocketChannelEndpoint;

import test.test3.Test3.Proto1.Proto1;
import test.test3.Test3.Proto1.handlers.A.Proto1_A;
import test.test3.Test3.Proto1.handlers.states.A.Proto1_A_6;
import test.test3.Test3.Proto1.handlers.states.A.messages.Proto1_A_6__1;
import test.test3.Test3.Proto1.handlers.states.A.messages.Proto1_A_6__3;;

public class MyA
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException
	{
		Proto1 P1 = new Proto1();
		try (Proto1_A a = new Proto1_A(P1, A, new ObjectStreamFormatter(), new int[1]))
		{
			a.request(B, SocketChannelEndpoint::new, "localhost", 8888);
			a.register(Proto1_A_6.id, x -> (((int[]) x)[0]++ < 3) ? new Proto1_A_6__1(B, 123) : new Proto1_A_6__3(B, "abc"));
			
			Future<Void> f = a.run();
			f.get();

			System.out.println("A done");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
