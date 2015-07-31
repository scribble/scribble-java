package org.scribble.net.scribsock;

import org.scribble.net.session.SessionEndpoint;

// For "receiving" the payloads after a branch is done
public abstract class BranchReceiveSocket extends LinearSocket  // No I/O induced by this socket itself (i.e. not a ReceiveSocket)
{
	protected BranchReceiveSocket(SessionEndpoint ep)
	{
		super(ep);
	}
}
