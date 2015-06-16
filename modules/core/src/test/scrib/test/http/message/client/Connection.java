package test.http.message.client;

import test.http.Http;
import test.http.message.HeaderField;

public class Connection extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Connection(String text)
	{
		super(Http.CONNECTION, text);
	}
}
