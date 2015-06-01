package test.http.message.server;

import test.http.Http;
import test.http.message.HeaderField;

public class Date extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Date(String date)
	{
		super(Http.DATE, date);
	}
}
