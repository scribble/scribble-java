//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -session Foo -d modules/validation/src/main/java
//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -api Foo A -d modules/validation/src/main/java

package demo.fib;

import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.fib.Fib.Adder.Adder;
import demo.fib.Fib.Adder.channels.C.Adder_C_1;
import demo.fib.Fib.Adder.roles.C;


public class MyC
{
	public static void main(String[] args) throws Exception
	{
		Adder adder = new Adder();
		try (SessionEndpoint<Adder, C> se = new SessionEndpoint<>(adder, Adder.C, new ObjectStreamFormatter()))
		{
			se.connect(Adder.S, SocketChannelEndpoint::new, "localhost", 8888);

			Adder_C_1 s1 = new Adder_C_1(se);
			
			//Buff<Integer> buf = new Buff<>(1);
			
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
