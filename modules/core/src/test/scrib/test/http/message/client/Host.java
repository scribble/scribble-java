package test.http.message.client;

import test.http.Http;
import test.http.message.HeaderField;

public class Host extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Host(String host)
	{
		super(Http.HOST, host);
	}
}
