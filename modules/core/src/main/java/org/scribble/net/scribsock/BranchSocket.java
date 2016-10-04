package org.scribble.net.scribsock;

import org.scribble.net.session.Session;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Role;

public abstract class BranchSocket<S extends Session, R extends Role> extends ReceiveSocket<S, R>
{
	//protected BranchSocket(MPSTEndpoint<S, R> ep)
	protected BranchSocket(SessionEndpoint<S, R> ep)
	{
		super(ep);
	}
}
