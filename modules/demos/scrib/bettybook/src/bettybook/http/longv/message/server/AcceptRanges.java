package bettybook.http.longv.message.server;

import bettybook.http.longv.HttpLong.Http.Http;
import bettybook.http.longv.message.HeaderField;

public class AcceptRanges extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public AcceptRanges(String text)
	{
		super(Http.AcceptR, text);
	}
}
