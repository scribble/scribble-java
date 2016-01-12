package demo.http.message.client;

import demo.http.Http.Http.Http;
import demo.http.message.HeaderField;

public class Connection extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Connection(String text)
	{
		super(Http.CONNECTION, text);
	}
}
