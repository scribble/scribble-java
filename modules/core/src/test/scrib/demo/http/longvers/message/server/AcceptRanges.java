package demo.http.longvers.message.server;

import demo.http.longvers.HttpLong.Http.Http;
import demo.http.longvers.message.HeaderField;

public class AcceptRanges extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public AcceptRanges(String text)
	{
		super(Http.ACCEPTR, text);
	}
}
