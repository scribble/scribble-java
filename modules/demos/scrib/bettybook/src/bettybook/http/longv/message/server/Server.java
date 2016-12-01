package bettybook.http.longv.message.server;

import bettybook.http.longv.HttpLong.Http.Http;
import bettybook.http.longv.message.HeaderField;

public class Server extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Server(String server)
	{
		super(Http.Server, server);
	}
}
