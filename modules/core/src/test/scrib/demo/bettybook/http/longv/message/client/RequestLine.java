package demo.bettybook.http.longv.message.client;

import demo.bettybook.http.longv.HttpLong.Http.Http;
import demo.bettybook.http.longv.message.StartLine;


// FIXME: not just GET
public class RequestLine extends StartLine
{
	private static final long serialVersionUID = 1L;

	public RequestLine(String reqtarget, String vers)
	{
		super(Http.RequestL, " " + reqtarget + " HTTP" + "/" + vers);
	}
}
