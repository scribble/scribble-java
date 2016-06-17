package demo.http.longvers.message;

import org.scribble.sesstype.name.Op;

public abstract class StartLine extends HttpLongMessage
{
	private static final long serialVersionUID = 1L;

	public StartLine(Op op, String body)
	{
		super(op, body);
	}
}
