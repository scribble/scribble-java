package demo.smtp.message.client;

import demo.smtp.Smtp.Smtp.Smtp;
import demo.smtp.message.SmtpMessage;

public class Rcpt extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	/*public Rcpt()
	{
		super(SMTP.RCPT);
	}*/

	public Rcpt(String receiver)
	{
		super(Smtp.Rcpt, "<" + receiver + ">");
	}
}
