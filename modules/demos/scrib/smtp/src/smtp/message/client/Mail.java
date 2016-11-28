package smtp.message.client;

import smtp.Smtp.Smtp.Smtp;
import smtp.message.SmtpMessage;

public class Mail extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public Mail(String sender)
	{
		super(Smtp.Mail, "<" + sender + ">");
	}
}
