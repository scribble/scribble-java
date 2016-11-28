package http.longvers.message.client;

import http.longvers.HttpLong.Http.Http;
import http.longvers.message.HeaderField;

public class DoNotTrack extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public DoNotTrack(int val)
	{
		super(Http.DNT, Integer.toString(val));
	}
}
