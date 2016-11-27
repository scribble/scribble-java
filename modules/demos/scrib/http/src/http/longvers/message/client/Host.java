package http.longvers.message.client;

import http.longvers.HttpLong.Http.Http;
import http.longvers.message.HeaderField;

public class Host extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Host(String host)
	{
		super(Http.HOST, host);
	}
}
