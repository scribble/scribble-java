package demo.http.message.client;

import demo.http.Http.Http.Http;
import demo.http.message.HeaderField;

public class AcceptEncoding extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public AcceptEncoding(String text)
	{
		super(Http.ACCEPTE, text);
	}
}
