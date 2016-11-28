package fase16.smtp.message.client;

import fase16.smtp.Smtp.Smtp.Smtp;
import fase16.smtp.message.SmtpMessage;

public class Quit extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public Quit()
	{
		super(Smtp.Quit);
	}
}
