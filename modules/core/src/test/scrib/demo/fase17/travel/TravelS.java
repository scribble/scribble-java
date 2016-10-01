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
import demo.fase17.travel.TravelAgent.TravelAgent.channels.S.TravelAgent_S_1;
import demo.fase17.travel.TravelAgent.TravelAgent.roles.S;
import static demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent.*;

public class TravelS
{
	public void run() throws Exception
	{
		try (ScribServerSocket ss = new SocketChannelServer(9999))
		{
			Buf<Object> b = new Buf<>();

			//while (true)
			{
				TravelAgent sess = new TravelAgent();
				try (SessionEndpoint<TravelAgent, S> se = new SessionEndpoint<>(sess, S, new ObjectStreamFormatter()))
				{
					new TravelAgent_S_1(se)
						.accept(C, ss)  // FIXME: state channel constructor already called init
						.receive(C, payment, b)
						.send(C, confirm, 4567);
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
		new TravelS().run();
	}
}
