package test.http.message.client;

import test.http.Http;
import test.http.message.StartLine;


// FIXME: not just GET
public class RequestLine extends StartLine
{
	private static final long serialVersionUID = 1L;

	public RequestLine(String reqtarget, String vers)
	{
		super(Http.REQUESTL, " " + reqtarget + " HTTP" + "/" + vers);
	}
}
