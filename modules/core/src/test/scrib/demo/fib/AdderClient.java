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


public class AdderClient
{
	public static void main(String[] args) throws UnknownHostException, ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	{
		Buf<Integer> i1 = new Buf<>(1);
		Buf<Integer> i2 = new Buf<>(2);
		
		Adder adder = new Adder();
		SessionEndpoint se = adder.project(Adder.C, new ObjectStreamFormatter());
		
		try (Adder_C_0 s0 = new Adder_C_0(se))
		{
			s0.connect(SocketChannelEndpoint::new, Adder.S, "localhost", 8888);
			Adder_C_1 s1 = s0.init();

			s1.send(Adder.S, Adder.ADD, i1.val, i1.val)
			  .receive(Adder.RES, i1)
				.send(Adder.S, Adder.ADD, i1.val, i1.val)
			  .receive(Adder.RES, i1)
			  .send(Adder.S, Adder.BYE)
			  .receive(Adder.BYE);
			
			/*while (i1.val < 100)
			{
				s1 = s1.send(Adder.S, Adder.ADD, i1.val, i1.val).receive(Adder.RES, i1);
			}
			s1.send(Adder.S, Adder.BYE)
				.end();*/

			//fib(i1, i2, s1).end();
			
			System.out.println("Client: " + i1.val);
		}
	}

	/*private static Adder_C_3 fib(Buff<Integer> i1, Buff<Integer> i2, Adder_C_1 s1) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		return (i1.val < 100)
				? fib(i1, i2,
											side(i1, i2, s1.send(Adder.S, Adder.ADD, i1.val, i2.val)).receive(Adder.RES, i2)
							)
				: s1.send(Adder.S, Adder.BYE);
	}
	
	private static Adder_C_2 side(Buff<Integer> i1, Buff<Integer> i2, Adder_C_2 s2)
	{
		i1.val = i2.val;
		System.out.print(i1.val + " ");
		return s2;
	}*/
}
