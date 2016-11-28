package betty16.lec2.smtp.message.server;

import betty16.lec2.smtp.Smtp.Smtp.Smtp;
import betty16.lec2.smtp.message.SmtpMessage;

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
