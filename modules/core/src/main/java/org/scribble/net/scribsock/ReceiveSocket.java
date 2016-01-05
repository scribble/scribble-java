package org.scribble.net.scribsock;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.ScribMessage;
import org.scribble.net.session.Session;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Role;

public abstract class ReceiveSocket<S extends Session, R extends Role> extends LinearSocket<S, R>
{
	private CompletableFuture<ScribMessage> fut;

	protected ReceiveSocket(SessionEndpoint<S, R> se)
	{
		super(se);
	}

	protected ScribMessage readScribMessage(Role peer) throws ClassNotFoundException, IOException, ScribbleRuntimeException
	{
		try
		{
			//ScribMessage m = this.ep.smf.readMessage(this.ep.getSocketEndpoint(role).dis);
			ScribMessage m = getFuture(peer).get();

			//System.out.println("Read: " + m);

			return m;
		}
		catch (InterruptedException e)
		{
			throw new IOException(e);
		}
		catch (ExecutionException e)
		{	
			// The Future converts the RuntimeScribbleException wrapper for the underlying IOException into an ExecutionException
			throw new IOException(e);
		}
	}
	
	protected boolean isDone(Role peer)
	{
		//return !this.ep.getInputQueues().isEmpty(peer);
		return (this.fut == null) || (this.fut.isDone());
	}
	
	protected CompletableFuture<ScribMessage> getFuture(Role peer) throws ScribbleRuntimeException
	{
		use();
		//this.fut = this.ep.getInputQueues().getFuture(peer);
		return this.se.getChannelEndpoint(peer).getFuture();
	}
}
