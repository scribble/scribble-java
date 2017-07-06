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
//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/parser/target/classes';'modules/demos/target/classes/ coco.smtp.Client3


package coco.smtp;

import static coco.smtp.Smtp.Smtp.Smtp.C;
import static coco.smtp.Smtp.Smtp.Smtp.S;
import static coco.smtp.Smtp.Smtp.Smtp._220;
import static coco.smtp.Smtp.Smtp.Smtp._250;
import static coco.smtp.Smtp.Smtp.Smtp._250d;

import org.scribble.runtime.net.Buf;
import org.scribble.runtime.net.scribsock.LinearSocket;
import org.scribble.runtime.net.session.MPSTEndpoint;
import org.scribble.runtime.net.session.SSLSocketChannelWrapper;
import org.scribble.runtime.net.session.SocketChannelEndpoint;

import coco.smtp.Smtp.Smtp.Smtp;
import coco.smtp.Smtp.Smtp.channels.C.Smtp_C_1;
import coco.smtp.Smtp.Smtp.channels.C.Smtp_C_1_Future;
import coco.smtp.Smtp.Smtp.channels.C.ioifaces.Branch_C_S_250__S_250d;
import coco.smtp.Smtp.Smtp.channels.C.ioifaces.Case_C_S_250__S_250d;
import coco.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S_Ehlo;
import coco.smtp.Smtp.Smtp.channels.C.ioifaces.Succ_In_S_250;
import coco.smtp.Smtp.Smtp.roles.C;
import coco.smtp.message.SmtpMessageFormatter;
import coco.smtp.message.client.Ehlo;
import coco.smtp.message.client.Quit;
import coco.smtp.message.client.StartTls;
import coco.smtp.message.server._250;
import coco.smtp.message.server._250d;

// "No casts" version -- via generic inference
public class SmtpC3
{
	public SmtpC3() throws Exception
	{
		run();
	}

	public static void main(String[] args) throws Exception
	{
		new SmtpC3();
	}

	public void run() throws Exception
	{
		String host = "smtp.cc.ic.ac.uk";
		int port = 25;

		Smtp smtp = new Smtp();
		try (MPSTEndpoint<Smtp, C> se = new MPSTEndpoint<>(smtp, C, new SmtpMessageFormatter()))
		{
			se.connect(S, SocketChannelEndpoint::new, host, port);

			Smtp_C_1 s1 = new Smtp_C_1(se);
			Buf<Smtp_C_1_Future> b = new Buf<>();
			doInit(
				LinearSocket.wrapClient(
					doInit(s1.async(S, _220, b))
						.send(S, new StartTls())
						.async(S, _220)
				, S, SSLSocketChannelWrapper::new)
			)
			.send(S, new Quit());
			
			//System.out.println("b1: " + b.val.sync().msg);
		}
	}

	private <T1 extends Branch_C_S_250__S_250d<T2, T1>, T2 extends Succ_In_S_250>
			//T2 doInit(Select_C_S_Ehlo__S_Quit<T1, EndSocket> s) throws Exception
			T2 doInit(Select_C_S_Ehlo<T1> s) throws Exception
	{
		Branch_C_S_250__S_250d<T2, T1> b = s.send(S, new Ehlo("test"));
		Buf<_250> b1 = new Buf<>();
		Buf<_250d> b2 = new Buf<>();
		while (true)
		{
			Case_C_S_250__S_250d<T2, T1> c = b.branch(S);
			switch (c.getOp())
			{
				case _250d:
					b = SmtpC1.printBuf(c.receive(S, _250d, b2), b2);
					break;
				case _250:
					return SmtpC1.printlnBuf(c.receive(S, _250, b1), b1);
			}
		}
	}
}
