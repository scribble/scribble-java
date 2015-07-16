package org.scribble.net;

import org.scribble.net.session.SessionEndpoint;

public abstract class BranchSocket extends ReceiveSocket
{
	protected BranchSocket(SessionEndpoint ep)
	{
		super(ep);
	}
}
