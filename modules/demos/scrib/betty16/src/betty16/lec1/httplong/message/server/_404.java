package betty16.lec1.httplong.message.server;

import betty16.lec1.httplong.HttpLong.Http.Http;

public class _404 extends StatusCode
{
	private static final long serialVersionUID = 1L;

	public _404(String reason)
	{
		super(Http._404, reason);
	}
}
