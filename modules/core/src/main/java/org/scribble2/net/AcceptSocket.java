package org.scribble2.net;

import java.io.IOException;

import org.scribble2.net.session.SessionEndpoint;
import org.scribble2.sesstype.name.Role;
import org.scribble2.util.ScribbleRuntimeException;

public abstract class AcceptSocket extends ScribSocket
{
	protected AcceptSocket(SessionEndpoint ep)
	{
		super(ep);
	}

	public void accept(ScribServerSocket ss, Role role) throws IOException, ScribbleRuntimeException
	{
		use();
		this.ep.register(role, ss.accept());
	}
}
