package demo.travel;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

public class Agent
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException, ExecutionException, InterruptedException
	{
		try (ScribServerSocket ss = new SocketChannelServer(8888))
		{
			while (true)
			{
				int quote = 1000;

				Booking booking = new Booking();
				SessionEndpoint A = booking.project(Booking.A, ss, new ObjectStreamFormatter());
				
				Booking_A_0 init = new Booking_A_0(A);
				init.accept(Booking.C);
				//init.connect(SocketChannelEndpoint::new, Booking.S, "localhost", 8889);
				init.connect(SocketChannelEndpoint::new, Booking.S, "localhost", 9999);
				try (Booking_A_0 s0 = init)
				{
					Booking_A_1 s1 = s0.init();
					Booking_A_6 s6;
					X: while (true)
					{
						s6 = s1.branch();
						switch (s6.op)
						{
							case Query:
								s1 = s6.receive(Booking.Query)
								       .send(Booking.C, Booking.Quote, quote -= 100)
								       .send(Booking.S, Booking._);
								break;
							case No:
								s6.receive(Booking.No)
								  .send(Booking.S, Booking.No);
								break X;
							case Yes:
								System.out.println("Yes: ");
								Booking_A_2 s2 = s6.receive(Booking.Yes);
								s2.send(Booking.S, Booking.Yes);
								break X;
						}
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
