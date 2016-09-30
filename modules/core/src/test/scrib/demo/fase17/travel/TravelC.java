package demo.fase17.travel;

import static demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent.C;
import static demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent.S;

import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent;
import demo.fase17.travel.TravelAgent.TravelAgent.channels.C.TravelAgent_C_1;
import demo.fase17.travel.TravelAgent.TravelAgent.roles.C;

import static demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent.*;

public class TravelC
{
	public void run() throws Exception
	{
		TravelAgent sess = new TravelAgent();
		try (SessionEndpoint<TravelAgent, C> se = new SessionEndpoint<>(sess, C, new ObjectStreamFormatter()))
		{	
			Buf<Integer> b = new Buf<>();
			new TravelAgent_C_1(se)
				.connect(A, SocketChannelEndpoint::new, "localhost", 8888)  // FIXME: state channel constructor already called init
					// FIXME: explicit connects/accepts shouldn't have the (same) init check -- their correctness are "checked" by typing
					// check /not/ initialised? -- differentiate an explicit SessionEndpoint?
				.send(A, query, "abcd")
				.receive(A, quote, b)
				.connect(S, SocketChannelEndpoint::new, "localhost", 9999)
				.send(S, payment, "efgh")
				.receive(S, confirm, b)
				.send(A, yes, b.val);  // Forward payment ref number
			
			System.out.println("(C) payment ref: " + b.val);
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		new TravelC().run();
	}
}
