package betty16.lec1.httplong.message.client;

import betty16.lec1.httplong.HttpLong.Http.Http;
import betty16.lec1.httplong.message.HeaderField;

public class DoNotTrack extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public DoNotTrack(int val)
	{
		super(Http.DNT, Integer.toString(val));
	}
}
