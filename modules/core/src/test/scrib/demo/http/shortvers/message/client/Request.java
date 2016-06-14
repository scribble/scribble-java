package demo.http.shortvers.message.client;

import demo.http.shortvers.HttpShort.Http.Http;
import demo.http.shortvers.message.HttpMessage;

public class Request extends HttpMessage
{
	private static final long serialVersionUID = 1L;

	//public static final String GET = "GET";
	//public static final String HTTP = "HTTP";
	public static final String HOST = "Host";

	/*GET /~rhu/ HTTP/1.1
	host: foo.bar.com*/
	public Request(String get, String http, String host)
	{
		super(Http.REQUEST, getHeadersAndBody(get, http, host));
	}
	
	// Empty body 
	protected static String getHeadersAndBody(String get, String http, String host)
	{
		return " "
				+ get + " " + HttpMessage.HTTP + "/" + http + HttpMessage.CRLF
				+ Request.HOST + ": " + host + HttpMessage.CRLF
				+ "" + HttpMessage.CRLF;  // Empty body
	}
}
