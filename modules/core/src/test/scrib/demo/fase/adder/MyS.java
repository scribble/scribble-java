package demo.fase.adder;

import static demo.fase.adder.Adder.Adder.Adder.Add;
import static demo.fase.adder.Adder.Adder.Adder.Bye;
import static demo.fase.adder.Adder.Adder.Adder.C;
import static demo.fase.adder.Adder.Adder.Adder.Res;
import static demo.fase.adder.Adder.Adder.Adder.S;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.SessionEndpoint;

import demo.fase.adder.Adder.Adder.Adder;
import demo.fase.adder.Adder.Adder.channels.S.Adder_S_1;
import demo.fase.adder.Adder.Adder.channels.S.Adder_S_1_Cases;
import demo.fase.adder.Adder.Adder.channels.S.Adder_S_3;
import demo.fase.adder.Adder.Adder.roles.S;

public class MyS
{
	public static void main(String[] args) throws Exception
	{
		try (ScribServerSocket ss = new SocketChannelServer(8888))
		{
			Buf<Integer> i1 = new Buf<>();
			Buf<Integer> i2 = new Buf<>();

			while (true)
			{
				Adder foo = new Adder();
				try (SessionEndpoint<Adder, S> se = new SessionEndpoint<>(foo, S, new ObjectStreamFormatter()))
				{
					se.accept(ss, C);

					add(new Adder_S_1(se), i1, i2).send(C, Bye);
				}
				catch (ScribbleRuntimeException | IOException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	private static Adder_S_3 add(Adder_S_1 s1, Buf<Integer> i1, Buf<Integer> i2) throws Exception
	{
		Adder_S_1_Cases cases = s1.branch(C);
		switch (cases.op)
		{
			case Add:
				return add(
						cases.receive(Add, i1, i2)
						     .send(C, Res, i1.val+i2.val)
						, i1, i2);
			case Bye:
				return cases.receive(Bye);
			default:
				throw new RuntimeException("Will never get here.");
		}
	} 	
}
