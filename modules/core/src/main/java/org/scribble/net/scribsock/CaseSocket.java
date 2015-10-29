package org.scribble.net.scribsock;

import org.scribble.net.session.SessionEndpoint;

// For "receiving" the payloads after a branch is done
public abstract class CaseSocket extends LinearSocket  // No I/O induced by this socket itself (i.e. not a ReceiveSocket)
{
	protected CaseSocket(SessionEndpoint ep)
	{
		super(ep);
	}
}
