package test.http.message.client;

import test.http.Http;
import test.http.message.HeaderField;

public class AcceptEncoding extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public AcceptEncoding(String text)
	{
		super(Http.ACCEPTE, text);
	}
}
