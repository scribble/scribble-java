package org.scribble2.net;

import org.scribble2.net.session.SessionEndpoint;
import org.scribble2.util.ScribbleRuntimeException;



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
		//this.ep.setCompleted();
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
