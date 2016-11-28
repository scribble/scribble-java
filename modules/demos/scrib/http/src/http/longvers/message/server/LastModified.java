package http.longvers.message.server;

import http.longvers.HttpLong.Http.Http;
import http.longvers.message.HeaderField;

public class LastModified extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public LastModified(String date)
	{
		super(Http.LASTM, date);
	}
}
