//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -session Foo -d modules/validation/src/main/java
//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -api Foo A -d modules/validation/src/main/java

package demo.travel;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.net.Buff;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;


public class Client
{
	static int MAX = 500;
	static List<String> QUERIES = IntStream.range(97, 122).mapToObj((i) -> new Character((char) i).toString()).collect(Collectors.toList());
	static Buff<Integer> QUOTE = new Buff<>();

	public static void main(String[] args) throws Exception
	{
		Booking booking = new Booking();
		SessionEndpoint C = booking.project(Booking.C, new ObjectStreamFormatter());

		try (Booking_C_0 s0 = new Booking_C_0(C))
		{
			s0.connect(SocketChannelEndpoint::new, Booking.A, "localhost", 8888);
			s0.connect(SocketChannelEndpoint::new, Booking.S, "localhost", 9999);
			Booking_C_1 s1 = s0.init();

			//foo1(s1);
			foo2(s1, 0, QUOTE).send(Booking.A, Booking.Bye);

			System.out.println("End: ");
		}
	}

	private static void foo1(Booking_C_1 s1) throws Exception
	{
		for (int i = 0; ; i++)
		{
			if (i >= QUERIES.size())
			{
				s1.send(Booking.A, Booking.No);
			}
			System.out.println("Querying: " + QUERIES.get(i));
			s1 = s1.send(Booking.A, Booking.Query, QUERIES.get(i))
			       .receive(Booking.Quote, QUOTE);
			System.out.println("Quoted: " + QUOTE.val);
			if (QUOTE.val <= MAX)
			{
				break;
			}
		}
		System.out.println("Yes: ");
		s1.send(Booking.A, Booking.Yes)
		  .send(Booking.S, Booking.Payment, "...")
		  .receive(Booking.Ack)
			.send(Booking.A, Booking.Bye);
	}
	
	private static Booking_C_3 foo2(Booking_C_1 s1, int i, Buff<Integer> buff) throws Exception
	{
		return (i >= QUERIES.size())
				? s1.send(Booking.A, Booking.No) 
				//: foo3(s1.send(Booking.A, Booking.Query, println(QUERIES.get(i), QUERIES.get(i))).receive(Booking.Quote, QUOTE), i, println(QUOTE.val, Integer.toString(QUOTE.val)));
				: foo3(s1.send(Booking.A, Booking.Query, QUERIES.get(i)).receive(Booking.Quote, buff), i, buff);
	}

	private static Booking_C_3 foo3(Booking_C_1 s1, int i, Buff<Integer> buff) throws Exception
	{
		return (buff.val <= MAX)
				? s1.send(Booking.A, Booking.Yes).send(Booking.S, Booking.Payment, "...").receive(Booking.Ack)
				: foo2(s1, i + 1, buff);
	}

	private static <T> T println(T t, String m)
	{
		System.out.println(m);
		return t;
	}

	/*static Optional<Integer> opt;
	private static Booking_C_1 foo2(Booking_C_1 s1, int i) throws Exception
	{
		return (i >= QUERIES.size())
				? ((opt = foo3(s1.send(Booking.A, Booking.Query, QUERIES.get(i)).receive(Booking.Quote, QUOTE), QUOTE.val)).isPresent())
							?  null
							: null
				: s1.send(, op);;
	       /*if (QUOTE.val <= MAX)
	       {
					s1.send(Booking.A, Booking.Yes).send(Booking.S, Booking.Payment, "...").receive(Booking.Ack);
	       }
	       else
	       {
	      	 if ()
	       }* /
	}

	private static Optional<Integer> foo3(Booking_C_1 s1, int quote) throws Exception
	{
		return (quote <= MAX)
				? Optional.of(quote)
				: Optional.empty();
	}*/
}
