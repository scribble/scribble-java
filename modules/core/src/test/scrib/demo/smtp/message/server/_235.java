package demo.smtp.message.server;

import demo.smtp.SMTP;
import demo.smtp.message.SmtpMessage;

public class _235 extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public _235()
	{
		super(SMTP._235);
	}

	public _235(String body)
	{
		super(SMTP._235, body);
	}

	/*@Override
	public Operator getOperator()
	{
		return Smtp._235;
	}*/
}
