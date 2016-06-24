package demo.betty16.lec1.httplong.message.client;

import demo.betty16.lec1.httplong.HttpLong.Http.Http;
import demo.betty16.lec1.httplong.message.HeaderField;

public class UserAgent extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public UserAgent(String text)
	{
		super(Http.USERA, text);
	}
}
