package test.http.message;

import test.http.Http;

public class Body extends HttpMessage
{
	private static final long serialVersionUID = 1L;
	
	public Body(String body)
	{
		super(Http.BODY, body);
	}

	@Override
	public byte[] toBytes()
	{
		return (getOpString(op) + getBody()).getBytes(HttpMessageFormatter.cs);  // opString should be empty
	}
}
