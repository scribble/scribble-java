package betty16.lec1.httplong.message.server;

import betty16.lec1.httplong.HttpLong.Http.Http;
import betty16.lec1.httplong.message.HeaderField;

public class ETag extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public ETag(String tag)
	{
		super(Http.ETAG, tag);
	}
}
