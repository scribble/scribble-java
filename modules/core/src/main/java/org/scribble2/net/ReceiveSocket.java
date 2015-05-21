package org.scribble2.net;

import java.io.IOException;

import org.scribble2.net.session.ScribbleRuntimeException;
import org.scribble2.sesstype.name.Role;

public abstract class ReceiveSocket extends ScribSocket
{
	protected Object receive(Role role) throws ClassNotFoundException, IOException, ScribbleRuntimeException
	{
		use();
		return this.ep.getSocketEndpoint(role).readObject();
	}
}
