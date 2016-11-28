package demo.fase17.travel2;

import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.C;
import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.S;
import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.A;
import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.confirm;
import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.port;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.ExplicitEndpoint;

import demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2;
import demo.fase17.travel2.TravelAgent2.TravelAgent2.channels.S.TravelAgent2_S_1;
import demo.fase17.travel2.TravelAgent2.TravelAgent2.roles.S;

public class Travel2S
{
	public void run() throws Exception
	{
		try (ScribServerSocket ss = new SocketChannelServer(9999);
				 ScribServerSocket ss2 = new SocketChannelServer(7777))
		{
			Buf<Object> b = new Buf<>();

			while (true)
			{
				TravelAgent2 sess = new TravelAgent2();
				try (ExplicitEndpoint<TravelAgent2, S> se = new ExplicitEndpoint<>(sess, S, new ObjectStreamFormatter()))
				{
					new TravelAgent2_S_1(se)
						.accept(A, ss)
						.send(A, port, 7777)
						.accept(C, ss2)  // FIXME: accept message
						.send(C, confirm, 4567);
					
					System.out.println("(S) payment: " + b.val);
				}
				catch (ScribbleRuntimeException | IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception
	{
		new Travel2S().run();
	}
}
