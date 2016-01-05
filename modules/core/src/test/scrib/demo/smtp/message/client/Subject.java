package demo.smtp.message.client;

import demo.smtp.Smtp.Smtp.Smtp;
import demo.smtp.message.SmtpMessage;

public class Subject extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public Subject(String subject)
	{
		super(Smtp.Subject, subject);
	}
}
