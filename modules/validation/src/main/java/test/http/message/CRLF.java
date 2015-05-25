package test.http.message;

import test.http.Http;

public class CRLF extends HttpMessage
{
	private static final long serialVersionUID = 1L;
	
	public CRLF()
	{
		//super(Http.CRLF, HttpMessage.CRLF);  // No: HttpMessage already appends the CRLF
		super(Http.CRLF, "");
	}
}
