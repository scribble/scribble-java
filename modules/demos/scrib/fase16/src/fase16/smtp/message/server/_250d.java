package fase16.smtp.message.server;

import fase16.smtp.Smtp.Smtp.Smtp;
import fase16.smtp.message.SmtpMessage;
import fase16.smtp.message.SmtpMessageFormatter;

public class _250d extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public _250d()
	{
		super(Smtp._250d);
	}

	public _250d(String body)
	{
		super(Smtp._250d, body);
	}

	@Override
	public byte[] toBytes()
	{
		// No space after op
		return (getOpString(this.op) + getBody() + SmtpMessage.CRLF).getBytes(SmtpMessageFormatter.cs);
	}
}
