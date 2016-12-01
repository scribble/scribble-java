package bettybook.http.longv.message.server;

import bettybook.http.longv.HttpLong.Http.Http;
import bettybook.http.longv.message.HeaderField;

public class Via extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Via(String text)
	{
		super(Http.Via, text);
	}
}
