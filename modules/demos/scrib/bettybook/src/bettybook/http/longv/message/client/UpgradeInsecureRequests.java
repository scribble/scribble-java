package bettybook.http.longv.message.client;

import bettybook.http.longv.HttpLong.Http.Http;
import bettybook.http.longv.message.HeaderField;

public class UpgradeInsecureRequests extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public UpgradeInsecureRequests(int val)
	{
		super(Http.UpgradeIR, Integer.toString(val));
	}
}
