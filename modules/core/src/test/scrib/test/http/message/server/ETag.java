package test.http.message.server;

import test.http.Http;
import test.http.message.HeaderField;

public class ETag extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public ETag(String tag)
	{
		super(Http.ETAG, tag);
	}
}
