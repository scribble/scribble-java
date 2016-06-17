package org.scribble.net.scribsock;

import org.scribble.net.session.Session;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Role;

@Deprecated
public abstract class AffineSocket<S extends Session, R extends Role> extends LinearSocket<S, R>
{
	protected AffineSocket(SessionEndpoint<S, R> ep)
	{
		super(ep);
	}

	/*@Override
	protected void close() throws ScribbleRuntimeException
	{
		this.ep.close();  // No used check
	}*/
}
