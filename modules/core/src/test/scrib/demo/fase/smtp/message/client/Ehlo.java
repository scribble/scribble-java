package demo.fase.smtp.message.client;

import demo.fase.smtp.Smtp.Smtp.Smtp;
import demo.fase.smtp.message.SmtpMessage;

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
