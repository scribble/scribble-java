package org.scribble.net.scribsock;

import org.scribble.net.session.Session;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Role;

@Deprecated  // For now
public abstract class ConnectSocket<S extends Session, R extends Role> extends ScribSocket<S, R>
{
	protected ConnectSocket(SessionEndpoint<S, R> ep)
	{
		super(ep);
	}

	/*public void connect(Role role, String host, int port) throws ScribbleRuntimeException, UnknownHostException, IOException
	{
		use();
		Socket s = new Socket(host, port);
		this.ep.register(role, new SocketWrapper(s));
	}*/
}
