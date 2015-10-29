package org.scribble.net.scribsock;

import org.scribble.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Role;

public abstract class BranchSocket<R extends Role> extends ReceiveSocket<R>
{
	protected BranchSocket(SessionEndpoint<R> ep)
	{
		super(ep);
	}
}
