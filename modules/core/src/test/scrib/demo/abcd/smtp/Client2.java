//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' demo.abcd.smtp.Client2


package demo.abcd.smtp;

import static demo.abcd.smtp.Smtp.Smtp.Smtp.S;
import static demo.abcd.smtp.Smtp.Smtp.Smtp._220;
import static demo.abcd.smtp.Smtp.Smtp.Smtp._250;
import static demo.abcd.smtp.Smtp.Smtp.Smtp._250d;

import org.scribble.net.Buf;
import org.scribble.net.scribsock.LinearSocket;
import org.scribble.net.session.SSLSocketChannelWrapper;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.abcd.smtp.Smtp.Smtp.Smtp;
import demo.abcd.smtp.Smtp.Smtp.channels.C.Smtp_C_1;
import demo.abcd.smtp.Smtp.Smtp.channels.C.Smtp_C_1_Future;
import demo.abcd.smtp.Smtp.Smtp.channels.C.ioifaces.Branch_C_S_250__S_250d;
import demo.abcd.smtp.Smtp.Smtp.channels.C.ioifaces.Case_C_S_250__S_250d;
import demo.abcd.smtp.Smtp.Smtp.channels.C.ioifaces.Receive_C_S_220;
import demo.abcd.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S_Ehlo;
import demo.abcd.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S_Quit;
import demo.abcd.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S_StartTls;
import demo.abcd.smtp.Smtp.Smtp.channels.C.ioifaces.Succ_In_S_250;
import demo.abcd.smtp.Smtp.Smtp.roles.C;
import demo.abcd.smtp.message.SmtpMessageFormatter;
import demo.abcd.smtp.message.client.Ehlo;
import demo.abcd.smtp.message.client.Quit;
import demo.abcd.smtp.message.client.StartTls;
import demo.abcd.smtp.message.server._250;
import demo.abcd.smtp.message.server._250d;

public class Client2
{
	public Client2() throws Exception
	{
		run();
	}

	public static void main(String[] args) throws Exception
	{
		new Client2();
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
			//Buf<Smtp_C_5_Future> b2 = new Buf<>();  // only supported by concrete state chans
			doInit(
				LinearSocket.wrapClient(
					doInit(s1.async(S, _220, b1))
						.to(Select_C_S_StartTls.cast).send(S, new StartTls())  // Run-time cast
						.to(Receive_C_S_220.cast)  // Safe cast
						//.receive(S, _220, new Buf<>())
						.async(S, _220)
						.to(Select_C_S_Ehlo.cast)  // Safe cast
				, S, SSLSocketChannelWrapper::new)
			)
			.to(Select_C_S_Quit.cast).send(S, new Quit());  // Run-time cast

			//b1.val.sync();
			//System.out.println("b1: " + b1.val.sync().msg);
			//System.out.println("b2: " + b2.val.sync().msg);
		}
	}

	//private <S extends Out_S_Ehlo<?>> Succ_In_S_250 doInit(S s) throws Exception
	//private Succ_In_S_250 doInit(Select_C_S_Ehlo__S_Quit<?, ?> s) throws Exception
	private Succ_In_S_250 doInit(Select_C_S_Ehlo<?> s) throws Exception
	{
		/*
		...
	}
	/*/
		Branch_C_S_250__S_250d<?, ?> b =
				s.send(S, new Ehlo("test"))
				 .to(Branch_C_S_250__S_250d.cast);  // Safe cast
		Buf<_250> b1 = new Buf<>();
		Buf<_250d> b2 = new Buf<>();
		while (true)
		{
			Case_C_S_250__S_250d<?, ?> c = b.branch(S);
			switch (c.getOp())
			{
				case _250:
					return Client1.printlnBuf(c.receive(S, _250, b1), b1);
				case _250d:
					b = Client1.printBuf(
								c.receive(S, _250d, b2)
								 .to(Branch_C_S_250__S_250d.cast)  // Safe cast
							, b2);
					break;
			}
		}
	}
	//*/
}

	


	/*private Succ_In_S_250 doInitiation(Receive_C_S_220<?> s) throws Exception
	{
		Branch_C_S_250d__S_250<?, ?> b =
				s.receive(S, _220, new Buf<>()).to(Select_C_S_Ehlo.cast)
				 .send(S, new Ehlo("test"))
				 .to(Branch_C_S_250d__S_250.cast);  // Safe cast
		Buf<_250> b1 = new Buf<>();
		Buf<_250_> b2 = new Buf<>();
		while (true)
		{
			Case_C_S_250d__S_250<?, ?> c = b.branch(S);
			switch (c.getOp())
			{
				case _250d:
				{
					b = c.receive(S, _250d, b2).to(Branch_C_S_250d__S_250.cast);
					System.out.print(b2.val);
					break;
				}
				case _250:
				{
					Succ_In_S_250 succ = c.receive(S, _250, b1);
					System.out.println(b1.val);
					return succ;
				}
			}
		}
	}*/