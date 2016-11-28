package demo.fase17.travel2;

import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.A;
import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.C;
import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.S;
import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.confirm;
import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.query;
import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.quote;
import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.accpt;
import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.port;

import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.ExplicitEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2;
import demo.fase17.travel2.TravelAgent2.TravelAgent2.channels.C.TravelAgent2_C_1;
import demo.fase17.travel2.TravelAgent2.TravelAgent2.channels.C.TravelAgent2_C_2;
import demo.fase17.travel2.TravelAgent2.TravelAgent2.roles.C;

public class Travel2C
{
	public void run() throws Exception
	{
		String[] queries = { "aaa", "bbb", "ccc" };
		
		TravelAgent2 sess = new TravelAgent2();
		try (ExplicitEndpoint<TravelAgent2, C> se = new ExplicitEndpoint<>(sess, C, new ObjectStreamFormatter()))
		{	
			Buf<Integer> b = new Buf<>();
			TravelAgent2_C_2 C2 = new TravelAgent2_C_1(se)
				.connect(A, SocketChannelEndpoint::new, "localhost", 8888);

			for (int i = 0; i < queries.length; i++)
			{
				C2 = C2.send(A, query, queries[i]).receive(A, quote, b);
			}

			C2.send(A, accpt)
				.receive(A, port, b)
				.connect(S, SocketChannelEndpoint::new, "localhost", (Integer) b.val)  // FIXME: connect message
				.receive(S, confirm, b);
			
			System.out.println("(C) confirm: " + b.val);
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		new Travel2C().run();
	}
}
