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

import static betty16.lec2.smtp.Smtp.Smtp.Smtp.C;
import static betty16.lec2.smtp.Smtp.Smtp.Smtp.S;
import static betty16.lec2.smtp.Smtp.Smtp.Smtp._220;
import static betty16.lec2.smtp.Smtp.Smtp.Smtp._250;
import static betty16.lec2.smtp.Smtp.Smtp.Smtp._250d;

import org.scribble.runtime.net.SSLSocketChannelWrapper;
import org.scribble.runtime.net.SocketChannelEndpoint;
import org.scribble.runtime.session.MPSTEndpoint;
import org.scribble.runtime.statechans.LinearSocket;
import org.scribble.runtime.util.Buf;

import betty16.lec2.smtp.Smtp.Smtp.Smtp;
import betty16.lec2.smtp.Smtp.Smtp.roles.C;
import betty16.lec2.smtp.Smtp.Smtp.statechans.C.EndSocket;
import betty16.lec2.smtp.Smtp.Smtp.statechans.C.Smtp_C_1;
import betty16.lec2.smtp.Smtp.Smtp.statechans.C.Smtp_C_2;
import betty16.lec2.smtp.Smtp.Smtp.statechans.C.Smtp_C_3;
import betty16.lec2.smtp.Smtp.Smtp.statechans.C.Smtp_C_3_Cases;
import betty16.lec2.smtp.Smtp.Smtp.statechans.C.Smtp_C_4;
import betty16.lec2.smtp.Smtp.Smtp.statechans.C.Smtp_C_6;
import betty16.lec2.smtp.Smtp.Smtp.statechans.C.Smtp_C_7;
import betty16.lec2.smtp.Smtp.Smtp.statechans.C.Smtp_C_7_Cases;
import betty16.lec2.smtp.Smtp.Smtp.statechans.C.Smtp_C_8;
import betty16.lec2.smtp.message.SmtpMessageFormatter;
import betty16.lec2.smtp.message.client.Ehlo;
import betty16.lec2.smtp.message.client.Quit;
import betty16.lec2.smtp.message.client.StartTls;

public class SmtpC1
{
	public static void main(String[] args) throws Exception {
		String host = "smtp.cc.ic.ac.uk";
		int port = 25;

		Smtp smtp = new Smtp();
		try (MPSTEndpoint<Smtp, C> client = new MPSTEndpoint<>(smtp, C,
				new SmtpMessageFormatter())) {
			client.request(S, SocketChannelEndpoint::new, host, port);
			new SmtpC1().run(new Smtp_C_1(client));
		}
	}

	private EndSocket run(Smtp_C_1 c1) throws Exception {
		return
			doInit(
					doStartTls(
							doInit(c1.async(S, _220)))
			)
			.send(S, new Quit());
	}
	
	private Smtp_C_4 doInit(Smtp_C_2 c2) throws Exception {
		Smtp_C_3 c3 = c2.send(S, new Ehlo("test"));
		Buf<Object> buf = new Buf<>();
		while (true) {
			Smtp_C_3_Cases cases = c3.branch(S);
			switch (cases.getOp()) {
				case _250d: c3 = cases.receive(S, _250d, buf); System.out.print(buf.val); break;
				case _250:  return printlnBuf(cases.receive(S, _250, buf), buf);
			}
		}
	}

	private Smtp_C_6 doStartTls(Smtp_C_4 c4) throws Exception {
		return
				LinearSocket.wrapClient(
						c4.send(S, new StartTls())
							.async(S, _220)
				, S, SSLSocketChannelWrapper::new);
	}

	private Smtp_C_8 doInit(Smtp_C_6 c6) throws Exception {
		Smtp_C_7 c7 = c6.send(S, new Ehlo("test"));
		Buf<Object> buf = new Buf<>();
		while (true) {
			Smtp_C_7_Cases cases = c7.branch(S);
			switch (cases.getOp()) {
				case _250d: c7 = cases.receive(S, _250d, buf); System.out.print(buf.val); break;
				case _250:  return printlnBuf(cases.receive(S, _250, buf), buf);
			}
		}
	}

	private static <S, B extends Buf<?>> S printlnBuf(S s, B b) {
		System.out.println(b.val);
		return s;
	}
}

