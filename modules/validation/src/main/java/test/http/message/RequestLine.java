package test.http.message;

import test.http.Http;


// FIXME: not just GET
public class RequestLine extends StartLine
{
	private static final long serialVersionUID = 1L;

	public RequestLine(String reqtarget, String vers)
	{
		super(Http.REQUESTL, " " + reqtarget + " HTTP" + "/" + vers);
	}
}
