package demo.smtp.message.server;

import demo.smtp.SMTP;
import demo.smtp.message.SmtpMessage;

public class _535 extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public _535()
	{
		super(SMTP._535);
	}

	public _535(String body)
	{
		super(SMTP._535, body);
	}

	/*@Override
	public Operator getOperator()
	{
		return Smtp._354;
	}*/
}
