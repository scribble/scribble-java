package exercise.calculator;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import exercise.calculator.EProtocol.Calc.Calc;
import exercise.calculator.EProtocol.Calc.channels.S.Calc_S_1;
import exercise.calculator.EProtocol.Calc.roles.S;


public class Server
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException, ExecutionException, InterruptedException, ClassNotFoundException
	{
		Calc calculator = new Calc();
		try (MPSTEndpoint<Calc, S> se = new MPSTEndpoint<>(calculator, Calc.S, new ObjectStreamFormatter()))
		{
			se.connect(Calc.S, SocketChannelEndpoint::new, "localhost", 8888);
			Calc_S_1 s1 = new Calc_S_1(se);
			
			// toto: Implement the rest ...
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
