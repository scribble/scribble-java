package org.scribble.net;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Role;

public abstract class ReceiveSocket extends ScribSocket
{
	protected ReceiveSocket(SessionEndpoint ep)
	{
		super(ep);
	}

	protected ScribMessage readScribMessage(Role role) throws ClassNotFoundException, IOException, ScribbleRuntimeException
	{
		use();
		//return this.ep.getSocketWrapper(role).readObject();
		ScribMessage m = this.ep.smf.readMessage(this.ep.getSocketWrapper(role).dis);

		//System.out.println("Read: " + m);

		return m;
	}
}
