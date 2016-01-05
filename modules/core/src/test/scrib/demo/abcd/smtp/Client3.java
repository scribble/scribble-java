//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' abcd.demo.smtp.Client3


package demo.abcd.smtp;

import static demo.abcd.smtp.Smtp.Smtp.Smtp.C;
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
import demo.abcd.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S_Ehlo;
import demo.abcd.smtp.Smtp.Smtp.channels.C.ioifaces.Succ_In_S_250;
import demo.abcd.smtp.Smtp.Smtp.roles.C;
import demo.abcd.smtp.message.SmtpMessageFormatter;
import demo.abcd.smtp.message.client.Ehlo;
import demo.abcd.smtp.message.client.Quit;
import demo.abcd.smtp.message.client.StartTls;
import demo.abcd.smtp.message.server._250;
import demo.abcd.smtp.message.server._250d;

// "No casts" version -- via generic inference
public class Client3
{
	public Client3() throws Exception
	{
		run();
	}

	public static void main(String[] args) throws Exception
	{
		new Client3();
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
					b = Client1.printBuf(c.receive(S, _250d, b2), b2);
					break;
				case _250:
					return Client1.printlnBuf(c.receive(S, _250, b1), b1);
			}
		}
	}
}
