package demo.http.shortvers.message;

import org.scribble.net.ScribMessage;
import org.scribble.sesstype.name.Op;

import demo.http.shortvers.HttpShort.Http.Http;

public abstract class HttpMessage extends ScribMessage
{
	private static final long serialVersionUID = 1L;

	public static final String GET = "GET";
	public static final String HTTP = "HTTP";

	public static final String CRLF = "\r\n";

	public HttpMessage(Op op, String m)
	{
		super(op, m);
	}
	
	public String getHeadersAndBody()
	{
		return (this.payload.length == 0) ? "" : (String) this.payload[0];
	}

	public byte[] toBytes()
	{
		// FIXME: factor better with Request/Response (e.g. " " after op, terminal CRLF, etc)
		return (getOpString(this.op) + getHeadersAndBody()).getBytes(HttpMessageFormatter.cs);  // Can give "utf-8" as arg directly
	}
	
	@Override
	public String toString()
	{
		return new String(toBytes());
	}
	
	protected static String getOpString(Op op)
	{
		if (op.equals(Http.REQUEST))
		{
			return HttpMessage.GET;
		}
		else if (op.equals(Http.RESPONSE))
		{
			return HttpMessage.HTTP;
		}
		else
		{
			throw new RuntimeException("TODO: " + op);
		}
	}
}
