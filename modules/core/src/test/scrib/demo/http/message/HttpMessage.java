package demo.http.message;

import org.scribble.net.ScribMessage;
import org.scribble.sesstype.name.Op;

import demo.http.Http.Http.Http;

// Unlike ScribMessage, HttpMessage is not actually "sent", but we use it as the base class since the socket API takes ScribMessages
public abstract class HttpMessage extends ScribMessage
{
	public static final String GET = "GET";
	public static final String HTTP = "HTTP";
	public static final String HOST = "Host";

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
	public static final String VIA = "Via";
	public static final String ETAG = "ETag";
	public static final String CONTENT_LENGTH = "Content-Length";

	private static final long serialVersionUID = 1L;
	
	protected static final String CRLF = "\r\n";

	public HttpMessage(Op op)
	{
		super(op);
	}

	public HttpMessage(Op op, String body)
	{
		super(op, body);
	}
	
	public String getBody()
	{
		return (this.payload.length == 0) ? "" : (String) this.payload[0];
	}

	public byte[] toBytes()
	{
		return (getOpString(this.op) + getBody() + HttpMessage.CRLF).getBytes(HttpMessageFormatter.cs);  // Can give "utf-8" as arg directly
	}
	
	@Override
	public String toString()
	{
		return new String(toBytes());
	}
	
	protected static String getOpString(Op op)
	{
		if (op.equals(Http.CONTENTL))
		{
			return HttpMessage.CONTENT_LENGTH;
		}
		else if (op.equals(Http.ETAG))
		{
			return HttpMessage.ETAG;
		}
		else if (op.equals(Http.BODY))
		{
			return HttpMessage.CRLF;
		}
		else if (op.equals(Http.VIA))
		{
			return HttpMessage.VIA;
		}
		else if (op.equals(Http.SERVER))
		{
			return HttpMessage.SERVER;
		}
		else if (op.equals(Http.VARY))
		{
			return HttpMessage.VARY;
		}
		else if (op.equals(HttpMessage.CRLF))
		{
			return "";
		}
		else if (op.equals(Http.REQUESTL))  // FIXME: not just GET
		{
			return HttpMessage.GET;
		}
		else if (op.equals(Http.LASTM))
		{
			return HttpMessage.LAST_MODIFIED;
		}
		else if (op.equals(Http.ACCEPTR))
		{
			return HttpMessage.ACCEPT_RANGES;
		}
		else if (op.equals(Http.HOST))
		{
			return HttpMessage.HOST;
		}
		else if (op.equals(Http.HTTPV))
		{
			return HttpMessage.HTTP;
		}
		else if (op.equals(Http._200))
		{
			return HttpMessage._200;
		}
		else if (op.equals(Http._404))
		{
			return HttpMessage._404;
		}
		else if (op.equals(Http.CONTENTT))
		{
			return HttpMessage.CONTENT_TYPE;
		}
		else if (op.equals(Http.DATE))
		{
			return HttpMessage.DATE;
		}
		else
		{
			throw new RuntimeException("TODO: " + op);
		}
	}
}
