package demo.http.message.client;

import demo.http.Http.Http.Http;
import demo.http.message.HeaderField;

public class Accept extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Accept(String text)
	{
		super(Http.ACCEPT, text);
	}
}
