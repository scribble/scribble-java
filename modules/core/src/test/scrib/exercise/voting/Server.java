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
