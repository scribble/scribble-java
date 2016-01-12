package demo.http.message;

import org.scribble.sesstype.name.Op;

public abstract class StartLine extends HttpMessage
{
	private static final long serialVersionUID = 1L;

	public StartLine(Op op, String body)
	{
		super(op, body);
	}
}
