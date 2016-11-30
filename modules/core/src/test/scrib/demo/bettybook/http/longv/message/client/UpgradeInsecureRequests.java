package demo.bettybook.http.longv.message.client;

import demo.bettybook.http.longv.HttpLong.Http.Http;
import demo.bettybook.http.longv.message.HeaderField;

public class UpgradeInsecureRequests extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public UpgradeInsecureRequests(int val)
	{
		super(Http.UpgradeIR, Integer.toString(val));
	}
}
