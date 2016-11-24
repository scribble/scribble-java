package fib;

import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import fib.Fib.Adder.Adder;
import fib.Fib.Adder.channels.C.Adder_C_1;
import fib.Fib.Adder.roles.C;

public class MyAdderC
{
	public static void main(String[] args) throws Exception
	{
		Adder adder = new Adder();
		try (MPSTEndpoint<Adder, C> se = new MPSTEndpoint<>(adder, Adder.C, new ObjectStreamFormatter()))
		{
			se.connect(Adder.S, SocketChannelEndpoint::new, "localhost", 8888);

			Adder_C_1 s1 = new Adder_C_1(se);
			
			//System.out.println("Client: " + buf.val);
		}
	}

	/*private static Adder_C_3 side(Buff<Integer> i1, Buff<Integer> i2, Adder_C_3 s3)
	{
		System.out.print(i1.val + " ");
		i1.val = i2.val;
		return s3;
	}*/
}
