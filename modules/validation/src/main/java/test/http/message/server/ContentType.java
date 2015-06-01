package test.http.message.server;

import test.http.Http;
import test.http.message.HeaderField;

public class ContentType extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public ContentType(String type)
	{
		super(Http.CONTENTT, type);
	}
}
