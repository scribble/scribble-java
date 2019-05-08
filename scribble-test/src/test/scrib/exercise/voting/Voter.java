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
import exercise.voting.EProtocol.EVoting.channels.V.EVoting_V_1;
import exercise.voting.EProtocol.EVoting.channels.V.EVoting_V_2_Cases;
import exercise.voting.EProtocol.EVoting.channels.V.EVoting_V_4;
import exercise.voting.EProtocol.EVoting.roles.V;


public class Voter
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException, ExecutionException, InterruptedException, ClassNotFoundException
	{
		EVoting vp = new EVoting();
		try (MPSTEndpoint<EVoting, V> se = new MPSTEndpoint<>(vp, EVoting.V, new ObjectStreamFormatter()))
		{
			se.connect(EVoting.V, SocketChannelEndpoint::new, "localhost", 8888);
			String name = "my name"; 
			EVoting_V_1 s1 = new EVoting_V_1(se);
			
			/*
			EVoting_V_2_Cases cases = s1.receive(EVoting.S, EVoting.Authenticate, name)
										 .branch(EVoting.S);;
			 
			EVoting_V_4 s3 = null; 
			
			switch(cases.op){
				case Ok: Buf<String> token = new Buf<>(); 
						 s3 = cases.receive(EVoting.Ok, token)
						  	       .send(EVoting.S, EVoting.Maybe, token);
					break;
				case Reject:
						Buf<String> reason = new Buf<>();  
						s3 = cases.receive(EVoting.S, EVoting.Reject,reason)
							 .send(EVoting.S, EVoting.Yes, name);
					break;
			
			}
			Buf<String> results = new Buf<>();
			s3.receive(EVoting.S, EVoting.Result, results);
			*/
			
			System.out.println("Done:");
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
