package http.longvers.message.client;

import http.longvers.HttpLong.Http.Http;
import http.longvers.message.HeaderField;

public class UserAgent extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public UserAgent(String text)
	{
		super(Http.USERA, text);
	}
}
