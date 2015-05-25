//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -session Foo -d modules/validation/src/main/java
//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -api Foo A -d modules/validation/src/main/java

package test.fib;

import java.io.IOException;

import org.scribble2.net.Buff;
import org.scribble2.net.ObjectStreamFormatter;
import org.scribble2.net.session.SessionEndpoint;
import org.scribble2.util.ScribbleException;
import org.scribble2.util.ScribbleRuntimeException;


public class A
{
	public static void main(String[] args) throws ScribbleException
	{
		Buff<Integer> i1 = new Buff<>(0);
		Buff<Integer> i2 = new Buff<>(1);
		
		Adder foo = new Adder();
		SessionEndpoint se = foo.project(Adder.A, new ObjectStreamFormatter());
		
		try (Adder_A_0 init = new Adder_A_0(se))
		{
			init.connect(Adder.B, "localhost", 8888);
			Adder_A_1 s1 = init.init();
			
			fib(s1, i1, i2, 0).end();
		}
		catch (ScribbleRuntimeException | IOException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	private static Adder_A_3 fib(Adder_A_1 s1, Buff<Integer> i1, Buff<Integer> i2, int i) throws ClassNotFoundException, ScribbleRuntimeException, IOException
	{
		if (i < 20)
		{
			return fib(side(s1.send(Adder.ADD, i1.val, i2.val), i1, i2).receive(Adder.RES, i2), i1, i2, i + 1);
		}
		else
		{	
			return s1.send(Adder.BYE);
		}
	}
	
	private static Adder_A_2 side(Adder_A_2 s2, Buff<Integer> i1, Buff<Integer> i2)
	{
		System.out.println("A: " + i1.val);
		i1.val = i2.val;
		return s2;
	}
}
