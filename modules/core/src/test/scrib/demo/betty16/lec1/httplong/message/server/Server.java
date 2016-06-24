package demo.betty16.lec1.httplong.message.server;

import demo.betty16.lec1.httplong.HttpLong.Http.Http;
import demo.betty16.lec1.httplong.message.HeaderField;

public class Server extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public Server(String server)
	{
		super(Http.SERVER, server);
	}
}
