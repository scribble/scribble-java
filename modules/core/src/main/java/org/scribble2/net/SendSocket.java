package org.scribble2.net;

import java.io.IOException;

import org.scribble2.net.session.SessionEndpoint;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleRuntimeException;

public abstract class SendSocket extends ScribSocket
{
	protected SendSocket(SessionEndpoint ep)
	{
		super(ep);
	}

	protected void send(Role role, Object o) throws IOException, ScribbleRuntimeException
	{
		use();
		this.ep.getSocketEndpoint(role).writeObjectAndFlush(o);
	}
}
