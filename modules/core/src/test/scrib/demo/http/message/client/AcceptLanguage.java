package demo.http.message.client;

import demo.http.Http.Http.Http;
import demo.http.message.HeaderField;

public class AcceptLanguage extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public AcceptLanguage(String text)
	{
		super(Http.ACCEPTL, text);
	}
}
