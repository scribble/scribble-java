package org.scribble2.net;

import org.scribble2.net.session.SessionEndpoint;

public abstract class BranchSocket extends ReceiveSocket
{
	protected BranchSocket(SessionEndpoint ep)
	{
		super(ep);
	}
}
