package org.scribble.net.scribsock;

import org.scribble.net.session.SessionEndpoint;

public abstract class BranchReceiveSocket extends ReceiveSocket
{
	protected BranchReceiveSocket(SessionEndpoint ep)
	{
		super(ep);
	}
}
