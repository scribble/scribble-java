package org.scribble.net.scribsock;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.ScribMessage;
import org.scribble.net.session.Session;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;

public abstract class SendSocket<S extends Session, R extends Role> extends LinearSocket<S, R>
{
	protected SendSocket(SessionEndpoint<S, R> ep)
	{
		super(ep);
	}

	protected void writeScribMessage(Role peer, Op op, Object... payload) throws IOException, ScribbleRuntimeException
	{
		writeScribMessage(peer, new ScribMessage(op, payload));
	}

	protected void writeScribMessage(Role peer, ScribMessage msg) throws IOException, ScribbleRuntimeException
	{
		use();
		/*SocketEndpoint se = this.ep.getSocketEndpoint(peer);
		se.writeMessageAndFlush(msg);*/
		this.se.getChannelEndpoint(peer).write(msg);
	}
	
	//public void wrap(Callable<? extends BinaryChannelEndpoint> cons, Role role, String host, int port) throws ScribbleRuntimeException, UnknownHostException, IOException

	/*// Right to limit reconnect to send state? Or generalise and manually handle pending incoming messages on old connection? 
	public void reconnect(Callable<? extends BinaryChannelEndpoint> cons, Role role, String host, int port) throws ScribbleRuntimeException, UnknownHostException, IOException
	{
		// Following InitSocket.connect
		use();
		
		...FIXME: close old and make new
		
		try
		{
			BinaryChannelEndpoint c = cons.call();
			c.initClient(se, host, port);
			this.se.reregister(role, c);
		}
		catch (Exception e)
		{
			if (e instanceof IOException)
			{
				throw (IOException) e;
			}
			throw new IOException(e);
		}
	}*/
}
