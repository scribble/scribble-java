package org.scribble.net.scribsock;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.session.SessionEndpoint;

public class EndSocket extends ScribSocket
{
	public EndSocket(SessionEndpoint se)
	{
		super(se);
	}

	public void end() throws ScribbleRuntimeException
	{
		/*super.use();
		this.se.setCompleted();*/
		if (!this.se.isCompleted())
		{
			throw new ScribbleRuntimeException("Session not completed: " + this.se);
		}
		this.se.close();
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
