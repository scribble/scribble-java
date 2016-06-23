package demo.betty16.lec2.smtp.message.client;

import demo.betty16.lec2.smtp.Smtp.Smtp.Smtp;
import demo.betty16.lec2.smtp.message.SmtpMessage;

public class Quit extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public Quit()
	{
		super(Smtp.Quit);
	}
}
