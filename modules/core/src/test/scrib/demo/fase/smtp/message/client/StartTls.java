package demo.fase.smtp.message.client;

import demo.fase.smtp.Smtp.Smtp.Smtp;
import demo.fase.smtp.message.SmtpMessage;

public class StartTls extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public StartTls()
	{
		super(Smtp.StartTls);
	}
}
