package test.test1;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
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
			Buf<Integer> i1 = new Buf<>();
			Buf<Integer> i2 = new Buf<>();

			while (true)
			{
				Proto1 foo = new Proto1();
				//SessionEndpoint<S> se = foo.project(Proto1.S, new ObjectStreamFormatter(), ss);
				try (SessionEndpoint<Proto1, S> se = foo.project(Proto1.S, new ObjectStreamFormatter()))
				{
					se.accept(ss, Proto1.C);

					new Proto1_S_1(se).async(Proto1.C, Proto1._1).branch(Proto1.C, new Handler());
				}
				catch (Exception e)//ScribbleRuntimeException | IOException | ExecutionException | InterruptedException | ClassNotFoundException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}

class Handler implements Proto1_S_2_Handler
{
	@Override
	public void receive(EndSocket<Proto1, S> schan, _4 op) throws ScribbleRuntimeException, IOException
	{
		System.out.println("Done");
		schan.end();
	}

	@Override
	public void receive(Proto1_S_3 schan, _2 op, Buf<? super Integer> b) throws ScribbleRuntimeException, IOException
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
