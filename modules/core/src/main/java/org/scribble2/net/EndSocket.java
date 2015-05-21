package org.scribble2.net;

import org.scribble2.net.session.ScribbleRuntimeException;


public abstract class EndSocket extends ScribSocket
{
	public void end() throws ScribbleRuntimeException
	{
		use();
	}
}
