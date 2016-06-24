package demo.betty16.lec1.httplong.message.server;

import demo.betty16.lec1.httplong.HttpLong.Http.Http;
import demo.betty16.lec1.httplong.message.HeaderField;

public class Via extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Via(String text)
	{
		super(Http.VIA, text);
	}
}
