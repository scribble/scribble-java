package http.longvers.message.server;

import http.longvers.HttpLong.Http.Http;
import http.longvers.message.HttpLongMessage;
import http.longvers.message.HttpLongMessageFormatter;

public class HttpVersion extends HttpLongMessage
{
	private static final long serialVersionUID = 1L;

	public HttpVersion(String version)
	{
		super(Http.HTTPV, "/" + version);
	}

	@Override
	public byte[] toBytes()
	{
		return (getOpString(op) + getBody() + " ").getBytes(HttpLongMessageFormatter.cs);
	}
}
