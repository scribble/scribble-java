package demo.http.longvers.message.server;

import org.scribble.sesstype.name.Op;

import demo.http.longvers.message.HttpMessage;

public abstract class StatusCode extends HttpMessage
{
	private static final long serialVersionUID = 1L;

	public StatusCode(Op code, String reason)
	{
		super(code, " " + reason);
	}
}
