package bettybook.http.longv.message.server;

import bettybook.http.longv.HttpLong.Http.Http;
import bettybook.http.longv.message.HttpLongMessage;
import bettybook.http.longv.message.HttpLongMessageFormatter;

public class HttpVersion extends HttpLongMessage
{
	private static final long serialVersionUID = 1L;

	public HttpVersion(String version)
	{
		super(Http.HttpV, "/" + version);
	}

	@Override
	public byte[] toBytes()
	{
		return (getOpString(op) + getBody() + " ").getBytes(HttpLongMessageFormatter.cs);
	}
}
