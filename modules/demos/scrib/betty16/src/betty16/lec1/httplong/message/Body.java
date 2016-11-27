package betty16.lec1.httplong.message;

import betty16.lec1.httplong.HttpLong.Http.Http;

public class Body extends HttpLongMessage
{
	private static final long serialVersionUID = 1L;
	
	public Body(String body)
	{
		super(Http.BODY, body);
	}

	@Override
	public byte[] toBytes()
	{
		return (getOpString(this.op) + getBody()).getBytes(HttpLongMessageFormatter.cs);  // opString should be empty
	}
}
