package demo.http.message.server;

import demo.http.Http.Http.Http;
import demo.http.message.HeaderField;

public class ContentLength extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public ContentLength(Integer len)
	{
		super(Http.CONTENTL, len.toString());
	}
}
