package test.http.message.server;

import test.http.Http;

public class _404 extends StatusCode
{
	private static final long serialVersionUID = 1L;

	public _404(String reason)
	{
		super(Http._404, reason);
	}
}
