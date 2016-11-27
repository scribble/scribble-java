package http.longvers.message.client;

import http.longvers.HttpLong.Http.Http;
import http.longvers.message.HeaderField;

public class Accept extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Accept(String text)
	{
		super(Http.ACCEPT, text);
	}
}
