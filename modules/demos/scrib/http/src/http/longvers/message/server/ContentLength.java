package http.longvers.message.server;

import http.longvers.HttpLong.Http.Http;
import http.longvers.message.HeaderField;

public class ContentLength extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public ContentLength(Integer len)
	{
		super(Http.CONTENTL, len.toString());
	}
}
