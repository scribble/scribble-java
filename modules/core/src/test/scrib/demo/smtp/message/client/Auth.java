package demo.smtp.message.client;

import demo.smtp.Smtp.SMTP.SMTP;
import demo.smtp.message.SmtpMessage;

public class Auth extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public Auth()
	{
		super(SMTP.AUTH);
	}

	// Move base 64 encode into here? (Or override toBytes)
	public Auth(String body)
	{
		super(SMTP.AUTH, body);
	}
	
	@Override
	public String toString()
	{
		return this.op + "(***)";  // FIXME
	}
}
