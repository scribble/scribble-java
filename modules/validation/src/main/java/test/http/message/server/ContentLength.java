package test.http.message.server;

import test.http.Http;
import test.http.message.HeaderField;

public class ContentLength extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public ContentLength(Integer len)
	{
		super(Http.CONTENTL, len.toString());
	}
}
