/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package coco.smtp.message;

import org.scribble.net.ScribMessage;
import org.scribble.sesstype.name.Op;
import org.scribble.util.Caller;

import coco.smtp.Smtp.Smtp.Smtp;

public abstract class SmtpMessage extends ScribMessage
{
	public static final String _220 = "220";
	public static final String _250 = "250";
	public static final String _250_ = "250-";
	public static final String _235 = "235";
	public static final String _535 = "535";
	public static final String _501 = "501";
	public static final String _354 = "354";

	public static final String EHLO = "ehlo";
	public static final String STARTTLS = "starttls";
	public static final String AUTH = "auth plain";
	public static final String MAIL = "mail from:";
	public static final String RCPT = "rcpt to:";
	public static final String SUBJECT = "subject:";
	public static final String DATA = "data";
	public static final String QUIT = "quit";

	public static final String DATA_LINE = "";
	public static final String END_OF_DATA = "";

	private static final long serialVersionUID = 1L;
	
	protected static final String CRLF = "\r\n";
	
	public SmtpMessage(Op op)
	{
		super(op);
	}

	public SmtpMessage(Op op, String body)
	{
		super(op, body);
	}
	
	public String getBody()
	{
		return (this.payload.length == 0) ? "" : (String) this.payload[0];
	}

	public byte[] toBytes()
	{
		byte[] bs = (getOpString(this.op) + " " + getBody() + SmtpMessage.CRLF).getBytes(SmtpMessageFormatter.cs);  // Can give "utf-8" as arg directly
		return bs;
	}
	
	@Override
	public String toString()
	{
		return new String(toBytes());
	}
	
	protected static String getOpString(Op op)
	{
		return
					(op.equals(Smtp._220)) ? SmtpMessage._220
				: (op.equals(Smtp._250)) ? SmtpMessage._250
				: (op.equals(Smtp._250d)) ? SmtpMessage._250_
				: (op.equals(Smtp.Ehlo)) ? SmtpMessage.EHLO
				: (op.equals(Smtp.StartTls)) ? SmtpMessage.STARTTLS
				: (op.equals(Smtp.Quit)) ? SmtpMessage.QUIT
				: new Caller().call(() -> { throw new RuntimeException("TODO: " + op); });
	}
}
