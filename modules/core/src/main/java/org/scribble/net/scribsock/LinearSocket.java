package org.scribble.net.scribsock;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.session.SessionEndpoint;

// Not AutoClosable -- leave that to InitSocket
public abstract class LinearSocket extends ScribSocket
{
	private boolean used = false;
	
	protected LinearSocket(SessionEndpoint ep)
	{
		super(ep);
	}
	
	protected boolean isUsed()
	{
		return this.used;
	}
	
	protected synchronized void use() throws ScribbleRuntimeException
	{
		if (this.used)
		{
			throw new ScribbleRuntimeException("Linear socket resource already used: " + this.getClass());
		}
		this.used = true;
	}

	// Only triggered by autoclose or explicit close, i.e. not called directly by user
	protected synchronized void close() throws ScribbleRuntimeException
	{
		if (!this.used)
		{
			this.ep.close();
			throw new ScribbleRuntimeException("Socket resource not used: " + this.getClass());
		}
	}
}
