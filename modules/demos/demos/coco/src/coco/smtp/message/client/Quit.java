package coco.smtp.message.client;

import coco.smtp.Smtp.Smtp.Smtp;
import coco.smtp.message.SmtpMessage;

public class Quit extends SmtpMessage
{
	private static final long serialVersionUID = 1L;

	public Quit()
	{
		super(Smtp.Quit);
	}
}
