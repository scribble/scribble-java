package demo.smtp.message.client;

import demo.smtp.Smtp.Smtp.Smtp;
import demo.smtp.message.SmtpMessage;
import demo.smtp.message.SmtpMessageFormatter;

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
