package demo.smtp.message.client;

import demo.smtp.SMTP;
import demo.smtp.message.SmtpMessage;

public class Data extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public Data()
	{
		super(SMTP.DATA);
	}
}
