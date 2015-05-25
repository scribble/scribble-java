package test.http.message;

import test.http.Http;

public class Date extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Date(String date)
	{
		super(Http.DATE, date);
	}
}
