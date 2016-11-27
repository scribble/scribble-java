package coco.smtp.message.client;

import coco.smtp.Smtp.Smtp.Smtp;
import coco.smtp.message.SmtpMessage;

public class StartTls extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public StartTls()
	{
		super(Smtp.StartTls);
	}
}
