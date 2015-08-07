//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -session Foo -d modules/validation/src/main/java
//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -api Foo A -d modules/validation/src/main/java

package test.test1;

import org.scribble.net.Buff;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;


public class MyC
{
	public static void main(String[] args) throws Exception
	{
		Proto1 adder = new Proto1();
		//SessionEndpoint se = adder.project(Proto1.C, new ObjectStreamFormatter());
		SessionEndpoint se = adder.project(Proto1.C, new ObjectStreamFormatter());
		
		try (Proto1_C_0 s0 = new Proto1_C_0(se))
		{
			System.out.println("c0: ");

			s0.connect(SocketChannelEndpoint::new, Proto1.S, "localhost", 8888);
			
			System.out.println("c1: ");

			Proto1_C_1 s1 = s0.init();

			System.out.println("c2: ");
			
			//s1.receive(Proto1._1).send(Proto1.S, Proto1._2);
			
			Proto1_C_6 s6 = s1.branch();
			switch (s6.op)
			{
				case _1:
				{
					Buff<Integer> b1 = new Buff<>();
					Buff<Future_Proto1_C_4> b2 = new Buff<>();

					s6.receive(Proto1._1, b1)
					  .async(Proto1._2, b2)
					  .send(Proto1.S, Proto1._3, 3);
			
					System.out.println("Client 1: ");
					System.out.println("Client 2: " + b2.val.sync().pay1);

					break;
				}
				case _4:
				{
					s6.receive(Proto1._4)
					  .async(Proto1._5)
					  .receive(Proto1._6);
			
					break;
				}
			}

			System.out.println("Client 3: ");
		}
	}
}
