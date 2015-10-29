package org.scribble.net.scribsock;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Role;

public class EndSocket<R extends Role> extends ScribSocket<R>
{
	public EndSocket(SessionEndpoint<R> se)
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
