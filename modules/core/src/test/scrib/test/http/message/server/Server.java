package test.http.message.server;

import test.http.Http;
import test.http.message.HeaderField;

public class Server extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Server(String server)
	{
		super(Http.SERVER, server);
	}
}
