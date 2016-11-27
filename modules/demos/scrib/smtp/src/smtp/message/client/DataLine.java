package smtp.message.client;

import smtp.Smtp.Smtp.Smtp;
import smtp.message.SmtpMessage;
import smtp.message.SmtpMessageFormatter;

// FIXME: rename
public class DataLine extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public DataLine(String body)
	{
		super(Smtp.DataLine, body);
		if (body.equals("."))
		{
			throw new RuntimeException("Illegal body: " + body);
		}
	}

	@Override
	public byte[] toBytes()
	{
		// No space after op and no implicit CRLF
		return (SmtpMessage.getOpString(this.op) + getBody()).getBytes(SmtpMessageFormatter.cs);
	}
}
