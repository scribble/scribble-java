package test.test1;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buff;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.EndSocket;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.SessionEndpoint;

public class MyS
{
	public static void main(String[] args) throws IOException, ScribbleRuntimeException
	{
		try (ScribServerSocket ss = new SocketChannelServer(8888))
		{
			Buff<Integer> i1 = new Buff<>();
			Buff<Integer> i2 = new Buff<>();

			while (true)
			{
				Proto1 foo = new Proto1();
				SessionEndpoint se = foo.project(Proto1.S, ss, new ObjectStreamFormatter());
				Proto1_S_0 init = new Proto1_S_0(se);
				init.accept(Proto1.C);

				try (Proto1_S_0 s0 = init)
				{
					Proto1_S_1 s1 = s0.init();

					s1.receive(Proto1.C, Proto1._1).branch(Proto1.C, new Handler());
					System.out.println("1");
				}
				catch (Exception e)//ScribbleRuntimeException | IOException | ExecutionException | InterruptedException | ClassNotFoundException e)
				{
					System.out.println("2");
					e.printStackTrace();
				}
				System.out.println("3");
			}
		}
	}
}

class Handler implements Proto1_S_2_Handler
{
	@Override
	public void receive(EndSocket schan, _4 op) throws ScribbleRuntimeException, IOException
	{
		System.out.println("Done");
		//schan.end();
	}

	@Override
	public void receive(Proto1_S_3 schan, _2 op, Buff<? super Integer> b) throws ScribbleRuntimeException, IOException
	{
		System.out.println("Redo: " + b.val);
		try
		{
			schan.send(Proto1.C, Proto1._3, 456).async(Proto1.C, Proto1._1).branch(Proto1.C, this);
		}
		catch (ClassNotFoundException | ExecutionException | InterruptedException e)
		{
			throw new IOException(e);
		}
	}
}
