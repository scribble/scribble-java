package org.scribble2.net;

import java.io.IOException;

import org.scribble2.net.session.ScribbleRuntimeException;
import org.scribble2.sesstype.name.Role;

public abstract class SendSocket extends ScribSocket
{
	protected void send(Role role, Object o) throws IOException, ScribbleRuntimeException
	{
		use();
		this.ep.getSocketEndpoint(role).writeObjectAndFlush(o);
	}
}
