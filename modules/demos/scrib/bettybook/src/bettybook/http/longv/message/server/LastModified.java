package bettybook.http.longv.message.server;

import bettybook.http.longv.HttpLong.Http.Http;
import bettybook.http.longv.message.HeaderField;

public class LastModified extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public LastModified(String date)
	{
		super(Http.LastM, date);
	}
}
