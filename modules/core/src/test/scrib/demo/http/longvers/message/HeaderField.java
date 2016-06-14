package demo.http.longvers.message;

import org.scribble.sesstype.name.Op;

public abstract class HeaderField extends HttpLongMessage
{
	private static final long serialVersionUID = 1L;

	public HeaderField(Op name, String value)
	{
		super(name, ": " + value + " ");
	}
}
