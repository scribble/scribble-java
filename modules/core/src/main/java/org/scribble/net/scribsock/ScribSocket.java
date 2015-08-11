package org.scribble.net.scribsock;

import org.scribble.net.session.SessionEndpoint;

public abstract class ScribSocket
{
	protected SessionEndpoint se;
	
	protected ScribSocket(SessionEndpoint ep)
	{
		this.se = ep;
	}
}
