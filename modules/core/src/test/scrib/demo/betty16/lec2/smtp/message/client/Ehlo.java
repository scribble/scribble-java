package demo.betty16.lec2.smtp.message.client;

import demo.betty16.lec2.smtp.Smtp.Smtp.Smtp;
import demo.betty16.lec2.smtp.message.SmtpMessage;

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
