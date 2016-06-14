package demo.http.shortvers.message.client;

import demo.http.shortvers.HttpShort.Http.Http;
import demo.http.shortvers.message.HttpMessage;

public class Request extends HttpMessage
{
	private static final long serialVersionUID = 1L;

	//public static final String GET = "GET";
	//public static final String HTTP = "HTTP";
	public static final String HOST = "Host";

	public static final String USER_AGENT = "User-Agent";
	public static final String ACCEPT = "Accept";
	public static final String ACCEPT_LANGUAGE = "Accept-Language";
	public static final String ACCEPT_ENCODING = "Accept-Encoding";
	public static final String DO_NOT_TRACK = "DNT";     
	public static final String CONNECTION = "Connection";

	public Request(String get, String http, String host, String userA, String accept, String acceptL, String acceptE, String dnt, String connection)
	{
		super(Http.REQUEST, getHeadersAndBody(get, http, host, userA, accept, acceptL, acceptE, dnt, connection));
	}
	
	/*GET /~rhu/ HTTP/1.1
	host: foo.bar.com*/
	public Request(String get, String http, String host)
	{
		this(get, http, host, "", "", "", "", "", "");
	}

	// Empty body 
	protected static String getHeadersAndBody(String get, String http, String host, String userA, String accept, String acceptL, String acceptE, String dnt, String connection) {
		return " "
				+ get + " " + HttpMessage.HTTP + "/" + http + HttpMessage.CRLF
				+ Request.HOST + ": " + host + HttpMessage.CRLF
				+ (userA.isEmpty() ? "" : Request.USER_AGENT + ": " + userA + HttpMessage.CRLF)
				+ (accept.isEmpty() ? "" : Request.ACCEPT + ": " + accept + HttpMessage.CRLF)
				+ (acceptL.isEmpty() ? "" : Request.ACCEPT_LANGUAGE + ": " + acceptL + HttpMessage.CRLF)
				+ (acceptE.isEmpty() ? "" : Request.ACCEPT_ENCODING + ": " + acceptE + HttpMessage.CRLF)
				+ (dnt.isEmpty() ? "" : Request.DO_NOT_TRACK + ": " + dnt + HttpMessage.CRLF)
				+ (connection.isEmpty() ? "" : Request.CONNECTION + ": " + connection + HttpMessage.CRLF)
				+ "" + HttpMessage.CRLF;  // Empty body
	}
}
