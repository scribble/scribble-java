package demo.http.message.server;

import demo.http.Http.Http.Http;
import demo.http.message.HeaderField;

public class Date extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Date(String date)
	{
		super(Http.DATE, date);
	}
}
