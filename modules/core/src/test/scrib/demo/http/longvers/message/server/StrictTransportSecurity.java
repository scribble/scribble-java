package demo.http.longvers.message.server;

import demo.http.longvers.HttpLong.Http.Http;
import demo.http.longvers.message.HeaderField;

public class StrictTransportSecurity extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public StrictTransportSecurity(String server)
	{
		super(Http.STRICTTS, server);
		//super(Http.STRICT_TRANSPORT_SECURITY, server);
	}
}
