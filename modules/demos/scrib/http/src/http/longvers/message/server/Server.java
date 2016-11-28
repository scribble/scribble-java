package http.longvers.message.server;

import http.longvers.HttpLong.Http.Http;
import http.longvers.message.HeaderField;

public class Server extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Server(String server)
	{
		super(Http.SERVER, server);
	}
}
