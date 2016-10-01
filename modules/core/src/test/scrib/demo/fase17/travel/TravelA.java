package demo.fase17.travel;

import static demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent.C;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.SessionEndpoint;

import demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent;
import demo.fase17.travel.TravelAgent.TravelAgent.channels.A.TravelAgent_A_1;
import demo.fase17.travel.TravelAgent.TravelAgent.channels.A.TravelAgent_A_2_Cases;
import demo.fase17.travel.TravelAgent.TravelAgent.roles.A;

import static demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent.*;

public class TravelA
{
	public void run() throws Exception
	{
		try (ScribServerSocket ss = new SocketChannelServer(8888))
		{
			Buf<Object> b = new Buf<>();

			while (true)
			{
				TravelAgent sess = new TravelAgent();
				try (SessionEndpoint<TravelAgent, A> se = new SessionEndpoint<>(sess, A, new ObjectStreamFormatter()))
				{
					TravelAgent_A_2_Cases A2
						= new TravelAgent_A_1(se)
							.accept(C, ss)
							.branch(C);
					switch (A2.op)
					{
						case query: A2 = A2.receive(query, b).send(C, quote, 1234).branch(C); break;
						case yes:   A2.receive(yes, b); System.out.println("(A) payment ref: " + b.val); break;
						case no:    break;
					}
				}
				catch (ScribbleRuntimeException | IOException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		new TravelA().run();
	}
}
