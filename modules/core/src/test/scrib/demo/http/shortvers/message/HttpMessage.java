package demo.http.shortvers.message;

import org.scribble.net.ScribMessage;
import org.scribble.sesstype.name.Op;

import demo.http.shortvers.HttpShort.Http.Http;

// Unlike ScribMessage, HttpMessage is not actually "sent", but we use it as the base class since the socket API takes ScribMessages
public abstract class HttpMessage extends ScribMessage
{
	private static final long serialVersionUID = 1L;

	public static final String GET = "GET";
	public static final String HTTP = "HTTP";
	/*public static final String HOST = "Host";

	public static final String USER_AGENT = "User-Agent";
	public static final String ACCEPT = "Accept";
	public static final String ACCEPT_LANGUAGE = "Accept-Language";
	public static final String ACCEPT_ENCODING = "Accept-Encoding";
	public static final String DO_NOT_TRACK = "DNT";     
	public static final String CONNECTION = "Connection";

	public static final String DATE = "Date";
	public static final String CONTENT_TYPE = "Content-Type";
	public static final String _404 = "404";
	public static final String _200 = "200";
	public static final String ACCEPT_RANGES = "Accept-Ranges";
	public static final String LAST_MODIFIED = "Last-Modified";
	public static final String VARY = "Vary";
	public static final String SERVER = "Server";
	public static final String STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";
	public static final String VIA = "Via";
	public static final String ETAG = "ETag";
	public static final String CONTENT_LENGTH = "Content-Length";*/
	
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
