package test.smtp.message.server;

import test.smtp.Smtp.Smtp.Smtp;
import test.smtp.message.SmtpMessage;
import test.smtp.message.SmtpMessageFormatter;

public class _250_ extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public _250_()
	{
		super(Smtp._250d);
	}

	public _250_(String body)
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
