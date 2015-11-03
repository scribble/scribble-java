package demo.smtp.message.client;

import demo.smtp.Smtp.SMTP.SMTP;
import demo.smtp.message.SmtpMessage;

public class Ehlo extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public Ehlo()
	{
		super(SMTP.EHLO);
	}

	public Ehlo(String body)
	{
		super(SMTP.EHLO, body);
	}
}
