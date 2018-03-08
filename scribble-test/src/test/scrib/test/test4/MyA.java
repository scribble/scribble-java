package test.test4;

import static test.test4.Test4.Proto1.Proto1.A;
import static test.test4.Test4.Proto1.Proto1.B;

import java.io.IOException;
import java.util.concurrent.Future;

import org.scribble.runtime.net.SocketChannelEndpoint;

import test.test4.Test4.Proto1.Proto1;
import test.test4.Test4.Proto1.handlers.A.Proto1_A;
import test.test4.Test4.Proto1.handlers.states.A.Proto1_A_6;
import test.test4.Test4.Proto1.handlers.states.A.messages.Proto1_A_Bar;
import test.test4.Test4.Proto1.handlers.states.A.messages.Proto1_A_Foo;
import test.test4.sig.Bar;
import test.test4.sig.Foo;
import test.test4.sig.Test4Formatter;;

public class MyA
{
	public static void main(String[] args) throws IOException
	{
		Proto1 P1 = new Proto1();
		try (Proto1_A<int[]> a = new Proto1_A<>(P1, A, new Test4Formatter(), new int[1]))
		{
			a.request(B, SocketChannelEndpoint::new, "localhost", 8888);

			a.icallback(Proto1_A_6.id,
					x -> (x[0]++ < 5) ? new Proto1_A_Foo(B, new Foo("abc")) : new Proto1_A_Bar(B, new Bar(123))
			);
			// FIXME: remove sid from message class for sigs
			
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
