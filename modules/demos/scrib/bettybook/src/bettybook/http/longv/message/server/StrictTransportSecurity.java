package bettybook.http.longv.message.server;

import bettybook.http.longv.HttpLong.Http.Http;
import bettybook.http.longv.message.HeaderField;

public class StrictTransportSecurity extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public StrictTransportSecurity(String server)
	{
		super(Http.StrictTS, server);
	}
}
