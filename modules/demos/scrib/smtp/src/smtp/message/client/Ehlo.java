package smtp.message.client;

import smtp.Smtp.Smtp.Smtp;
import smtp.message.SmtpMessage;

public class Ehlo extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public Ehlo(String body)
	{
		super(Smtp.Ehlo, body);
	}
}
