package demo.bettybook.http.longv.message.server;

import demo.bettybook.http.longv.HttpLong.Http.Http;
import demo.bettybook.http.longv.message.HeaderField;

public class Via extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Via(String text)
	{
		super(Http.Via, text);
	}
}
