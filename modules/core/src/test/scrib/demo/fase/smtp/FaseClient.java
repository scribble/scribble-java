//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' demo.fase.smtp.Client2


package demo.fase.smtp;

import static demo.fase.smtp.Smtp.Smtp.Smtp.S;
import static demo.fase.smtp.Smtp.Smtp.Smtp._220;
import static demo.fase.smtp.Smtp.Smtp.Smtp._250;
import static demo.fase.smtp.Smtp.Smtp.Smtp._250d;

import org.scribble.net.Buf;
import org.scribble.net.scribsock.LinearSocket;
import org.scribble.net.session.SSLSocketChannelWrapper;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.fase.smtp.Smtp.Smtp.Smtp;
import demo.fase.smtp.Smtp.Smtp.channels.C.Smtp_C_1;
import demo.fase.smtp.Smtp.Smtp.channels.C.Smtp_C_1_Future;
import demo.fase.smtp.Smtp.Smtp.channels.C.ioifaces.Branch_C_S_250__S_250d;
import demo.fase.smtp.Smtp.Smtp.channels.C.ioifaces.Case_C_S_250__S_250d;
import demo.fase.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S_Ehlo;
import demo.fase.smtp.Smtp.Smtp.channels.C.ioifaces.Succ_In_S_250;
import demo.fase.smtp.Smtp.Smtp.roles.C;
import demo.fase.smtp.message.SmtpMessageFormatter;
import demo.fase.smtp.message.client.Ehlo;
import demo.fase.smtp.message.client.Quit;
import demo.fase.smtp.message.client.StartTls;
import demo.fase.smtp.message.server._250;
import demo.fase.smtp.message.server._250d;

public class FaseClient
{
	public FaseClient() throws Exception
	{
		run();
	}

	public static void main(String[] args) throws Exception
	{
		new FaseClient();
	}

	public void run() throws Exception
	{
		String host = "smtp.cc.ic.ac.uk";
		int port = 25;

		Smtp smtp = new Smtp();
		try (SessionEndpoint<Smtp, C> se = new SessionEndpoint<>(smtp, Smtp.C, new SmtpMessageFormatter()))
		{
			se.connect(Smtp.S, SocketChannelEndpoint::new, host, port);

			Buf<Smtp_C_1_Future> b1 = new Buf<>();
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
			doInit1(
				LinearSocket.wrapClient(
					doInit1(s1.async(S, _220, b1))
						.send(S, new StartTls())
						.async(S, _220)
				, S, SSLSocketChannelWrapper::new)
			)
			.send(S, new Quit());
			//*/

			System.out.println("b1: " + b1.val.sync().msg);
		}
	}

	private Succ_In_S_250 doInit(Select_C_S_Ehlo<?> s) throws Exception
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
	S2 doInit1(Select_C_S_Ehlo<S1> s) throws Exception
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
}
