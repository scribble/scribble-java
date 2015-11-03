package demo.http.message.server;

import demo.http.Http.Http.Http;
import demo.http.message.HeaderField;

public class Via extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Via(String text)
	{
		super(Http.VIA, text);
	}
}
