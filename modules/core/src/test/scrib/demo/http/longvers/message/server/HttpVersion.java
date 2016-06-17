package demo.http.longvers.message.server;

import demo.http.longvers.HttpLong.Http.Http;
import demo.http.longvers.message.HttpLongMessage;
import demo.http.longvers.message.HttpLongMessageFormatter;

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
