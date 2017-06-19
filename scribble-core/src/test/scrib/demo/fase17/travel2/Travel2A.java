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
package demo.fase17.travel2;

import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.A;
import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.C;
import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.S;
import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.accpt;
import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.port;
import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.query;
import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.quote;
import static demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2.reject;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.ExplicitEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.fase17.travel2.TravelAgent2.TravelAgent2.TravelAgent2;
import demo.fase17.travel2.TravelAgent2.TravelAgent2.channels.A.EndSocket;
import demo.fase17.travel2.TravelAgent2.TravelAgent2.channels.A.TravelAgent2_A_1;
import demo.fase17.travel2.TravelAgent2.TravelAgent2.channels.A.TravelAgent2_A_2_Cases;
import demo.fase17.travel2.TravelAgent2.TravelAgent2.roles.A;

public class Travel2A
{
	public void run() throws Exception
	{
		try (ScribServerSocket ss = new SocketChannelServer(8888))
		{
			while (true)
			{
				TravelAgent2 sess = new TravelAgent2();
				try (ExplicitEndpoint<TravelAgent2, A> se = new ExplicitEndpoint<>(sess, A, new ObjectStreamFormatter()))
				{
					run(
						new TravelAgent2_A_1(se)
							.accept(C, ss)
							.branch(C));
				}
				catch (ScribbleRuntimeException | IOException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	private EndSocket run(TravelAgent2_A_2_Cases A2) throws Exception
	{
		Buf<Object> b = new Buf<>();
		switch (A2.op)
		{
			case query: A2 = A2.receive(query, b).send(C, quote, 1234).branch(C); System.out.println("(A) query: " + b.val); return run(A2);
			case accpt:   
				//EndSocket end = A2.receive(accpt, b); System.out.println("(A) yes: " + b.val); return end;
				return A2.receive(accpt).connect(S, SocketChannelEndpoint::new, "localhost", 9999)
				  .receive(S, port, b).send(C, port, (Integer) b.val);
			case reject:    return A2.receive(reject);
			default:    throw new RuntimeException("Shouldn't get in here: " + A2.op);
		}
	}

	public static void main(String[] args) throws Exception
	{
		new Travel2A().run();
	}
}
