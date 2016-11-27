package http.longvers.message.server;

import http.longvers.HttpLong.Http.Http;
import http.longvers.message.HeaderField;

public class StrictTransportSecurity extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public StrictTransportSecurity(String server)
	{
		super(Http.STRICTTS, server);
		//super(Http.STRICT_TRANSPORT_SECURITY, server);
	}
}
