package org.scribble.net.scribsock;

import org.scribble.net.session.SessionEndpoint;

public abstract class ScribSocket
{
	protected SessionEndpoint ep;
	
	protected ScribSocket(SessionEndpoint ep)
	{
		this.ep = ep;
	}
}
