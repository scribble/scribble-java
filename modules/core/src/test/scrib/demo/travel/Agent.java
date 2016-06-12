package demo.travel;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.travel.Travel.Booking.Booking;
import demo.travel.Travel.Booking.channels.A.Booking_A_1;
import demo.travel.Travel.Booking.channels.A.Booking_A_1_Cases;
import demo.travel.Travel.Booking.roles.A;

public class Agent
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException, ExecutionException, InterruptedException
	{
		try (ScribServerSocket ss_C = new SocketChannelServer(7777))
		{
			while (true)
			{
				int quote = 1000;

				Booking booking = new Booking();
				try (SessionEndpoint<Booking, A> se = new SessionEndpoint<>(booking, Booking.A, new ObjectStreamFormatter()))
				{
					se.accept(ss_C, Booking.C);
					
					//Thread.sleep(1000);  // FIXME: ensure S is ready
					
					se.connect(Booking.S, SocketChannelEndpoint::new, "localhost", 9999);

					Booking_A_1 s1 = new Booking_A_1(se);
					Booking_A_1_Cases s1cases;
					X: while (true)
					{
						s1cases = s1.branch(Booking.C);
						switch (s1cases.op)
						{
							case Query:
								s1 = s1cases.receive(Booking.Query)
								       .send(Booking.C, Booking.Quote, quote -= 100)
								       .send(Booking.S, Booking.Dummy);
								break;
							case No:
								s1cases.receive(Booking.No)
								  .send(Booking.S, Booking.No)
								  .receive(Booking.C, Booking.Bye);
								break X;
							case Yes:
								System.out.println("Yes: ");
								s1cases.receive(Booking.Yes)
								  .send(Booking.S, Booking.Yes)
								  .receive(Booking.C, Booking.Bye);
								break X;
						}
					}

					System.out.println("Done:");
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
