package http.longvers.message.server;

import http.longvers.HttpLong.Http.Http;

public class _404 extends StatusCode
{
	private static final long serialVersionUID = 1L;

	public _404(String reason)
	{
		super(Http._404, reason);
	}
}
