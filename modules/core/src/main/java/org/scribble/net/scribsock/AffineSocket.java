package org.scribble.net.scribsock;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.session.SessionEndpoint;

public abstract class AffineSocket extends LinearSocket
{
	protected AffineSocket(SessionEndpoint ep)
	{
		super(ep);
	}

	@Override
	protected void close() throws ScribbleRuntimeException
	{
		this.ep.close();  // No used check
	}
}
