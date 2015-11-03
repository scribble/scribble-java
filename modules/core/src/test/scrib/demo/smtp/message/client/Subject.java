package demo.smtp.message.client;

import demo.smtp.Smtp.SMTP.SMTP;
import demo.smtp.message.SmtpMessage;

public class Subject extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public Subject()
	{
		super(SMTP.SUBJECT);
	}

	public Subject(String subject)
	{
		super(SMTP.SUBJECT, subject);
	}
}
