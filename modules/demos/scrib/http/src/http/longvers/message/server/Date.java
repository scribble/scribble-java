package http.longvers.message.server;

import http.longvers.HttpLong.Http.Http;
import http.longvers.message.HeaderField;

public class Date extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Date(String date)
	{
		super(Http.DATE, date);
	}
}
