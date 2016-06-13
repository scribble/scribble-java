package demo.http.longvers.message.server;

import demo.http.longvers.Http.Http.Http;
import demo.http.longvers.message.HttpMessage;
import demo.http.longvers.message.HttpMessageFormatter;

public class HttpVersion extends HttpMessage
{
	private static final long serialVersionUID = 1L;

	public HttpVersion(String version)
	{
		super(Http.HTTPV, "/" + version);
	}

	@Override
	public byte[] toBytes()
	{
		return (getOpString(op) + getBody() + " ").getBytes(HttpMessageFormatter.cs);
	}
}
