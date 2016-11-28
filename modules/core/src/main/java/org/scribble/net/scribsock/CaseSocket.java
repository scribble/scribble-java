package org.scribble.net.scribsock;

import org.scribble.net.session.Session;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Role;

// For "receiving" the payloads after a branch is done
public abstract class CaseSocket<S extends Session, R extends Role> extends LinearSocket<S, R>  // No I/O induced by this socket itself (i.e. not a ReceiveSocket)
{
	//protected CaseSocket(MPSTEndpoint<S, R> ep)
	protected CaseSocket(SessionEndpoint<S, R> ep)
	{
		super(ep);
	}
}
