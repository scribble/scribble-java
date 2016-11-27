package http.longvers.message.server;

import http.longvers.HttpLong.Http.Http;
import http.longvers.message.HeaderField;

public class ETag extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public ETag(String tag)
	{
		super(Http.ETAG, tag);
	}
}
