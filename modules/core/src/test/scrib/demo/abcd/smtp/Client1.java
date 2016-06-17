//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' demo.abcd.smtp.Client1


package demo.abcd.smtp;

import static demo.abcd.smtp.Smtp.Smtp.Smtp.C;
import static demo.abcd.smtp.Smtp.Smtp.Smtp.S;
import static demo.abcd.smtp.Smtp.Smtp.Smtp._220;

import org.scribble.net.Buf;
import org.scribble.net.scribsock.LinearSocket;
import org.scribble.net.session.SSLSocketChannelWrapper;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.abcd.smtp.Smtp.Smtp.Smtp;
import demo.abcd.smtp.Smtp.Smtp.channels.C.Smtp_C_1;
import demo.abcd.smtp.Smtp.Smtp.channels.C.Smtp_C_2;
import demo.abcd.smtp.Smtp.Smtp.channels.C.Smtp_C_3;
import demo.abcd.smtp.Smtp.Smtp.channels.C.Smtp_C_3_Cases;
import demo.abcd.smtp.Smtp.Smtp.channels.C.Smtp_C_4;
import demo.abcd.smtp.Smtp.Smtp.channels.C.Smtp_C_6;
import demo.abcd.smtp.Smtp.Smtp.channels.C.Smtp_C_7;
import demo.abcd.smtp.Smtp.Smtp.channels.C.Smtp_C_7_Cases;
import demo.abcd.smtp.Smtp.Smtp.channels.C.Smtp_C_8;
import demo.abcd.smtp.Smtp.Smtp.roles.C;
import demo.abcd.smtp.message.SmtpMessageFormatter;
import demo.abcd.smtp.message.client.Ehlo;
import demo.abcd.smtp.message.client.Quit;
import demo.abcd.smtp.message.client.StartTls;
import demo.abcd.smtp.message.server._250;
import demo.abcd.smtp.message.server._250d;

import static demo.abcd.smtp.Smtp.Smtp.Smtp.*;

public class Client1
{
	public Client1() throws Exception
	{
		run();
	}

	public static void main(String[] args) throws Exception
	{
		new Client1();
	}

	public void run() throws Exception
	{
		String host = "smtp.cc.ic.ac.uk";
		int port = 25;

		Smtp smtp = new Smtp();
		try (SessionEndpoint<Smtp, C> se = new SessionEndpoint<>(smtp, C, new SmtpMessageFormatter()))
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
