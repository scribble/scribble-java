/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -session Foo -d modules/validation/src/main/java
//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -api Foo A -d modules/validation/src/main/java

package test.test1;

import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import test.test1.Test1.Proto1.Proto1;
import test.test1.Test1.Proto1.channels.C.Proto1_C_1;
import test.test1.Test1.Proto1.channels.C.Proto1_C_2;
import test.test1.Test1.Proto1.roles.C;

public class MyC
{
	public static void main(String[] args) throws Exception
	{
		Proto1 adder = new Proto1();
		try (MPSTEndpoint<Proto1, C> se = new MPSTEndpoint<>(adder, Proto1.C, new ObjectStreamFormatter()))
		{
			se.connect(Proto1.S, SocketChannelEndpoint::new, "localhost", 8888);

			Proto1_C_2 s2 = new Proto1_C_1(se).send(Proto1.S, Proto1._1);
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
