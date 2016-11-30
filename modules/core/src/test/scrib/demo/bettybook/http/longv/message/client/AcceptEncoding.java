package demo.bettybook.http.longv.message.client;

import demo.bettybook.http.longv.HttpLong.Http.Http;
import demo.bettybook.http.longv.message.HeaderField;

public class AcceptEncoding extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public AcceptEncoding(String text)
	{
		super(Http.AcceptE, text);
	}
}
