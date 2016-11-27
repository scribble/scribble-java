package betty16.lec1.httplong.message.server;

import org.scribble.sesstype.name.Op;

import betty16.lec1.httplong.message.HttpLongMessage;

public abstract class StatusCode extends HttpLongMessage
{
	private static final long serialVersionUID = 1L;

	public StatusCode(Op code, String reason)
	{
		super(code, " " + reason);
	}
}
