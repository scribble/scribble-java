package demo.http.message;

import org.scribble.sesstype.name.Op;

public abstract class HeaderField extends HttpMessage
{
	private static final long serialVersionUID = 1L;

	public HeaderField(Op name, String value)
	{
		super(name, ": " + value + " ");
	}
}
