package bettybook.http.longv.message.client;

import bettybook.http.longv.HttpLong.Http.Http;
import bettybook.http.longv.message.StartLine;


// FIXME: not just GET
public class RequestLine extends StartLine
{
	private static final long serialVersionUID = 1L;

	public RequestLine(String reqtarget, String vers)
	{
		super(Http.RequestL, " " + reqtarget + " HTTP" + "/" + vers);
	}
}
