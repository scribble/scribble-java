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
package betty16.lec2.smtp;

import static betty16.lec2.smtp.Smtp.Smtp.Smtp.S;
import static betty16.lec2.smtp.Smtp.Smtp.Smtp._220;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.runtime.net.Buf;
import org.scribble.runtime.net.scribsock.LinearSocket;
import org.scribble.runtime.net.session.MPSTEndpoint;
import org.scribble.runtime.net.session.SSLSocketChannelWrapper;
import org.scribble.runtime.net.session.SocketChannelEndpoint;

import betty16.lec2.smtp.Smtp.Smtp.Smtp;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_1;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_3;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_3_Handler;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_4;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_7;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_7_Handler;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_8;
import betty16.lec2.smtp.Smtp.Smtp.roles.C;
import betty16.lec2.smtp.message.SmtpMessageFormatter;
import betty16.lec2.smtp.message.client.Ehlo;
import betty16.lec2.smtp.message.client.Quit;
import betty16.lec2.smtp.message.client.StartTls;
import betty16.lec2.smtp.message.server._250;
import betty16.lec2.smtp.message.server._250d;

public class SmtpC4 {

	public static void main(String[] args) throws Exception
	{
		String host = "smtp.cc.ic.ac.uk";
		int port = 25;

		Smtp smtp = new Smtp();
		try (MPSTEndpoint<Smtp, C> client = new MPSTEndpoint<>(smtp, Smtp.C, new SmtpMessageFormatter()))
		{
			client.connect(S, SocketChannelEndpoint::new, host, port);
			new SmtpC4().run(new Smtp_C_1(client));
		}
	}

	private void run(Smtp_C_1 c1) throws Exception {
		
		System.out.println("Init: ");
		
		c1.async(S, _220).send(S, new Ehlo("test")).branch(S, new MySmtp_C_3Handler());
	}
}
	
class MySmtp_C_3Handler implements Smtp_C_3_Handler {

	@Override
	public void receive(Smtp_C_3 s3, betty16.lec2.smtp.Smtp.Smtp.ops._250d op, Buf<_250d> arg) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		System.out.println("250-: " + arg.val.getBody());

		s3.branch(S, this);
	}

	@Override
	public void receive(Smtp_C_4 s4, betty16.lec2.smtp.Smtp.Smtp.ops._250 op, Buf<_250> arg) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		System.out.println("250: " + arg.val.getBody());

		LinearSocket.wrapClient(
					s4.send(S, new StartTls()).async(S, _220)
				, S, SSLSocketChannelWrapper::new)
			.send(S, new Ehlo("test")).branch(S, new MySmtp_C_8Handler());
	}
}

class MySmtp_C_8Handler implements Smtp_C_7_Handler {

	@Override
	public void receive(Smtp_C_7 s7, betty16.lec2.smtp.Smtp.Smtp.ops._250d op, Buf<_250d> arg) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		System.out.println("(TLS) 250-: " + arg.val.getBody());

		s7.branch(S, this);
	}

	@Override
	public void receive(Smtp_C_8 s8, betty16.lec2.smtp.Smtp.Smtp.ops._250 op, Buf<_250> arg) throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		System.out.println("(TLS) 250: " + arg.val.getBody());

		s8.send(S, new Quit());
	}
}
