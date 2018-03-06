package test.test2;

import static test.test2.Test2.Proto1.Proto1.A;
import static test.test2.Test2.Proto1.Proto1.B;
import static test.test2.Test2.Proto1.Proto1._1;
import static test.test2.Test2.Proto1.Proto1._2;

import java.io.IOException;
import java.util.concurrent.Future;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.runtime.net.ObjectStreamFormatter;
import org.scribble.runtime.net.session.MPSTEndpoint;
import org.scribble.runtime.net.session.SocketChannelEndpoint;
import org.scribble.runtime.net.state.ScribEvent;

import test.test2.Test2.Proto1.Proto1;
import test.test2.Test2.Proto1.channels.A.Proto1_A_1;
import test.test2.Test2.Proto1.handlers.A.Proto1_A;
import test.test2.Test2.Proto1.roles.A;
import test.test2.Test2.Proto1.states.A.Proto1_A_5;;

public class MyA
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException
	{
		foo();
	}
	
	public static void foo() throws IOException, ScribbleRuntimeException
	{
		Proto1 P1 = new Proto1();
		try (Proto1_A a = new Proto1_A(P1, A, new ObjectStreamFormatter()))
		{
			a.request(B, SocketChannelEndpoint::new, "localhost", 8888);
			//a.register(Proto1_A_5.id, x -> new ScribEvent(B, _1));
			a.register(Proto1_A_5.id, x -> new ScribEvent(B, _2));
			.// FIXME: type by Op; sort out session object
			
			Future<Void> f = a.run();
			f.get();

			System.out.println("A done");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

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
