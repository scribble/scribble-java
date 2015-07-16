package org.scribble.net;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.session.SessionEndpoint;



public abstract class EndSocket extends ScribSocket
{
	//private boolean closed = false;

	protected EndSocket(SessionEndpoint ep)
	{
		super(ep);
	}

	public void end() throws ScribbleRuntimeException
	{
		super.use();
		this.ep.setCompleted();
		this.ep.close();
		//this.closed = true;
	}
	
	/*@Override
	public void close()
	{
		if (!this.closed)
		{
			this.ep.close();
		}
	}*/
}
