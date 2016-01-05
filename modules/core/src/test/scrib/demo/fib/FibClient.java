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

import demo.fib.Fib.Adder.Adder;
import demo.fib.Fib.Adder.channels.C.Adder_C_1;
import demo.fib.Fib.Adder.channels.C.Adder_C_2;
import demo.fib.Fib.Adder.channels.C.Adder_C_3;
import demo.fib.Fib.Adder.roles.C;

public class FibClient
{
	public static void main(String[] args) throws UnknownHostException, ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	{
		Buf<Integer> i1 = new Buf<>(0);
		Buf<Integer> i2 = new Buf<>(1);
		
		Adder adder = new Adder();
		try (SessionEndpoint<Adder, C> se = new SessionEndpoint<>(adder, Adder.C, new ObjectStreamFormatter()))
		{
			se.connect(Adder.S, SocketChannelEndpoint::new, "localhost", 8888);

			Adder_C_1 s1 = new Adder_C_1(se);

			fib(s1, i1, i2, 0).receive(Adder.S, Adder.BYE);
		}
	}

	private static Adder_C_3 fib(Adder_C_1 s1, Buf<Integer> i1, Buf<Integer> i2, int i) throws ClassNotFoundException, ScribbleRuntimeException, IOException, ExecutionException, InterruptedException
	{
		return (i < 20)
			//? fib(side(s1.send(Adder.S, Adder.ADD, i1.val, i2.val), i1, i2).receive(Adder.RES, i2), i1, i2, i + 1)
			? fib(side(s1.send(Adder.S, Adder.ADD, i1.val, i1.val = i2.val), i1, i2).receive(Adder.S, Adder.RES, i2), i1, i2, i + 1)
			: s1.send(Adder.S, Adder.BYE);
	}
	
	private static Adder_C_2 side(Adder_C_2 s2, Buf<Integer> i1, Buf<Integer> i2)
	{
		System.out.print(i1.val + " ");
		//i1.val = i2.val;
		return s2;
	}
}
