package smtp.message.server;

import smtp.Smtp.Smtp.Smtp;
import smtp.message.SmtpMessage;

public class _354 extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public _354()
	{
		super(Smtp._354);
	}

	public _354(String body)
	{
		super(Smtp._354, body);
	}

	/*@Override
	public Operator getOperator()
	{
		return Smtp._354;
	}*/
}
