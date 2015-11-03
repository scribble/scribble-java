package demo.smtp.message.server;

import demo.smtp.Smtp.SMTP.SMTP;
import demo.smtp.message.SmtpMessage;
import demo.smtp.message.SmtpMessageFormatter;

public class _250_ extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public _250_()
	{
		super(SMTP._250_);
	}

	public _250_(String body)
	{
		super(SMTP._250_, body);
	}

	@Override
	public byte[] toBytes()
	{
		// No space after op
		return (getOpString(this.op) + getBody() + SmtpMessage.CRLF).getBytes(SmtpMessageFormatter.cs);
	}
}
