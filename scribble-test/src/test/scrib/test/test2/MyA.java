package test.test2;

import static test.test2.Test2.Proto1.Proto1.A;
import static test.test2.Test2.Proto1.Proto1.B;
import static test.test2.Test2.Proto1.Proto1._1;

import java.io.IOException;
import java.util.concurrent.Future;

import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.SocketChannelEndpoint;
import org.scribble.runtime.session.MPSTEndpoint;

import test.test2.Test2.Proto1.Proto1;
import test.test2.Test2.Proto1.callbacks.A.Proto1_A;
import test.test2.Test2.Proto1.callbacks.A.states.Proto1_A_1;
import test.test2.Test2.Proto1.roles.A;

// TODO: test interop between ED API and SC API (also for sig messages)
public class MyA
{
	public static void main(String[] args) throws IOException
	{
		//foo1();
		foo3();
	}

	public static void foo3() throws IOException
	{
		Proto1 P1 = new Proto1();
		try (Proto1_A<Void> a = new Proto1_A<>(P1, A, new ObjectStreamFormatter(), null))
		{
			a.request(B, SocketChannelEndpoint::new, "localhost", 8888);

			//TODO: test choice at A { 1() from A to B; 2() from A to C; } or { 1() from A to C; 3() from A to B; }

			a.icallback(Proto1_A_1.id, x ->
					new Proto1_A_1.B._1(123)
					//new Proto1_A_5.B._2("abc")
			);
					// new IntPay<>(B, _1, 123));  // IntPay<B, _1>  -- or StrPay<B, _2>
			
			Future<Void> f = a.run();  // Even if registration checks still dynamic, much better to check at "session init" than partway through session after potential side effects
			f.get();

			System.out.println("(A) end");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/*public static void foo2() throws IOException, ScribbleRuntimeException
	{
		Proto1 P1 = new Proto1();
		try (Proto1_A a = new Proto1_A(P1, A, new ObjectStreamFormatter()))
		{
			a.request(B, SocketChannelEndpoint::new, "localhost", 8888);
			//a.register(Proto1_A_5.id, x -> new ScribEvent(B, _1));
			a.register(Proto1_A_5.id, x -> new ScribEvent(B, _2));
			// FIXME: type by Op (state-specific op enum for state-specific scribevent?); sort out session object
			
			Future<Void> f = a.run();
			f.get();

			System.out.println("A done");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}*/

	public static void foo1()
	{
		Proto1 P1 = new Proto1();
		try (MPSTEndpoint<Proto1, A> a = new MPSTEndpoint<>(P1, A, new ObjectStreamFormatter()))
		{
			a.request(B, SocketChannelEndpoint::new, "localhost", 8888);

			test.test2.Test2.Proto1.statechans.A.Proto1_A_1 s
					= new test.test2.Test2.Proto1.statechans.A.Proto1_A_1(a);
			s.send(B, _1, 123);

			//new Proto1_A_1(a).send(B, _2, "abc");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
