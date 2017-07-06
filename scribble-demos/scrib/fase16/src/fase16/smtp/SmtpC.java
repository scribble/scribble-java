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


//$ java -cp scribble-core/target/classes:scribble-runtime/target/classes:scribble-demos/target/classes fase16.smtp.SmtpC


package fase16.smtp;

import static fase16.smtp.Smtp.Smtp.Smtp.S;
import static fase16.smtp.Smtp.Smtp.Smtp._220;
import static fase16.smtp.Smtp.Smtp.Smtp._250;
import static fase16.smtp.Smtp.Smtp.Smtp._250d;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.runtime.net.Buf;
import org.scribble.runtime.net.scribsock.LinearSocket;
import org.scribble.runtime.net.session.MPSTEndpoint;
import org.scribble.runtime.net.session.SSLSocketChannelWrapper;
import org.scribble.runtime.net.session.SocketChannelEndpoint;

import fase16.smtp.Smtp.Smtp.Smtp;
import fase16.smtp.Smtp.Smtp.channels.C.Smtp_C_1;
import fase16.smtp.Smtp.Smtp.channels.C.Smtp_C_1_Future;
import fase16.smtp.Smtp.Smtp.channels.C.Smtp_C_3;
import fase16.smtp.Smtp.Smtp.channels.C.Smtp_C_3_Handler;
import fase16.smtp.Smtp.Smtp.channels.C.Smtp_C_4;
import fase16.smtp.Smtp.Smtp.channels.C.ioifaces.Branch_C_S_250__S_250d;
import fase16.smtp.Smtp.Smtp.channels.C.ioifaces.Case_C_S_250__S_250d;
import fase16.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S_Ehlo;
import fase16.smtp.Smtp.Smtp.channels.C.ioifaces.Succ_In_S_250;
import fase16.smtp.Smtp.Smtp.roles.C;
import fase16.smtp.message.SmtpMessageFormatter;
import fase16.smtp.message.client.Ehlo;
import fase16.smtp.message.client.Quit;
import fase16.smtp.message.client.StartTls;
import fase16.smtp.message.server._250;
import fase16.smtp.message.server._250d;

public class SmtpC
{
	public SmtpC() throws Exception
	{
		run();
	}

	public static void main(String[] args) throws Exception
	{
		new SmtpC();
	}

	public void run() throws Exception
	{
		String host = "smtp.cc.ic.ac.uk";
		int port = 25;

		Smtp smtp = new Smtp();
		try (MPSTEndpoint<Smtp, C> se = new MPSTEndpoint<>(smtp, Smtp.C, new SmtpMessageFormatter()))
		{
			se.connect(Smtp.S, SocketChannelEndpoint::new, host, port);

			Buf<Smtp_C_1_Future> f1 = new Buf<>();
			Smtp_C_1 s1 = new Smtp_C_1(se);

			/*
			doInit(
				LinearSocket.wrapClient(
					doInit(s1.async(S, _220, b1))
						.to(Select_C_S_StartTls.cast).send(S, new StartTls())
						.to(Receive_C_S_220.cast).async(S, _220)
						.to(Select_C_S_Ehlo.cast)
				, S, SSLSocketChannelWrapper::new)
			)
			.to(Select_C_S_Quit.cast).send(S, new Quit());
			/*/
			doInit(
				LinearSocket.wrapClient(
					doInit(s1.async(S, _220, f1))
						.send(S, new StartTls())
						.async(S, _220)
				, S, SSLSocketChannelWrapper::new)
			)
			.send(S, new Quit());
			//*/

			System.out.println("Server greeting: " + f1.val.sync().msg);
		}
	}

	private Succ_In_S_250 doInitWithCasts(Select_C_S_Ehlo<?> s) throws Exception
	{
		Branch_C_S_250__S_250d<?, ?> b =
				s.send(S, new Ehlo("test")).to(Branch_C_S_250__S_250d.cast);
		Buf<_250> b1 = new Buf<>();
		Buf<_250d> b2 = new Buf<>();
		for (Case_C_S_250__S_250d<?, ?> c = b.branch(S); true; c = b.branch(S))
		{
			switch (c.getOp())
			{
				case _250:
					return printlnBuf(c.receive(S, _250, b1), b1);
				case _250d:
					b = printBuf(c.receive(S, _250d, b2).to(Branch_C_S_250__S_250d.cast), b2);
					break;
			}
		}
	}
	
	private 
	<
		S1 extends Branch_C_S_250__S_250d<S2, S1>,
		S2 extends Succ_In_S_250
	>
	S2 doInit(Select_C_S_Ehlo<S1> s) throws Exception
	{
		Branch_C_S_250__S_250d<S2, S1> b = s.send(S, new Ehlo("test"));
		Buf<_250> b1 = new Buf<>();
		Buf<_250d> b2 = new Buf<>();
		for (Case_C_S_250__S_250d<S2, S1> c = b.branch(S); true; c = b.branch(S))
		{
			switch (c.getOp())
			{
				case _250:
					return printlnBuf(c.receive(S, _250, b1), b1);
				case _250d:
					b = printBuf(c.receive(S, _250d, b2), b2);
					break;
			}
		}
	}

	public static <S, B extends Buf<?>> S printBuf(S s, B b)
	{
		System.out.print(b.val);
		return s;
	}

	public static <S, B extends Buf<?>> S printlnBuf(S s, B b)
	{
		System.out.println(b.val);
		return s;
	}
	
	class MySmtpC3Handler implements Smtp_C_3_Handler
	{
		@Override
		public void receive(Smtp_C_3 s3, fase16.smtp.Smtp.Smtp.ops._250d op, Buf<_250d> arg) throws ScribbleRuntimeException, IOException, ClassNotFoundException
		{
			s3.branch(S, this);
		}

		@Override
		public void receive(Smtp_C_4 s4, fase16.smtp.Smtp.Smtp.ops._250 op, Buf<_250> arg) throws ScribbleRuntimeException, IOException, ClassNotFoundException
		{
			try
			{
				doInit(
					LinearSocket.wrapClient(
						s4.send(S, new StartTls())
							.async(S, _220)
					, S, SSLSocketChannelWrapper::new)
				)
				.send(S, new Quit());
			}
			catch (ScribbleRuntimeException | IOException | ClassNotFoundException x)
			{
				throw x;
			}
			catch (Exception x)
			{
				throw new ScribbleRuntimeException(x);
			}
		}
	}
}

