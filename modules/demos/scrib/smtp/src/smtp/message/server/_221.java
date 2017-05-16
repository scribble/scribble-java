package smtp.message.server;

import smtp.Smtp.Smtp.Smtp;
import smtp.message.SmtpMessage;

public class _221 extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public _221()
	{
		super(Smtp._220);
	}

	public _221(String body)
	{
		super(Smtp._220, body);
	}
	
	/*@Override
	public Operator getOperator()
	{
		return Smtp._220;
	}*/
}
