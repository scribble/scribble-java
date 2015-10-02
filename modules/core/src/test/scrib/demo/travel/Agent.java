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
				
				Thread.sleep(1000);  // FIXME:
				
				init.connect(SocketChannelEndpoint::new, Booking.S, "localhost", 9999);
				try (Booking_A_0 s0 = init)
				{
					Booking_A_1 s1 = s0.init();
					Booking_A_7 s7;
					X: while (true)
					{
						s7 = s1.branch();
						switch (s7.op)
						{
							case Query:
								s1 = s7.receive(Booking.Query)
								       .send(Booking.C, Booking.Quote, quote -= 100)
								       .send(Booking.S, Booking._);
								break;
							case No:
								s7.receive(Booking.No)
								  .send(Booking.S, Booking.No)
								  .receive(Booking.Bye);
								break X;
							case Yes:
								System.out.println("Yes: ");
								s7.receive(Booking.Yes)
								  .send(Booking.S, Booking.Yes)
								  .receive(Booking.Bye);
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
