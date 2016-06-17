package demo.http.longvers.message.server;

import org.scribble.sesstype.name.Op;

import demo.http.longvers.message.HttpLongMessage;

public abstract class StatusCode extends HttpLongMessage
{
	private static final long serialVersionUID = 1L;

	public StatusCode(Op code, String reason)
	{
		super(code, " " + reason);
	}
}
