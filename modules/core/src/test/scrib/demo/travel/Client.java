//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -session Foo -d modules/validation/src/main/java
//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' org.scribble2.cli.CommandLine -path modules/validation/src/test/scrib/src modules/validation/src/test/scrib/src/Test.scr -api Foo A -d modules/validation/src/main/java

package demo.travel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.travel.Travel.Booking.Booking;
import demo.travel.Travel.Booking.channels.C.Booking_C_1;
import demo.travel.Travel.Booking.channels.C.Booking_C_5;
import demo.travel.Travel.Booking.roles.C;


public class Client
{
	static int MAX = 500;
	static List<String> QUERIES = IntStream.range(97, 122).mapToObj((i) -> new Character((char) i).toString()).collect(Collectors.toList());

	public static void main(String[] args) throws Exception
	{
		Booking booking = new Booking();
		try (SessionEndpoint<Booking, C> se = new SessionEndpoint<>(booking, Booking.C, new ObjectStreamFormatter()))
		{
			se.connect(Booking.A, SocketChannelEndpoint::new, "localhost", 7777);
			se.connect(Booking.S, SocketChannelEndpoint::new, "localhost", 8888);
			Booking_C_1 s1 = new Booking_C_1(se);

			Buf<Integer> quote = new Buf<>();
			Booking_C_5 s3;
			for (int i = 0; ; i++)
			{
				if (i >= QUERIES.size())
				{
					s3 = s1.send(Booking.A, Booking.No);
					break;
				}
				System.out.println("Querying: " + QUERIES.get(i));
				s1 = s1.send(Booking.A, Booking.Query, QUERIES.get(i))
							 .receive(Booking.A, Booking.Quote, quote);
				System.out.println("Quoted: " + quote.val);
				if (quote.val <= MAX)
				{
					System.out.println("Yes: ");
					s3 = s1.send(Booking.A, Booking.Yes)
						     .send(Booking.S, Booking.Payment, "...")
						     .receive(Booking.S, Booking.Ack);
					break;
				}
			}
			s3.send(Booking.A, Booking.Bye);

			//foo(s1, quote);
			//doQuery(s1, 0, quote).send(Booking.A, Booking.Bye);

			System.out.println("End: ");
		}
	}

	/*private static void foo(Booking_C_1 s1, Buf<Integer> buf) throws Exception
	{
		for (int i = 0; i < QUERIES.size(); i++)  // Stream.forEach not suitable due to Exceptions
		{
			System.out.println("Querying: " + QUERIES.get(i));
			s1 = s1.send(Booking.A, Booking.Query, QUERIES.get(i))
			       .receive(Booking.A, Booking.Quote, buf);
			System.out.println("Quoted: " + buf.val);
			if (buf.val <= MAX)
			{
				System.out.println("Yes: ");
				s1.send(Booking.A, Booking.Yes)
					.send(Booking.S, Booking.Payment, "...")
					.receive(Booking.S, Booking.Ack)
					.send(Booking.A, Booking.Bye);
				return;
			}
		}
		s1.send(Booking.A, Booking.No).send(Booking.A, Booking.Bye);
	}*/
	
	private static Booking_C_5 doQuery(Booking_C_1 s1, int i, Buf<Integer> buf) throws Exception
	{
		return (i >= QUERIES.size())
				? s1.send(Booking.A, Booking.No) 
				//: foo3(s1.send(Booking.A, Booking.Query, println(QUERIES.get(i), QUERIES.get(i))).receive(Booking.Quote, QUOTE), i, println(QUOTE.val, Integer.toString(QUOTE.val)));
				: checkMax(s1.send(Booking.A, Booking.Query, QUERIES.get(i))
						         .receive(Booking.A, Booking.Quote, buf)
					        , i, buf);
	}

	private static Booking_C_5 checkMax(Booking_C_1 s1, int i, Buf<Integer> buf) throws Exception
	{
		return (buf.val <= MAX)
				? s1.send(Booking.A, Booking.Yes)
						.send(Booking.S, Booking.Payment, "...")
						.receive(Booking.S, Booking.Ack)
				: doQuery(s1, i + 1, buf);
	}

	/*private static <T> T println(T t, String m)
	{
		System.out.println(m);
		return t;
	}*/

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
