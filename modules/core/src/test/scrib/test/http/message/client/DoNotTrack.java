package test.http.message.client;

import test.http.Http;
import test.http.message.HeaderField;

public class DoNotTrack extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public DoNotTrack(int val)
	{
		super(Http.DNT, Integer.toString(val));
	}
}
