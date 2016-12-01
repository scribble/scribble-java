package bettybook.http.longv.message.client;

import bettybook.http.longv.HttpLong.Http.Http;
import bettybook.http.longv.message.HeaderField;

public class Connection extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Connection(String text)
	{
		super(Http.Connection, text);
	}
}
