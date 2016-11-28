package org.scribble.net.scribsock;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.session.Session;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Role;

public abstract class EndSocket<S extends Session, R extends Role> extends ScribSocket<S, R>
{
	//public EndSocket(SessionEndpoint<S, R> se, boolean dummy)
	//protected EndSocket(MPSTEndpoint<S, R> se)
	protected EndSocket(SessionEndpoint<S, R> se)
	{
		super(se);
	}

	public void end() throws ScribbleRuntimeException
	{
		/*super.use();
		this.se.setCompleted();*/
		/*if (!this.se.isCompleted())  // Disabled for user implementations of the Handle interface -- No: unnecessary -- and if EndSocket has been returned, then session must be complete anyway...
		{
			throw new ScribbleRuntimeException("Session not completed: " + this.se);
		}*/
		this.se.setCompleted();
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
