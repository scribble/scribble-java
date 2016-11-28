package http.longvers.message.client;

import http.longvers.HttpLong.Http.Http;
import http.longvers.message.HeaderField;

public class AcceptEncoding extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public AcceptEncoding(String text)
	{
		super(Http.ACCEPTE, text);
	}
}
