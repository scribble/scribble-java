package http.longvers.message.client;

import http.longvers.HttpLong.Http.Http;
import http.longvers.message.HeaderField;

public class Connection extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Connection(String text)
	{
		super(Http.CONNECTION, text);
	}
}
