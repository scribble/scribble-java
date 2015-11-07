package test.smtp.message.client;

import test.smtp.Smtp.Smtp.Smtp;
import test.smtp.message.SmtpMessage;

public class Quit extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public Quit()
	{
		super(Smtp.Quit);
	}
}
