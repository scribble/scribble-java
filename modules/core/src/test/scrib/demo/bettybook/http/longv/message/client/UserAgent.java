package demo.bettybook.http.longv.message.client;

import demo.bettybook.http.longv.HttpLong.Http.Http;
import demo.bettybook.http.longv.message.HeaderField;

public class UserAgent extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public UserAgent(String text)
	{
		super(Http.UserA, text);
	}
}
