package fase16.smtp.message.client;

import fase16.smtp.Smtp.Smtp.Smtp;
import fase16.smtp.message.SmtpMessage;

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
