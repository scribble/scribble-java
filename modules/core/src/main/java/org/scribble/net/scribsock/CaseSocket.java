package org.scribble.net.scribsock;

import org.scribble.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Role;

// For "receiving" the payloads after a branch is done
public abstract class CaseSocket<R extends Role> extends LinearSocket<R>  // No I/O induced by this socket itself (i.e. not a ReceiveSocket)
{
	protected CaseSocket(SessionEndpoint<R> ep)
	{
		super(ep);
	}
}
