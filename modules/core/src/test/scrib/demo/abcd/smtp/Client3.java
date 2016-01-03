//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' abcd.demo.smtp.Client3


package demo.abcd.smtp;

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
		try (SessionEndpoint<Smtp, C> se = new SessionEndpoint<>(smtp, Smtp.C, new SmtpMessageFormatter()))
		{
			se.connect(Smtp.S, SocketChannelEndpoint::new, host, port);

			Smtp_C_1 s1 = new Smtp_C_1(se);
			Buf<Smtp_C_1_Future> b = new Buf<>();
			doInit(
				LinearSocket.wrapClient(
					doInit(s1.async(Smtp.S, Smtp._220, b))
						.send(Smtp.S, new StartTls())
						.async(Smtp.S, Smtp._220)
				, Smtp.S, SSLSocketChannelWrapper::new)
			)
			.send(Smtp.S, new Quit());
			
			//System.out.println("b1: " + b.val.sync().msg);
		}
	}

	private <S1 extends Branch_C_S_250__S_250d<S2, S1>, S2 extends Succ_In_S_250>
			S2 doInit(Select_C_S_Ehlo<S1> s) throws Exception
	{
		Branch_C_S_250__S_250d<S2, S1> b = s.send(Smtp.S, new Ehlo("test"));
		Buf<_250> b1 = new Buf<>();
		Buf<_250d> b2 = new Buf<>();
		while (true)
		{
			Case_C_S_250__S_250d<S2, S1> c = b.branch(Smtp.S);
			switch (c.getOp())
			{
				case _250d:
					b = Client1.printBuf(c.receive(Smtp.S, Smtp._250d, b2), b2);
					break;
				case _250:
					return Client1.printlnBuf(c.receive(Smtp.S, Smtp._250, b1), b1);
			}
		}
	}
}
