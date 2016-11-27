package fase16.smtp.message.server;

import fase16.smtp.Smtp.Smtp.Smtp;
import fase16.smtp.message.SmtpMessage;

public class _220 extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public _220()
	{
		super(Smtp._220);
	}

	public _220(String body)
	{
		super(Smtp._220, body);
	}
	
	/*@Override
	public Operator getOperator()
	{
		return Smtp._220;
	}*/
}
