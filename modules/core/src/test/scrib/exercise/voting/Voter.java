package exercise.voting;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import exercise.voting.EProtocol.EVoting.EVoting;
import exercise.voting.EProtocol.EVoting.channels.V.EVoting_V_1;
import exercise.voting.EProtocol.EVoting.channels.V.EVoting_V_2_Cases;
import exercise.voting.EProtocol.EVoting.roles.V;


public class Voter
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException, ExecutionException, InterruptedException, ClassNotFoundException
	{
		EVoting vp = new EVoting();
		try (SessionEndpoint<EVoting, V> se = new SessionEndpoint<>(vp, EVoting.V, new ObjectStreamFormatter()))
		{
			se.connect(EVoting.V, SocketChannelEndpoint::new, "localhost", 8888);
			
			EVoting_V_1 s1 = new EVoting_V_1(se);
			
			EVoting_V_2_Cases cases = s1.send(EVoting.S, EVoting.Authenticate, "my name")
					   					 .branch(EVoting.S);
			
			Buf<String> token = new Buf<>(); 
			
			switch(cases.op){
				case Ok:
						Buf<Integer> result = new Buf<>();
						cases.receive(EVoting.Ok, token)
						  	  .send(EVoting.S, EVoting.No, token.val)
						  	  .receive(EVoting.S, EVoting.Result, result);
						
						  System.out.printf("Done: ~s", result);
					break;
				case Reject:
						Buf<String> reason = new Buf<>();  
						cases.receive(EVoting.S, EVoting.Reject,reason);
					break;
			
			}
			
			System.out.println("Done:");
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
