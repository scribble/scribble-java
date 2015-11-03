package demo.http.message.server;

import demo.http.Http.Http.Http;
import demo.http.message.HttpMessage;
import demo.http.message.HttpMessageFormatter;

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
