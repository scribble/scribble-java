package org.scribble.net.scribsock;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.ScribMessage;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Role;

public abstract class ReceiveSocket extends AffineSocket
{
	protected ReceiveSocket(SessionEndpoint ep)
	{
		super(ep);
	}

	protected ScribMessage readScribMessage(Role peer) throws ClassNotFoundException, IOException, ScribbleRuntimeException
	{
		use();
		//ScribMessage m = this.ep.smf.readMessage(this.ep.getSocketEndpoint(role).dis);
		ScribMessage m = this.ep.getInputQueues().dequeue(peer);

		//System.out.println("Read: " + m);

		return m;
	}
}
