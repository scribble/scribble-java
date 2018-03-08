package test.test2;

import static test.test2.Test2.Proto1.Proto1.A;
import static test.test2.Test2.Proto1.Proto1.B;
import static test.test2.Test2.Proto1.Proto1._1;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;

import org.scribble.runtime.net.ObjectStreamFormatter;
import org.scribble.runtime.net.session.MPSTEndpoint;
import org.scribble.runtime.net.session.SocketChannelEndpoint;

import test.test2.Test2.Proto1.Proto1;
import test.test2.Test2.Proto1.channels.A.Proto1_A_1;
import test.test2.Test2.Proto1.handlers.A.Proto1_A;
import test.test2.Test2.Proto1.handlers.states.A.Proto1_A_5;
import test.test2.Test2.Proto1.handlers.states.A.messages.Proto1_A_5__2;
import test.test2.Test2.Proto1.roles.A;;

public class MyA
{
	public static void main(String[] args) throws IOException
	{
		foo3();
	}

	interface I1 { }
	interface I2 { }
	<T extends I1 & I2> void foo (T x)
	{
		
	}
	
	<T extends I1 & I2> void bar(List<T> l)
	{
		
	}
	
	public static void foo3() throws IOException
	{
		Proto1 P1 = new Proto1();
		try (Proto1_A<Void> a = new Proto1_A<>(P1, A, new ObjectStreamFormatter(), null))
		{
			a.request(B, SocketChannelEndpoint::new, "localhost", 8888);
			a.callback(Proto1_A_5.id, x ->
					//new Proto1_A_5__1(B, 123));
					new Proto1_A_5__2(B, "abc"));
			// FIXME: make "structural" nominal types for messages using roles+labs+pays, instead of sids
			
			Future<Void> f = a.run();
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

			new Proto1_A_1(a).send(B, _1);
			//new Proto1_A_1(a).send(B, _2);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
