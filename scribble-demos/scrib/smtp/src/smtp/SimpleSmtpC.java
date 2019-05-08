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

//$ java -cp scribble-runtime/target/classes/:scribble-core/target/classes:scribble-demos/target/classes smtp.SimpleSmtpC


package smtp;

import static smtp.Smtp.Smtp.Smtp.C;
import static smtp.Smtp.Smtp.Smtp.S;
import static smtp.Smtp.Smtp.Smtp._220;
import static smtp.Smtp.Smtp.Smtp._221;
import static smtp.Smtp.Smtp.Smtp._235;
import static smtp.Smtp.Smtp.Smtp._250;
import static smtp.Smtp.Smtp.Smtp._250d;
import static smtp.Smtp.Smtp.Smtp._354;
import static smtp.Smtp.Smtp.Smtp._501;
import static smtp.Smtp.Smtp.Smtp._535;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;

import org.scribble.runtime.net.SSLSocketChannelWrapper;
import org.scribble.runtime.net.SocketChannelEndpoint;
import org.scribble.runtime.session.MPSTEndpoint;
import org.scribble.runtime.statechans.LinearSocket;
import org.scribble.runtime.util.Buf;

import smtp.Smtp.Smtp.Smtp;
import smtp.Smtp.Smtp.statechans.C.Smtp_C_1;
import smtp.Smtp.Smtp.statechans.C.Smtp_C_10;
import smtp.Smtp.Smtp.statechans.C.Smtp_C_11_Cases;
import smtp.Smtp.Smtp.statechans.C.Smtp_C_4;
import smtp.Smtp.Smtp.statechans.C.Smtp_C_6;
import smtp.Smtp.Smtp.statechans.C.Smtp_C_8;
import smtp.Smtp.Smtp.statechans.C.Smtp_C_9_Cases;
import smtp.Smtp.Smtp.statechans.C.ioifaces.Branch_C_S_250__S_250d;
import smtp.Smtp.Smtp.statechans.C.ioifaces.Case_C_S_250__S_250d;
import smtp.Smtp.Smtp.statechans.C.ioifaces.Select_C_S_Ehlo;
import smtp.Smtp.Smtp.statechans.C.ioifaces.Succ_In_S_250;
import smtp.Smtp.Smtp.roles.C;
import smtp.message.SmtpMessageFormatter;
import smtp.message.client.Auth;
import smtp.message.client.Data;
import smtp.message.client.DataLine;
import smtp.message.client.Ehlo;
import smtp.message.client.EndOfData;
import smtp.message.client.Mail;
import smtp.message.client.Quit;
import smtp.message.client.Rcpt;
import smtp.message.client.StartTls;
import smtp.message.client.Subject;

// NB: needs the -subtypes option in the Endpoint API generation
public class SimpleSmtpC
{
	public SimpleSmtpC() throws Exception
	{
		run();
	}

	public void run() throws Exception
	{
		String host = "smtp.cc.ic.ac.uk";
		int port = 25;

		String ehlo = "user.testing.com";
		String mailFrom = "rhu@doc.ic.ac.uk";  // Sender
		String rcptTo = mailFrom;
		String subj = "test";
		String body = "body";

		Smtp smtp = new Smtp();
		try (MPSTEndpoint<Smtp, C> se = new MPSTEndpoint<>(smtp, C, new SmtpMessageFormatter()))
		{
			se.request(S, SocketChannelEndpoint::new, host, port);

			Smtp_C_11_Cases cases =
					doAuth(
						doEhlo(
							doStartTls(
								doEhlo(new Smtp_C_1(se).async(S, _220), ehlo)
							)
						, ehlo))
					.send(S, new Mail(mailFrom))
					.branch(S);
			switch (cases.getOp())
			{
				case _250:
				{
					cases.receive(_250)                       
						.send(S, new Rcpt(rcptTo)).async(S, _250) 
						.send(S, new Data()).async(S, _354)     
						.send(S, new Subject(subj))             
						.send(S, new DataLine(body))            
						.send(S, new EndOfData())             
						.receive(S, _250, new Buf<>())          
						.send(S, new Quit()).async(S, _221);                  
					break;
				}
				case _501:
				{
					cases.receive(_501).send(S, new Quit()).async(S, _221);
				}
			}
		}
	}

	private <S1 extends Succ_In_S_250, S2 extends Branch_C_S_250__S_250d<S1, S2>>
			S1 doEhlo(Select_C_S_Ehlo<S2> s, String ehlo) throws Exception
	{
		Branch_C_S_250__S_250d<S1, S2> bra = s.send(S, new Ehlo(ehlo));
		while (true)
		{	
			Case_C_S_250__S_250d<S1, S2> cases = bra.branch(S);
			switch (cases.getOp())
			{
				case _250:
				{
					return cases.receive(_250);
				}
				case _250d:
				{
					bra = cases.receive(_250d);
					break;
				}
			}
		}
	}

	private Smtp_C_6 doStartTls(Smtp_C_4 s4) throws Exception
	{
		return
				LinearSocket.wrapClient(
						s4.send(S, new StartTls())
							.async(S, _220)
				, S, SSLSocketChannelWrapper::new);
	}

	private Smtp_C_10 doAuth(Smtp_C_8 s8) throws Exception
	{
		Smtp_C_9_Cases s9cases = s8.send(S, new Auth(getAuthPlain())).branch(S);
		switch (s9cases.op)
		{
			case _235:
			{
				return s9cases.receive(_235);
			}
			case _535:
			{
				s9cases.receive(_535).send(S, new Quit());
				System.exit(0);
			}
			default:  // To satisfy Java typing for return
			{
				throw new RuntimeException("Won't get in here: " + s9cases.op);
			}
		}
	}

	private String getAuthPlain() throws IOException
	{
		return myGetAuthPlain();
	}

	private static String myGetAuthPlain() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String user;
		String pw;

		System.out.println("User: ");
		user = br.readLine();

		System.out.println("Password: ");
		EraserThread et = new EraserThread();
		et.start();
		pw = br.readLine();
		et.stopMasking();

		byte[] u = user.getBytes("utf-8");
		byte[] p = pw.getBytes("utf-8");
		byte[] bs = new byte[u.length + p.length + 2];
		System.arraycopy(u, 0, bs, 1, u.length);
		System.arraycopy(p, 0, bs, u.length + 2, p.length);

		return Base64.getEncoder().encodeToString(bs);
	}

	public static void main(String[] args) throws Exception
	{
		new SimpleSmtpC();
	}
	
	
	
	
	/*public void run() throws Exception
	{
		String host = "smtp.cc.ic.ac.uk";
		int port = 25;

		String ehlo = "user.testing.com";
		String mail = "rhu@doc.ic.ac.uk";  // Sender
		String rcpt = "raymond.hu05@imperial.ac.uk";
		String subj = "test";
		String body = "body";

		Smtp smtp = new Smtp();
		try (SessionEndpoint<Smtp, C> se = new SessionEndpoint<>(smtp, C, new SmtpMessageFormatter()))
		{
			se.connect(S, SocketChannelEndpoint::new, host, port);

			Smtp_C_11_Cases cases =
					doAuth(
						doEhlo(
							doStartTls(
								//doEhlo(new Smtp_C_1(se).async(S, _220), ehlo).to(Select_C_S_Quit__S_StartTls.cast).to(Smtp_C_4.cast)
								doEhlo(new Smtp_C_1(se).async(S, _220), ehlo)
							)
						//, ehlo).to(Select_C_S_Auth__S_Quit.cast).to(Smtp_C_8.cast))
						, ehlo))
					.send(S, new Mail(mail))
					.branch(S);
			switch (cases.getOp())
			{
				case _250:
				{
					cases.receive(_250)
						.send(S, new Rcpt(rcpt))
						.async(S, _250)
						.send(S, new Data())
						.async(S, _354)
						.send(S, new Subject(subj))
						.send(S, new DataLine(body))
						.send(S, new EndOfData())
						.receive(S, _250, new Buf<>())
						//.async(S, _250)
						.send(S, new Quit());
					break;
				}
				case _501:
				{
					cases.receive(_501).send(S, new Quit());
				}
			}
		}
	}

	//private Succ_In_S_250 doEhlo(Select_C_S_Ehlo__S_Quit<?, ?> s, String ehlo) throws Exception
	//private <S extends Succ_In_S_250> S doEhlo(Select_C_S_Ehlo__S_Quit<? extends Branch_C_S_250__S_250d<S, ?>, ?> s, String ehlo) throws Exception
	// NOTE: needs the -subtypes option
	private <S1 extends Succ_In_S_250, S2 extends Branch_C_S_250__S_250d<S1, S2>>
			//S1 doEhlo(Select_C_S_Ehlo__S_Quit<? extends Branch_C_S_250__S_250d<S1, S2>, ?> s, String ehlo) throws Exception
			//S1 doEhlo(Select_C_S_Ehlo__S_Quit<S2, ?> s, String ehlo) throws Exception
			S1 doEhlo(Select_C_S_Ehlo<S2> s, String ehlo) throws Exception
	{
		//Branch_C_S_250__S_250d<?, ?> bra = s.send(S, new Ehlo(ehlo)).to(Branch_C_S_250__S_250d.cast);
		//Branch_C_S_250__S_250d<S1, ?> bra = s.send(S, new Ehlo(ehlo));
		Branch_C_S_250__S_250d<S1, S2> bra = s.send(S, new Ehlo(ehlo));
		while (true)
		{	
			//Case_C_S_250__S_250d<?, ?> cases = bra.branch(S);
			//Case_C_S_250__S_250d<S1, ?> cases = bra.branch(S);
			Case_C_S_250__S_250d<S1, S2> cases = bra.branch(S);
			switch (cases.getOp())
			{
				case _250:
				{
					return cases.receive(_250);
				}
				case _250d:
				{
					//bra = cases.receive(_250d).to(Branch_C_S_250__S_250d.cast);
					//bra = (Branch_C_S_250__S_250d<S1, ?>) cases.receive(_250d);
					bra = cases.receive(_250d);
					break;
				}
			}
		}
	}*/
}
