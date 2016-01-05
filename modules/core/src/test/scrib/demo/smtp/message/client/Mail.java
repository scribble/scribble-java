package demo.smtp.message.client;

import demo.smtp.Smtp.Smtp.Smtp;
import demo.smtp.message.SmtpMessage;

public class Mail extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public Mail(String sender)
	{
		super(Smtp.Mail, "<" + sender + ">");
	}
}
