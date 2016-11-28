package http.longvers.message.server;

import http.longvers.HttpLong.Http.Http;
import http.longvers.message.HeaderField;

public class ContentType extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public ContentType(String type)
	{
		super(Http.CONTENTT, type);
	}
}
