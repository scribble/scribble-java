package org.scribble2.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.scribble2.net.session.SessionEndpoint;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleRuntimeException;

public abstract class ConnectSocket extends ScribSocket
{
	protected ConnectSocket(SessionEndpoint ep)
	{
		super(ep);
	}

	public void connect(Role role, String host, int port) throws ScribbleRuntimeException, UnknownHostException, IOException
	{
		use();
		Socket s = new Socket(host, port);
		this.ep.register(role, new SocketWrapper(s));
	}
}
