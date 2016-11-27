package http.longvers.message.server;

import http.longvers.HttpLong.Http.Http;
import http.longvers.message.HeaderField;

public class Vary extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Vary(String text)
	{
		super(Http.VARY, text);
	}
}
