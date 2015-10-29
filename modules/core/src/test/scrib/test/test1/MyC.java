//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -session Foo -d modules/validation/src/main/java
//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -api Foo A -d modules/validation/src/main/java

package test.test1;

import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;


public class MyC
{
	public static void main(String[] args) throws Exception
	{
		Proto1 adder = new Proto1();
		SessionEndpoint<C> se = adder.project(Proto1.C, new ObjectStreamFormatter());
		
		try (Proto1_C_0 s0 = new Proto1_C_0(se))
		{
			s0.connect(SocketChannelEndpoint::new, Proto1.S, "localhost", 8888);
			Proto1_C_1 s1 = s0.init();
			
			Proto1_C_2 s2 = s1.send(Proto1.S, Proto1._1);
			for (int i = 0; i < 3; i++)
			{
				s2 = 
					s2.send(Proto1.S, Proto1._2, 123)
					  .receive(Proto1.S, Proto1._3, new Buf<>())
					  .send(Proto1.S, Proto1._1);
			}
			s2.send(Proto1.S, Proto1._4).end();

			/*for (int i = 0; i < 3; i++)
			{
				s1 =
					s1.send(Proto1.S, Proto1._1)
					  .send(Proto1.S, Proto1._2, 123)
					  .receive(Proto1.S, Proto1._3, new Buff<>());
			}
			s1.send(Proto1.S, Proto1._1).send(Proto1.S, Proto1._4).end();*/
		}
	}
}
