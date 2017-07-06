/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package travel;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.runtime.net.ObjectStreamFormatter;
import org.scribble.runtime.net.scribsock.ScribServerSocket;
import org.scribble.runtime.net.scribsock.SocketChannelServer;
import org.scribble.runtime.net.session.MPSTEndpoint;
import org.scribble.runtime.net.session.SocketChannelEndpoint;

import travel.Travel.Booking.Booking;
import travel.Travel.Booking.channels.A.Booking_A_1;
import travel.Travel.Booking.channels.A.Booking_A_1_Cases;
import travel.Travel.Booking.roles.A;

public class BookingA
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException, ExecutionException, InterruptedException
	{
		try (ScribServerSocket ss_C = new SocketChannelServer(7777))
		{
			while (true)
			{
				int quote = 1000;

				Booking booking = new Booking();
				try (MPSTEndpoint<Booking, A> se = new MPSTEndpoint<>(booking, Booking.A, new ObjectStreamFormatter()))
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
