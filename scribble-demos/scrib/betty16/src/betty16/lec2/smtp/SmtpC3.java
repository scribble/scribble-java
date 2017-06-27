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

import org.scribble.net.Buf;
import org.scribble.net.scribsock.LinearSocket;
import org.scribble.net.session.SSLSocketChannelWrapper;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import betty16.lec2.smtp.Smtp.Smtp.Smtp;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.EndSocket;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_1;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_4;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_6;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.ioifaces.Branch_C_S_250__S_250d;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.ioifaces.Case_C_S_250__S_250d;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S_Ehlo;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.ioifaces.Succ_In_S_250;
import betty16.lec2.smtp.Smtp.Smtp.roles.C;
import betty16.lec2.smtp.message.SmtpMessageFormatter;
import betty16.lec2.smtp.message.client.Ehlo;
import betty16.lec2.smtp.message.client.Quit;
import betty16.lec2.smtp.message.client.StartTls;

public class SmtpC3 {

	public static void main(String[] args) throws Exception {
		String host = "smtp.cc.ic.ac.uk";
		int port = 25;

		Smtp smtp = new Smtp();
		try (MPSTEndpoint<Smtp, C> client = new MPSTEndpoint<>(smtp, C, new SmtpMessageFormatter())) {
			client.connect(S, SocketChannelEndpoint::new, host, port);
			new SmtpC3().run(new Smtp_C_1(client));
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

	/*
	private 
	<
		S1 extends Branch_C_S_250__S_250d<?, S1>   // S1 = S?{ 250: Succ(S?250), 250d: S1 }
	>
	// S!Ehlo.S1  ->  S2
	Succ_In_S_250 doInit(Select_C_S_Ehlo<S1> c) throws Exception {
		S1 b = c.send(S, new Ehlo("test"));
		Buf<Object> buf = new Buf<>();
		for (Case_C_S_250__S_250d<?, S1> cases = b.branch(S); true; cases = b.branch(S)) {
			switch (cases.getOp()) {
				case _250:  return printlnBuf(cases.receive(S, _250, buf), buf);
				case _250d: b = cases.receive(S, _250d, buf); System.out.print(buf.val); break;
			}
		}
	}
	//*/

	//*
	private 
	<
		S1 extends Branch_C_S_250__S_250d<S2, S1>,   // S1 = S?{ 250: S2, 250d: S1 }
		S2 extends Succ_In_S_250                     // S2 = Succ(S?250)
	>
	// S!Ehlo.S1  ->  S2
	S2 doInit(Select_C_S_Ehlo<S1> c) throws Exception {
		S1 b = c.send(S, new Ehlo("test"));
		Buf<Object> buf = new Buf<>();
		while (true) {
			Case_C_S_250__S_250d<S2, S1> cases = b.branch(S);
			switch (cases.getOp()) {
				case _250d: b = cases.receive(S, _250d, buf); System.out.print(buf.val); break;
				case _250:  return printlnBuf(cases.receive(S, _250, buf), buf);
			}
		}
	}
	//*/

	private Smtp_C_6 doStartTls(Smtp_C_4 c4) throws Exception {
		return
				LinearSocket.wrapClient(
						c4.send(S, new StartTls())
							.async(S, _220)
				, S, SSLSocketChannelWrapper::new);
	}

	private static <S, B extends Buf<?>> S printlnBuf(S s, B b) {
		System.out.println(b.val);
		return s;
	}

	/*private static <S, B extends Buf<?>> S printBuf(S s, B b) {
		System.out.print(b.val);
		return s;
	}*/
}
