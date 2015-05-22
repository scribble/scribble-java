package org.scribble2.net;

import java.io.IOException;

import org.scribble2.net.session.SessionEndpoint;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleRuntimeException;

public abstract class ReceiveSocket extends ScribSocket
{
	protected ReceiveSocket(SessionEndpoint ep)
	{
		super(ep);
	}

	protected Object receive(Role role) throws ClassNotFoundException, IOException, ScribbleRuntimeException
	{
		use();
		return this.ep.getSocketEndpoint(role).readObject();
	}
}
