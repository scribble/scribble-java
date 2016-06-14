package demo.http.shortvers.message.server;

import demo.http.shortvers.HttpShort.Http.Http;
import demo.http.shortvers.message.HttpMessage;

public class Response extends HttpMessage
{
	private static final long serialVersionUID = 1L;

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
	public static final String CONTENT_LENGTH = "Content-Length";

	/*HTTP/1.1 200 OK
	Date: Mon, 13 Jun 2016 19:42:34 GMT
	Server: Apache
	Strict-Transport-Security: max-age=31536000; preload; includeSubDomains
	Strict-Transport-Security: max-age=31536000; preload; includeSubDomains  // BUG? (Apache configuration)
	Last-Modified: Thu, 14 Apr 2016 12:46:24 GMT
	ETag: "74a-53071482f6e0f"
	Accept-Ranges: bytes
	Content-Length: 1866
	Vary: Accept-Encoding
	Content-Type: text/html
	Via: 1.1 www.doc.ic.ac.uk*/
	public Response(String httpv, String date, String server, String strictTS, String lastMod,
			String eTag, String acceptR, String contentL, String vary, String contentT, String via, String body)
	{
		super(Http.RESPONSE, getHeadersAndBody(
				httpv, date, server, strictTS, lastMod, eTag, acceptR, contentL, vary, contentT, via, body));
	}

	protected static String getHeadersAndBody(String httpv, String date, String server, String strictTS, String lastMod,
			String eTag, String acceptR, String contentL, String vary, String contentT, String via, String body)
	{
		return "/" + httpv + " 200 OK" + HttpMessage.CRLF  // FIXME: 200 OK?
				+ Response.DATE + ": " + date + HttpMessage.CRLF
				+ Response.SERVER + ": " + server + HttpMessage.CRLF
				+ Response.STRICT_TRANSPORT_SECURITY + ": " + strictTS + HttpMessage.CRLF
				+ Response.LAST_MODIFIED + ": " + lastMod + HttpMessage.CRLF
				+ Response.ETAG + ": " + eTag + HttpMessage.CRLF
				+ Response.ACCEPT_RANGES + ": " + acceptR + HttpMessage.CRLF
				+ Response.CONTENT_LENGTH + ": " + contentL + HttpMessage.CRLF
				+ Response.VARY + ": " + vary + HttpMessage.CRLF
				+ Response.CONTENT_TYPE + ": " + contentT + HttpMessage.CRLF
				+ Response.VIA + ": " + via + HttpMessage.CRLF
				+ HttpMessage.CRLF  // Empty line for end of headers
				+ body + HttpMessage.CRLF;
	}
}
