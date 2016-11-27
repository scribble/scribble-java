package http.longvers.message.server;

import http.longvers.HttpLong.Http.Http;
import http.longvers.message.HeaderField;

public class Via extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Via(String text)
	{
		super(Http.VIA, text);
	}
}
