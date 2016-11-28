package org.scribble.net.scribsock;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.Callable;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.session.BinaryChannelEndpoint;
import org.scribble.net.session.Session;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.sesstype.name.Role;

@Deprecated
public abstract class ConnectSocket<S extends Session, R extends Role> extends LinearSocket<S, R>
{
	protected ConnectSocket(MPSTEndpoint<S, R> ep)
	{
		super(ep);
	}

	/*public void connect(Role role, String host, int port) throws ScribbleRuntimeException, UnknownHostException, IOException
	{
		use();
		Socket s = new Socket(host, port);
		this.ep.register(role, new SocketWrapper(s));
	}*/

	protected void connect(Role role, Callable<? extends BinaryChannelEndpoint> cons, String host, int port) throws ScribbleRuntimeException, UnknownHostException, IOException
	{
		use();
		//this.se.connect(role, cons, host, port);
	}
}
