package demo.smtp.message.client;

import demo.smtp.Smtp.Smtp.Smtp;
import demo.smtp.message.SmtpMessage;

public class Ehlo extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public Ehlo(String body)
	{
		super(Smtp.Ehlo, body);
	}
}
