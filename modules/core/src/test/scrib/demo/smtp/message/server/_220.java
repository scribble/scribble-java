package demo.smtp.message.server;

import demo.smtp.Smtp.SMTP.SMTP;
import demo.smtp.message.SmtpMessage;

public class _220 extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public _220()
	{
		super(SMTP._220);
	}

	public _220(String body)
	{
		super(SMTP._220, body);
	}
	
	/*@Override
	public Operator getOperator()
	{
		return Smtp._220;
	}*/
}
