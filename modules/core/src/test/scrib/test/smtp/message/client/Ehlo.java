package test.smtp.message.client;

import test.smtp.Smtp.Smtp.Smtp;
import test.smtp.message.SmtpMessage;

public class Ehlo extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public Ehlo()
	{
		super(Smtp.Ehlo);
	}

	public Ehlo(String body)
	{
		super(Smtp.Ehlo, body);
	}
}
