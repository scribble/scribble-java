package smtp.message.client;

import smtp.Smtp.Smtp.Smtp;
import smtp.message.SmtpMessage;

public class Subject extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public Subject(String subject)
	{
		super(Smtp.Subject, subject);
	}
}
