package demo.bettybook.http.longv.message;

import demo.bettybook.http.longv.HttpLong.Http.Http;

public class Body extends HttpLongMessage
{
	private static final long serialVersionUID = 1L;
	
	public Body(String body)
	{
		super(Http.Body, body);
	}

	@Override
	public byte[] toBytes()
	{
		return (getOpString(this.op) + getBody()).getBytes(HttpLongMessageFormatter.cs);  // opString should be empty
	}
}
