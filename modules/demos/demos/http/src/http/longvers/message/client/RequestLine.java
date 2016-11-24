package http.longvers.message.client;

import http.longvers.HttpLong.Http.Http;
import http.longvers.message.StartLine;


// FIXME: not just GET
public class RequestLine extends StartLine
{
	private static final long serialVersionUID = 1L;

	public RequestLine(String reqtarget, String vers)
	{
		super(Http.REQUESTL, " " + reqtarget + " HTTP" + "/" + vers);
	}
}
