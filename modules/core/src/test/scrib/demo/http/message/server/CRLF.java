package demo.http.message.server;

import demo.http.message.HttpMessage;

@Deprecated
public class CRLF extends HttpMessage
{
	private static final long serialVersionUID = 1L;
	
	public CRLF()
	{
		//super(Http.CRLF, HttpMessage.CRLF);  // No: HttpMessage already appends the CRLF
		//super(Http.CRLF, "");
		super(null, "");
	}
}
