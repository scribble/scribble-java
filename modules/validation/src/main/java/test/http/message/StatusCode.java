package test.http.message;

import org.scribble2.sesstype.name.Op;

public abstract class StatusCode extends HttpMessage
{
	private static final long serialVersionUID = 1L;

	public StatusCode(Op code, String reason)
	{
		super(code, " " + reason);
	}
}
