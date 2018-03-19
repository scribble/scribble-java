package test.test8;

import static test.test8.Test8.Proto1.Proto1.A;
import static test.test8.Test8.Proto1.Proto1.B;
import static test.test8.Test8.Proto1.Proto1.C;

import java.io.IOException;
import java.util.concurrent.Future;

import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.SocketChannelEndpoint;

import test.test8.Test8.Proto1.Proto1;
import test.test8.Test8.Proto1.callbacks.A.Proto1_A;
import test.test8.Test8.Proto1.callbacks.A.states.Proto1_A_1;
import test.test8.Test8.Proto1.callbacks.A.states.Proto1_A_2;
import test.test8.Test8.Proto1.callbacks.A.states.Proto1_A_3;
import test.test8.Test8.Proto1.callbacks.A.states.Proto1_A_4;
import test.test8.Test8.Proto1.callbacks.A.states.Proto1_A_5;

public class MyA
{
	public static void main(String[] args) throws IOException
	{
		foo();
	}

	public static void foo() throws IOException
	{
		Proto1 P1 = new Proto1();
		try (Proto1_A<int[]> a = new Proto1_A<>(P1, A, new ObjectStreamFormatter(), new int[] { 0 }))
		{
			a.request(B, SocketChannelEndpoint::new, "localhost", 8888);
			a.request(C, SocketChannelEndpoint::new, "localhost", 9999);

			a.icallback(Proto1_A_1.id,
					x -> (x[0]++ < 3) ? new Proto1_A_1.B._1(x[0]) : new Proto1_A_1.C._4("abc")
			);
			a.icallback(Proto1_A_2.id, x -> new Proto1_A_2.B._1(123));
			a.icallback(Proto1_A_3.id, x -> new Proto1_A_3.C._3());
			a.icallback(Proto1_A_4.id, x -> new Proto1_A_4.B._3(456));
			a.icallback(Proto1_A_5.id, x -> new Proto1_A_5.B._4("def"));

			/*a.callback(Proto1_A_1.id, x -> new Proto1_A_1.B._1("abc"));
			a.callback(Proto1_A_1.id, x -> new Proto1_A_1.B._2(123));
			a.callback(Proto1_A_1.id, x -> new Proto1_A_1.C._1(123));
			a.callback(Proto1_A_1.id, x -> new Proto1_A_5.B._4("def"));

			a.icallback(Proto1_A_1.id, x -> new Proto1_A_1.B._1("abc"));
			a.icallback(Proto1_A_1.id, x -> new Proto1_A_1.B._2(123));
			a.icallback(Proto1_A_1.id, x -> new Proto1_A_1.C._1(123));
			a.icallback(Proto1_A_1.id, x -> new Proto1_A_5.B._4("def"));*/

			//a.callback(Proto1_A_2.id, x -> new Proto1_A_1.B._1(123));
			a.icallback(Proto1_A_2.id, x -> new Proto1_A_1.B._1(123));
			
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
