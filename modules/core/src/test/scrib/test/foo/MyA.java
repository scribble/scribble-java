//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -session Foo -d modules/validation/src/main/java
//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -api Foo A -d modules/validation/src/main/java

package test.foo;

import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.SessionEndpoint;


public class MyA
{
	public static void main(String[] args) throws Exception
	{
		//Buff<Integer> i1 = new Buff<>(0);
		
		Foo foo = new Foo();
		SessionEndpoint se = foo.project(Foo.A, new ObjectStreamFormatter());
		
		try (Foo_A_0 s0 = new Foo_A_0(se))
		{
			s0.connect(Foo.B, "localhost", 8888);
			s0.connect(Foo.C, "localhost", 9999);
			Foo_A_1 s1 = s0.init();
			
			s1.send(Foo.C, Foo._1)
			  .send(Foo.B, Foo._2)
			  .end();
		}
	}
}
