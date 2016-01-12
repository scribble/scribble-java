package demo.smtp.message.client;

import demo.smtp.Smtp.Smtp.Smtp;
import demo.smtp.message.SmtpMessage;

public class Auth extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public Auth()
	{
		super(Smtp.Auth);
	}

	// Move base 64 encode into here? (Or override toBytes)
	public Auth(String body)
	{
		super(Smtp.Auth, body);
	}
	
	@Override
	public String toString()
	{
		return this.op + "(***)";  // FIXME
	}
}
