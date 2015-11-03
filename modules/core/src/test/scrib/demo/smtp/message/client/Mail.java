package demo.smtp.message.client;

import demo.smtp.Smtp.SMTP.SMTP;
import demo.smtp.message.SmtpMessage;

public class Mail extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public Mail()
	{
		super(SMTP.MAIL);
	}

	public Mail(String sender)
	{
		super(SMTP.MAIL, "<" + sender + ">");
	}
}
