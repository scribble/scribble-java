package org.scribble.net.scribsock;

import org.scribble.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Role;

public abstract class ScribSocket<R extends Role>
{
	protected SessionEndpoint<R> se;
	
	protected ScribSocket(SessionEndpoint<R> ep)
	{
		this.se = ep;
	}
}
