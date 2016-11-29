package demo.bettybook.http.longv.message.server;

import demo.bettybook.http.longv.HttpLong.Http.Http;
import demo.bettybook.http.longv.message.HeaderField;

public class Vary extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Vary(String text)
	{
		super(Http.VARY, text);
	}
}
