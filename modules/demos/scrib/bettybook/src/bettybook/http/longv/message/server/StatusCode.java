package bettybook.http.longv.message.server;

import org.scribble.sesstype.name.Op;

import bettybook.http.longv.message.HttpLongMessage;

public abstract class StatusCode extends HttpLongMessage
{
	private static final long serialVersionUID = 1L;

	public StatusCode(Op code, String reason)
	{
		super(code, " " + reason);
	}
}
