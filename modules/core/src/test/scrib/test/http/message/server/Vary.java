package test.http.message.server;

import test.http.Http;
import test.http.message.HeaderField;

public class Vary extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Vary(String text)
	{
		super(Http.VARY, text);
	}
}
