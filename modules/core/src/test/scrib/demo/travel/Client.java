//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -session Foo -d modules/validation/src/main/java
//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -api Foo A -d modules/validation/src/main/java

package demo.travel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.net.Buff;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;


public class Client
{
	public static void main(String[] args) throws Exception
	{
		int MAX = 500;
		List<String> queries = IntStream.range(97, 122).mapToObj((i) -> new Character((char) i).toString()).collect(Collectors.toList());

		Booking booking = new Booking();
		SessionEndpoint C = booking.project(Booking.C, new ObjectStreamFormatter());

		try (Booking_C_0 s0 = new Booking_C_0(C))
		{
			s0.connect(SocketChannelEndpoint::new, Booking.A, "localhost", 8888);
			s0.connect(SocketChannelEndpoint::new, Booking.S, "localhost", 9999);
			Booking_C_1 s1 = s0.init();
			Buff<Integer> quote = new Buff<>();
			for (int i = 0; ; i++)
			{
				if (i >= queries.size())
				{
					s1.send(Booking.A, Booking.No);
				}
				System.out.println("Querying: " + queries.get(i));
				s1 = s1.send(Booking.A, Booking.Query, queries.get(i))
				       .receive(Booking.Quote, quote);
				System.out.println("Quoted: " + quote.val);
				if (quote.val < MAX)
				{
					break;
				}
			}
			System.out.println("Yes: ");
			s1.send(Booking.A, Booking.Yes)
			  .send(Booking.S, Booking.Payment, "...")
			  .receive(Booking.Ack);

			System.out.println("End: ");
		}
	}
}
