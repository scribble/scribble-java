package betty16.lec1.httplong.message;

import org.scribble.sesstype.name.Op;

public abstract class HeaderField extends HttpLongMessage
{
	private static final long serialVersionUID = 1L;

	public HeaderField(Op name, String value)
	{
		super(name, ": " + value + " ");
	}
}
