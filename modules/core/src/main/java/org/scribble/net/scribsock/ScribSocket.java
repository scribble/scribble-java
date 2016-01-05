package org.scribble.net.scribsock;

import org.scribble.net.session.Session;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Role;

public abstract class ScribSocket<S extends Session, R extends Role>
{
	protected SessionEndpoint<S, R> se;
	
	protected ScribSocket(SessionEndpoint<S, R> ep)
	{
		this.se = ep;
	}
}
