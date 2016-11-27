package betty16.lec1.httplong.message.server;

import betty16.lec1.httplong.HttpLong.Http.Http;
import betty16.lec1.httplong.message.HttpLongMessage;
import betty16.lec1.httplong.message.HttpLongMessageFormatter;

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
