package org.scribble.net.scribsock;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.ScribMessage;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.Role;

public abstract class SendSocket extends LinearSocket
{
	protected SendSocket(SessionEndpoint ep)
	{
		super(ep);
	}

	protected void writeScribMessage(Role peer, Op op, Object... payload) throws IOException, ScribbleRuntimeException
	{
		//System.out.println("Write: " + msg);

		use();
		ScribMessage msg = new ScribMessage(op, payload);
		this.ep.getSocketEndpoint(peer).writeMessageAndFlush(msg);
	}
}
