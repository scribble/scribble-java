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
package exercise.voting;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import exercise.voting.EProtocol.EVoting.EVoting;
import exercise.voting.EProtocol.EVoting.channels.S.EVoting_S_1;
import exercise.voting.EProtocol.EVoting.channels.S.EVoting_S_3_Cases;
import exercise.voting.EProtocol.EVoting.channels.S.EVoting_S_4;
import exercise.voting.EProtocol.EVoting.roles.S;

public class Server
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException, ExecutionException, InterruptedException, ClassNotFoundException
	{
		EVoting vp = new EVoting();
		try (MPSTEndpoint<EVoting, S> se = new MPSTEndpoint<>(vp, EVoting.S, new ObjectStreamFormatter()))
		{
			se.connect(EVoting.S, SocketChannelEndpoint::new, "localhost", 8888);
			EVoting_S_1 s1 = new EVoting_S_1(se);
			Buf<String> name = new Buf<>(); 
			
			EVoting_S_3_Cases cases = s1.receive(EVoting.V, EVoting.Authenticate, name)
							 			 .send(EVoting.V, EVoting.Ok, name.val).branch(EVoting.V);
			
			Buf<String> vote = new Buf<>(); 
			
			EVoting_S_4 s2 = null;
			switch(cases.op){
				case No: s2 = cases.receive(EVoting.V, EVoting.No, vote); break; 
				case Yes:  s2 = cases.receive(EVoting.V, EVoting.No, vote); break;
			}
			
			s2.send(EVoting.V, EVoting.Result, new Integer(1));
			
			System.out.printf("Done: ~s", vote.val);
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
