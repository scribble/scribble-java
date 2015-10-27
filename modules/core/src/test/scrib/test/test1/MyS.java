package test.test1;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buff;
import org.scribble.net.ObjectStreamFormatter;
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

					s1.receive(Proto1._1).branch(new Handler());
				}
				catch (Exception e)//ScribbleRuntimeException | IOException | ExecutionException | InterruptedException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}

class Handler implements Proto1_S_2_IBranch
{
	@Override
	public void receive(_3 op) throws ScribbleRuntimeException, IOException
	{
		System.out.println("Done");
	}

	@Override
	public void receive(Proto1_S_1 schan, _2 op) throws ScribbleRuntimeException, IOException
	{
		try
		{
			schan.receive(Proto1._1).branch(this);
		}
		catch (ClassNotFoundException | ExecutionException | InterruptedException e)
		{
			throw new IOException(e);
		}
	}
}
