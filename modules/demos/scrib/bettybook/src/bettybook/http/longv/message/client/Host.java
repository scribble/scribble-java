package bettybook.http.longv.message.client;

import bettybook.http.longv.HttpLong.Http.Http;
import bettybook.http.longv.message.HeaderField;

public class Host extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Host(String host)
	{
		super(Http.Host, host);
	}
}
