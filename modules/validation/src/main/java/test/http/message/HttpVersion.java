package test.http.message;

import test.http.Http;

public class HttpVersion extends HttpMessage
{
	private static final long serialVersionUID = 1L;

	public HttpVersion(String version)
	{
		super(Http.HTTPV, version);
	}

	@Override
	public byte[] toBytes()
	{
		return (getOpString(op) + getBody() + " ").getBytes(HttpMessageFormatter.cs);
	}
}
