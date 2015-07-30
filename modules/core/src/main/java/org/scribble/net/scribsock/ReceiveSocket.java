package org.scribble.net.scribsock;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

	protected ScribMessage readScribMessage(Role peer) throws ClassNotFoundException, IOException, ScribbleRuntimeException, InterruptedException, ExecutionException
	{
		use();
		//ScribMessage m = this.ep.smf.readMessage(this.ep.getSocketEndpoint(role).dis);
		ScribMessage m = getFuture(peer).get();

		//System.out.println("Read: " + m);

		return m;
	}
	
	protected boolean isDone(Role peer)
	{
		return !this.ep.getInputQueues().isEmpty(peer);
	}
	
	protected CompletableFuture<ScribMessage> getFuture(Role peer)
	{
		return this.ep.getInputQueues().getFuture(peer);
	}
}
