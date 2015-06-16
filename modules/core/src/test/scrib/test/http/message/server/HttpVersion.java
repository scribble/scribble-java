package test.http.message.server;

import test.http.Http;
import test.http.message.HttpMessage;
import test.http.message.HttpMessageFormatter;

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
