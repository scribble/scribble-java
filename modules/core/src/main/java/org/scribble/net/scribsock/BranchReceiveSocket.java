package org.scribble.net.scribsock;

import org.scribble.net.session.SessionEndpoint;

public abstract class BranchReceiveSocket extends LinearSocket  // No I/O induced by this socket itself (i.e. not a ReceiveSocket)
{
	protected BranchReceiveSocket(SessionEndpoint ep)
	{
		super(ep);
	}
}
