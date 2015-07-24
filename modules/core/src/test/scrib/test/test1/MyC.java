//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -session Foo -d modules/validation/src/main/java
//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -api Foo A -d modules/validation/src/main/java

package test.test1;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buff;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.ScribMessage;
import org.scribble.net.session.SessionEndpoint;


public class MyC
{
	public static void main(String[] args) throws UnknownHostException, ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	{
		Buff<Integer> i1 = new Buff<>(1);
		Buff<Integer> i2 = new Buff<>(1);
		
		Proto1 adder = new Proto1();
		SessionEndpoint se = adder.project(Proto1.C, new ObjectStreamFormatter());
		
		try (Proto1_C_0 s0 = new Proto1_C_0(se))
		{
			s0.connect(Proto1.S, "localhost", 8888);
			Proto1_C_1 s1 = s0.init();
			
			Buff<ScribMessage> b2 = new Buff<>();
			Buff<ScribMessage> b3 = new Buff<>();
			Buff<ScribMessage> b4 = new Buff<>();
			
			s1.send(Proto1.S, Proto1._1)
			  .async()  .. should take Buff<ScribFuture> as arg -- next is not needed
			  .next(b2)
			  .async()
			  .next(b3)
			  .async()
			  .next(b4)
			  .send(Proto1.S, Proto1._5);

			System.out.println("b2: " + b2.val);
			System.out.println("b3: " + b3.val);
			System.out.println("b4: " + b4.val);
			
			//System.out.println("Client: " + i1.val);
		}
	}

	/*private static Proto1_C_3 side(Buff<Integer> i1, Buff<Integer> i2, Proto1_C_3 s3)
	{
		System.out.print(i1.val + " ");
		i1.val = i2.val;
		return s3;
	}*/
}
