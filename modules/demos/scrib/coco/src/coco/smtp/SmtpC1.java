//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/parser/target/classes';'modules/demos/target/classes/ coco.smtp.Client1


package coco.smtp;

import static coco.smtp.Smtp.Smtp.Smtp.C;
import static coco.smtp.Smtp.Smtp.Smtp.S;
import static coco.smtp.Smtp.Smtp.Smtp._220;

import org.scribble.net.Buf;
import org.scribble.net.scribsock.LinearSocket;
import org.scribble.net.session.SSLSocketChannelWrapper;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import coco.smtp.Smtp.Smtp.Smtp;
import coco.smtp.Smtp.Smtp.channels.C.Smtp_C_1;
import coco.smtp.Smtp.Smtp.channels.C.Smtp_C_2;
import coco.smtp.Smtp.Smtp.channels.C.Smtp_C_3;
import coco.smtp.Smtp.Smtp.channels.C.Smtp_C_3_Cases;
import coco.smtp.Smtp.Smtp.channels.C.Smtp_C_4;
import coco.smtp.Smtp.Smtp.channels.C.Smtp_C_6;
import coco.smtp.Smtp.Smtp.channels.C.Smtp_C_7;
import coco.smtp.Smtp.Smtp.channels.C.Smtp_C_7_Cases;
import coco.smtp.Smtp.Smtp.channels.C.Smtp_C_8;
import coco.smtp.Smtp.Smtp.roles.C;
import coco.smtp.message.SmtpMessageFormatter;
import coco.smtp.message.client.Ehlo;
import coco.smtp.message.client.Quit;
import coco.smtp.message.client.StartTls;
import coco.smtp.message.server._250;
import coco.smtp.message.server._250d;

import static coco.smtp.Smtp.Smtp.Smtp.*;

public class SmtpC1
{
	public SmtpC1() throws Exception
	{
		run();
	}

	public static void main(String[] args) throws Exception
	{
		new SmtpC1();
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
			doInit(
				doStartTls(
					doInit(s1.receive(S, _220, new Buf<>()))
				)
			)
			.send(S, new Quit());
		}
	}

	private Smtp_C_4 doInit(Smtp_C_2 s2) throws Exception
	{
		Smtp_C_3 s3 = s2.send(S, new Ehlo("test"));
		Buf<_250> b1 = new Buf<>();
		Buf<_250d> b2 = new Buf<>();
		while (true)
		{
			Smtp_C_3_Cases c = s3.branch(S);
			switch (c.op)
			{
				case _250:
					return printlnBuf(c.receive(S, _250, b1), b1);
				case _250d:
					s3 = printBuf(c.receive(S, _250d, b2), b2);
					break;
			}
		}
	}

	private Smtp_C_8 doInit(Smtp_C_6 s6) throws Exception
	{
		Smtp_C_7 s7 = s6.send(S, new Ehlo("test"));
		Buf<_250> b1 = new Buf<>();
		Buf<_250d> b2 = new Buf<>();
		while (true)
		{
			Smtp_C_7_Cases c = s7.branch(S);
			switch (c.op)
			{
				case _250:
					return printlnBuf(c.receive(S, _250, b1), b1);
				case _250d:
					s7 = printBuf(c.receive(S, _250d, b2), b2);
					break;
			}
		}
	}
	
	private Smtp_C_6 doStartTls(Smtp_C_4 s4) throws Exception
	{
		return LinearSocket.wrapClient(
				s4.send(S, new StartTls()).receive(S, _220, new Buf<>()), S, SSLSocketChannelWrapper::new
		);
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
}
