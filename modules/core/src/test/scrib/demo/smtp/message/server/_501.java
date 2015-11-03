package demo.smtp.message.server;

import demo.smtp.Smtp.SMTP.SMTP;
import demo.smtp.message.SmtpMessage;

public class _501 extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public _501()
	{
		super(SMTP._501);
	}

	public _501(String body)
	{
		super(SMTP._501, body);
	}

	/*@Override
	public Operator getOperator()
	{
		return Smtp._354;
	}*/
}
