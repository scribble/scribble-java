package demo.smtp.message.server;

import demo.smtp.Smtp.Smtp.Smtp;
import demo.smtp.message.SmtpMessage;

public class _235 extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public _235()
	{
		super(Smtp._235);
	}

	public _235(String body)
	{
		super(Smtp._235, body);
	}

	/*@Override
	public Operator getOperator()
	{
		return Smtp._235;
	}*/
}
