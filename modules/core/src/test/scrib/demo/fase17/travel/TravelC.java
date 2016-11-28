package demo.fase17.travel;

import static demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent.A;
import static demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent.C;
import static demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent.S;
import static demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent.confirm;
import static demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent.payment;
import static demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent.query;
import static demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent.quote;
import static demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent.accpt;

import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.ExplicitEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent;
import demo.fase17.travel.TravelAgent.TravelAgent.channels.C.TravelAgent_C_1;
import demo.fase17.travel.TravelAgent.TravelAgent.channels.C.TravelAgent_C_2;
import demo.fase17.travel.TravelAgent.TravelAgent.roles.C;

public class TravelC
{
	public void run() throws Exception
	{
		String[] queries = { "aaa", "bbb", "ccc" };
		
		TravelAgent sess = new TravelAgent();
		try (ExplicitEndpoint<TravelAgent, C> se = new ExplicitEndpoint<>(sess, C, new ObjectStreamFormatter()))
		{	
			Buf<Integer> b = new Buf<>();
			TravelAgent_C_2 C2 = new TravelAgent_C_1(se)
				.connect(A, SocketChannelEndpoint::new, "localhost", 8888);

			//Stream.of(queries).forEach((q) -> C2.send(A, query, q).receive(A, quote, b));  // C2 not reassigned; exceptions not handled
			for (int i = 0; i < queries.length; i++)
			{
				C2 = C2.send(A, query, queries[i]).receive(A, quote, b);
			}

			C2.connect(S, SocketChannelEndpoint::new, "localhost", 9999)
				.send(S, payment, "efgh")
				.receive(S, confirm, b)
				.send(A, accpt, b.val);  // Forward payment ref number
			
			System.out.println("(C) confirm: " + b.val);
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		new TravelC().run();
	}
}
