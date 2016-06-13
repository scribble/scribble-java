package demo.http.longvers.message.server;

import demo.http.longvers.HttpLong.Http.Http;
import demo.http.longvers.message.HeaderField;

public class ETag extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public ETag(String tag)
	{
		super(Http.ETAG, tag);
	}
}
