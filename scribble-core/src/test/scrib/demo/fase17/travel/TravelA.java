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
package demo.fase17.travel;

import static demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent.A;
import static demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent.C;
import static demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent.query;
import static demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent.quote;
import static demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent.accpt;
import static demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent.reject;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.runtime.net.Buf;
import org.scribble.runtime.net.ObjectStreamFormatter;
import org.scribble.runtime.net.scribsock.ScribServerSocket;
import org.scribble.runtime.net.scribsock.SocketChannelServer;
import org.scribble.runtime.net.session.ExplicitEndpoint;

import demo.fase17.travel.TravelAgent.TravelAgent.TravelAgent;
import demo.fase17.travel.TravelAgent.TravelAgent.channels.A.EndSocket;
import demo.fase17.travel.TravelAgent.TravelAgent.channels.A.TravelAgent_A_1;
import demo.fase17.travel.TravelAgent.TravelAgent.channels.A.TravelAgent_A_2_Cases;
import demo.fase17.travel.TravelAgent.TravelAgent.roles.A;

public class TravelA
{
	public void run() throws Exception
	{
		try (ScribServerSocket ss = new SocketChannelServer(8888))
		{
			while (true)
			{
				TravelAgent sess = new TravelAgent();
				try (ExplicitEndpoint<TravelAgent, A> se = new ExplicitEndpoint<>(sess, A, new ObjectStreamFormatter()))
				{
					run(
						new TravelAgent_A_1(se)
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

	private EndSocket run(TravelAgent_A_2_Cases A2) throws Exception
	{
		Buf<Object> b = new Buf<>();
		switch (A2.op)
		{
			case query:  A2 = A2.receive(query, b).send(C, quote, 1234).branch(C); System.out.println("(A) query: " + b.val); return run(A2);
			case accpt:  EndSocket end = A2.receive(accpt, b); System.out.println("(A) yes: " + b.val); return end;
			case reject: return A2.receive(reject);
			default:     throw new RuntimeException("Shouldn't get in here: " + A2.op);
		}
	}

	public static void main(String[] args) throws Exception
	{
		new TravelA().run();
	}
}
