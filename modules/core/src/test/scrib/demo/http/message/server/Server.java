package demo.http.message.server;

import demo.http.Http.Http.Http;
import demo.http.message.HeaderField;

public class Server extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Server(String server)
	{
		super(Http.SERVER, server);
	}
}
