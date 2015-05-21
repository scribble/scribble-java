package org.scribble2.net;

import java.io.IOException;

import org.scribble2.net.session.ScribbleRuntimeException;
import org.scribble2.sesstype.name.Role;

public abstract class AcceptSocket extends ScribSocket
{
	public void accept(ScribServerSocket ss, Role role) throws IOException, ScribbleRuntimeException
	{
		use();
		this.ep.register(role, ss.accept());
	}
}
