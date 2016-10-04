package org.scribble.net.session;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.ScribMessageFormatter;
import org.scribble.sesstype.name.Role;

public class ExplicitEndpoint<S extends Session, R extends Role> extends SessionEndpoint<S, R>
{
	public ExplicitEndpoint(S sess, R self, ScribMessageFormatter smf) throws IOException, ScribbleRuntimeException
	{
		super(sess, self, smf);
	}
	
	// FIXME HACK: "init" really only for MPSTEndpoint?  Then refactor into there only
	@Override
	public void init() throws ScribbleRuntimeException
	{
		if (this.init)
		{
			throw new ScribbleRuntimeException("Session endpoint already initialised.");
		}
	}
}
