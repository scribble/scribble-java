package exercise.calculator.EProtocol.Calc;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import exercise.calculator.EProtocol.Calc.channels.C.Calc_C_1;
import exercise.calculator.EProtocol.Calc.roles.C;



public class Client
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException, ExecutionException, InterruptedException, ClassNotFoundException
	{
		Calc calculator = new Calc();
		try (SessionEndpoint<Calc, C> se = new SessionEndpoint<>(calculator, Calc.C, new ObjectStreamFormatter()))
		{
			se.connect(Calc.C, SocketChannelEndpoint::new, "localhost", 8888);
			
			Calc_C_1 s1 = new Calc_C_1(se);
			
			System.out.println("Done:");
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
