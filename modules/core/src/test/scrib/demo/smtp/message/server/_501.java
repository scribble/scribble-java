package demo.smtp.message.server;

import demo.smtp.Smtp.Smtp.Smtp;
import demo.smtp.message.SmtpMessage;

public class _501 extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public _501()
	{
		super(Smtp._501);
	}

	public _501(String body)
	{
		super(Smtp._501, body);
	}

	/*@Override
	public Operator getOperator()
	{
		return Smtp._354;
	}*/
}
