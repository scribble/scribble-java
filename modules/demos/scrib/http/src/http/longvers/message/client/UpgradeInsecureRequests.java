package http.longvers.message.client;

import http.longvers.HttpLong.Http.Http;
import http.longvers.message.HeaderField;

public class UpgradeInsecureRequests extends HeaderField
{
	private static final long serialVersionUID = 1L;

	public UpgradeInsecureRequests(int val)
	{
		super(Http.UPGRADEIR, Integer.toString(val));
	}
}
