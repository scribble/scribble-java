package test.http.message.server;

import test.http.Http;
import test.http.message.HeaderField;

public class AcceptRanges extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public AcceptRanges(String text)
	{
		super(Http.ACCEPTR, text);
	}
}
