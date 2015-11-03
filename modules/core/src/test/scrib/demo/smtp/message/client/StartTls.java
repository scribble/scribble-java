package demo.smtp.message.client;

import demo.smtp.Smtp.SMTP.SMTP;
import demo.smtp.message.SmtpMessage;

public class StartTls extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public StartTls()
	{
		super(SMTP.STARTTLS);
	}
}
