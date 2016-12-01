package bettybook.http.longv.message.server;

import bettybook.http.longv.HttpLong.Http.Http;
import bettybook.http.longv.message.HeaderField;

public class Vary extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Vary(String text)
	{
		super(Http.Vary, text);
	}
}
