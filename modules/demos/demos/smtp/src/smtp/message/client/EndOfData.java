package smtp.message.client;

import smtp.Smtp.Smtp.Smtp;
import smtp.message.SmtpMessage;
import smtp.message.SmtpMessageFormatter;

public class EndOfData extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public EndOfData()
	{
		super(Smtp.EndOfData, SmtpMessage.CRLF + "." + SmtpMessage.CRLF);
	}

	// Drop operator used for Scribble
	@Override
	public byte[] toBytes()
	{
		// No space after op and no implicit CRLF
		return (SmtpMessage.getOpString(this.op) + getBody()).getBytes(SmtpMessageFormatter.cs);
	}
}
