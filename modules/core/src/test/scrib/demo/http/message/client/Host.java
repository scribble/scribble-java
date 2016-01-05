package demo.http.message.client;

import demo.http.Http.Http.Http;
import demo.http.message.HeaderField;

public class Host extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Host(String host)
	{
		super(Http.HOST, host);
	}
}
