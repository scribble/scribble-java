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

import static betty16.lec2.smtp.Smtp.Smtp.Smtp.*;

import org.scribble.net.scribsock.LinearSocket;
import org.scribble.net.session.SSLSocketChannelWrapper;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import betty16.lec2.smtp.Smtp.Smtp.Smtp;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.EndSocket;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_1;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_4;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_6;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S_Ehlo;
import betty16.lec2.smtp.Smtp.Smtp.channels.C.ioifaces.Succ_In_S_250;
import betty16.lec2.smtp.Smtp.Smtp.roles.C;
import betty16.lec2.smtp.message.SmtpMessageFormatter;
import betty16.lec2.smtp.message.client.Quit;
import betty16.lec2.smtp.message.client.StartTls;

public class MySmtpC {

	public static void main(String[] args) throws Exception {
		String host = "smtp.cc.ic.ac.uk";
		int port = 25;

		Smtp smtp = new Smtp();
		try (MPSTEndpoint<Smtp, C> client = new MPSTEndpoint<>(smtp, C, new SmtpMessageFormatter())) {
			client.connect(S, SocketChannelEndpoint::new, host, port);
			new MySmtpC().run(new Smtp_C_1(client));
		}
	}

	private EndSocket run(Smtp_C_1 c1) throws Exception {
		/*

		return
			doInit(
					doStartTls(
							doInit(c1.async(S, _220)))
			)
			.send(S, new Quit());

		/*/
		throw new RuntimeException("[TODO]: ");
		//*/
	}

	private 
	//  succ(S?250)  <-  S!{ Ehlo: ?? }
	Succ_In_S_250 doInit(Select_C_S_Ehlo<?> c) throws Exception {
		
		throw new RuntimeException("[TODO]: ");
		
	}

	private Smtp_C_6 doStartTls(Smtp_C_4 c4) throws Exception {
		return
				LinearSocket.wrapClient(
						c4.send(S, new StartTls())
							.async(S, _220)
				, S, SSLSocketChannelWrapper::new);
	}
}
