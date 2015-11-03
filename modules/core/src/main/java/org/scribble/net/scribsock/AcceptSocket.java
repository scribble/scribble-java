package org.scribble.net.scribsock;

import org.scribble.net.session.Session;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Role;

@Deprecated  // For now
public abstract class AcceptSocket<S extends Session, R extends Role> extends ScribSocket<S, R>
{
	protected AcceptSocket(SessionEndpoint<S, R> ep)
	{
		super(ep);
	}

	/*public void accept(ScribServerSocket ss, Role role) throws IOException, ScribbleRuntimeException
	{
		use();
		this.ep.register(role, ss.accept());
	}*/
}
