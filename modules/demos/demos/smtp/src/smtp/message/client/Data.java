package smtp.message.client;

import smtp.Smtp.Smtp.Smtp;
import smtp.message.SmtpMessage;

public class Data extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public Data()
	{
		super(Smtp.Data);
	}
}
