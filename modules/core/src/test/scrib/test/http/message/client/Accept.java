package test.http.message.client;

import test.http.Http;
import test.http.message.HeaderField;

public class Accept extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Accept(String text)
	{
		super(Http.ACCEPT, text);
	}
}