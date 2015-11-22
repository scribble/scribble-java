//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -session Foo -d modules/validation/src/main/java
//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -api Foo A -d modules/validation/src/main/java

package demo.fib;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.fib.Fib.Adder2.Adder2;
import demo.fib.Fib.Adder2.channels.C.Adder2_C_1;
//import demo.fib.Fib.Adder2.channels.C.ioifaces.Receive_C_S_BYE;
import demo.fib.Fib.Adder2.channels.C.ioifaces.Receive_C_S_RES_Integer;
import demo.fib.Fib.Adder2.channels.C.ioifaces.Select_C_S_ADD_Integer_Integer__S_BYE;
import demo.fib.Fib.Adder2.channels.C.ioifaces.Succ_Out_S_BYE;
import demo.fib.Fib.Adder2.roles.C;


public class AdderClient
{
	public static void main(String[] args) throws UnknownHostException, ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	{
		Buf<Integer> i1 = new Buf<>(1);
		//Buf<Integer> i2 = new Buf<>(2);

		Adder2 adder = new Adder2();
		try (SessionEndpoint<Adder2, C> se = new SessionEndpoint<>(adder, Adder2.C, new ObjectStreamFormatter()))
		{	
			se.connect(Adder2.S, SocketChannelEndpoint::new, "localhost", 8888);

			Adder2_C_1 s1 = new Adder2_C_1(se);

			/*s1.send(Adder2.S, Adder2.ADD, i1.val, i1.val)
				.receive(Adder2.S, Adder2.RES, i1)
				.send(Adder2.S, Adder2.ADD, i1.val, i1.val)
				.receive(Adder2.S, Adder2.RES, i1)
				.send(Adder2.S, Adder2.BYE)
				.receive(Adder2.S, Adder2.BYE);*/
			
			/*while (i1.val < 100)
			{
				s1 = s1.send(Adder2.S, Adder2.ADD, i1.val, i1.val).receive(Adder2.S, Adder2.RES, i1);
			}
			s1.send(Adder2.S, Adder2.BYE).receive(Adder2.S, Adder2.BYE)
				.end();*/

			//fib(i1, i2, s1).end();
			
			//foo(s1, i1).to(Receive_C_S_BYE.cast).receive(Adder2.S, Adder2.BYE);
			foo(s1, i1);
			
			System.out.println("Client: " + i1.val);
		}
	}
	
	private static Succ_Out_S_BYE foo(Select_C_S_ADD_Integer_Integer__S_BYE<?, ?> s, Buf<Integer> i) throws ClassNotFoundException, ScribbleRuntimeException, IOException
	//private static EndSocket foo(Select_C_S_ADD_Integer_Integer__S_BYE<?, ?> s, Buf<Integer> i) throws ClassNotFoundException, ScribbleRuntimeException, IOException
	{
		return (i.val < 100)
				? foo(
						s.send(Adder2.S, Adder2.ADD, i.val, i.val)
						 .to(Receive_C_S_RES_Integer.cast)
						 .receive(Adder2.S, Adder2.RES, i)
						 .to(Select_C_S_ADD_Integer_Integer__S_BYE.cast)
					, i)
				: s.send(Adder2.S, Adder2.BYE);
	}

	/*private static EndSocket<Adder2, C> fib(Buf<Integer> i1, Buf<Integer> i2, Adder2_C_1 s1) throws ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	{
		return (i1.val < 100)
				? fib(i1, i2,
							side(i1, i2, s1.send(Adder2.S, Adder2.ADD, i1.val, i2.val)).receive(Adder2.S, Adder2.RES, i2)
					   )
				: s1.send(Adder2.S, Adder2.BYE).receive(Adder2.S, Adder2.BYE);
	}
	
	private static Adder2_C_2 side(Buf<Integer> i1, Buf<Integer> i2, Adder2_C_2 s2)
	{
		i1.val = i2.val;
		System.out.print(i1.val + " ");
		return s2;
	}*/
}
