package demo.travel;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.SessionEndpoint;

public class Seller
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException, ExecutionException, InterruptedException
	{
		try (ScribServerSocket ss_C = new SocketChannelServer(9999))
				 //ScribServerSocket ss_A = new SocketChannelServer(8889);)
		{
			while (true)
			{
				Booking booking = new Booking();
				SessionEndpoint S = booking.project(Booking.C, new ObjectStreamFormatter(), ss_C);
				//S.register(Booking.A, ss_A);
				
				Booking_S_0 init = new Booking_S_0(S);
				init.accept(Booking.C);
				init.accept(Booking.A);
				Buf<String> payment = new Buf<>();
				try (Booking_S_0 s0 = init)
				{
					Booking_S_1 s1 = s0.init();
					Booking_S_4 s4;
					X: while (true)
					{
						s4 = s1.branch();
						switch (s4.op)
						{
							case _:
								s1 = s4.receive(Booking._);
								break;
							case No:
								s4.receive(Booking.No);
								break X;
							case Yes:
								System.out.println("Yes: ");
								s4.receive(Booking.Yes)
								  .receive(Booking.Payment, payment)
								  .send(Booking.C, Booking.Ack);
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
