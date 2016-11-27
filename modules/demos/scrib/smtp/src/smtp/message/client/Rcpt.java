package smtp.message.client;

import smtp.Smtp.Smtp.Smtp;
import smtp.message.SmtpMessage;

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
