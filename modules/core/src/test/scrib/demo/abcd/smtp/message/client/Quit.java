package demo.abcd.smtp.message.client;

import demo.abcd.smtp.Smtp.Smtp.Smtp;
import demo.abcd.smtp.message.SmtpMessage;

public class Quit extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public Quit()
	{
		super(Smtp.Quit);
	}
}
