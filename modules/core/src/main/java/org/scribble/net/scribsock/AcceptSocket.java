package org.scribble.net.scribsock;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.Session;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.sesstype.name.Role;

public abstract class AcceptSocket<S extends Session, R extends Role> extends LinearSocket<S, R>
{
	//protected AcceptSocket(MPSTEndpoint<S, R> ep)
	protected AcceptSocket(SessionEndpoint<S, R> ep)
	{
		super(ep);
	}

	/*public void accept(ScribServerSocket ss, Role role) throws IOException, ScribbleRuntimeException
	{
		use();
		this.ep.register(role, ss.accept());
	}*/

	protected void accept(ScribServerSocket ss, Role role) throws IOException, ScribbleRuntimeException
	{
		use();
		//this.se.accept(ss, role);  // FIXME: csat
		MPSTEndpoint.accept(this.se, ss, role);
	}
}
