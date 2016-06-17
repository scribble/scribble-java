//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -session Foo -d modules/validation/src/main/java
//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -api Foo A -d modules/validation/src/main/java

package test.foo;

import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import test.foo.Foo.Foo.Foo;
import test.foo.Foo.Foo.channels.A.Foo_A_1;
import test.foo.Foo.Foo.roles.A;


public class MyA
{
	public static void main(String[] args) throws Exception
	{
		//Buff<Integer> i1 = new Buff<>(0);
		
		Foo foo = new Foo();
		try (SessionEndpoint<Foo, A> se = new SessionEndpoint<>(foo, Foo.A, new ObjectStreamFormatter()))
		{
			se.connect(Foo.B, SocketChannelEndpoint::new, "localhost", 8888);
			se.connect(Foo.C, SocketChannelEndpoint::new, "localhost", 9999);

			Foo_A_1 s1 = new Foo_A_1(se);
			s1
			  //.send(Foo.C, Foo._1).send(Foo.B, Foo._2);
			  .send(Foo.B, Foo._1).send(Foo.C, Foo._2);
		}
	}
}
