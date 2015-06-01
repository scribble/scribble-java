package test.http.message.client;

import test.http.Http;
import test.http.message.HeaderField;

public class UserAgent extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public UserAgent(String text)
	{
		super(Http.USERA, text);
	}
}
