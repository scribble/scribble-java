package demo.bettybook.http.longv.message.client;

import demo.bettybook.http.longv.HttpLong.Http.Http;
import demo.bettybook.http.longv.message.HeaderField;

public class DoNotTrack extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public DoNotTrack(int val)
	{
		super(Http.DNT, Integer.toString(val));
	}
}
