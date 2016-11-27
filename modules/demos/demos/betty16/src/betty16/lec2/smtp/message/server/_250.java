package betty16.lec2.smtp.message.server;

import betty16.lec2.smtp.Smtp.Smtp.Smtp;
import betty16.lec2.smtp.message.SmtpMessage;

public class _250 extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public _250()
	{
		super(Smtp._250);
	}

	public _250(String body)
	{
		super(Smtp._250, body);
	}
}
