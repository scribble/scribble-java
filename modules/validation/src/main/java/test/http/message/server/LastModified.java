package test.http.message.server;

import test.http.Http;
import test.http.message.HeaderField;

public class LastModified extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public LastModified(String date)
	{
		super(Http.LASTM, date);
	}
}
