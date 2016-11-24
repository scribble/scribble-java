package fase16.smtp.message.client;

import fase16.smtp.Smtp.Smtp.Smtp;
import fase16.smtp.message.SmtpMessage;

public class StartTls extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public StartTls()
	{
		super(Smtp.StartTls);
	}
}
