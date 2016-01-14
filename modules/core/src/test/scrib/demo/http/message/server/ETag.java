package demo.http.message.server;

import demo.http.Http.Http.Http;
import demo.http.message.HeaderField;

public class ETag extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public ETag(String tag)
	{
		super(Http.ETAG, tag);
	}
}
