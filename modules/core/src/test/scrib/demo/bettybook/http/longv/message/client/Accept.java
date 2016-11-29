package demo.bettybook.http.longv.message.client;

import demo.bettybook.http.longv.HttpLong.Http.Http;
import demo.bettybook.http.longv.message.HeaderField;

public class Accept extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Accept(String text)
	{
		super(Http.ACCEPT, text);
	}
}
