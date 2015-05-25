package test.http.message;

import test.http.Http;

public class Host extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Host(String host)
	{
		super(Http.HOST, host);
	}
}
