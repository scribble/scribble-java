package demo.fase.smtp.message.client;

import demo.fase.smtp.Smtp.Smtp.Smtp;
import demo.fase.smtp.message.SmtpMessage;

public class Quit extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public Quit()
	{
		super(Smtp.Quit);
	}
}
