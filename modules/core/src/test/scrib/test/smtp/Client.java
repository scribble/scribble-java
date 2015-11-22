//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' test.smtp.Client


package test.smtp;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.scribsock.LinearSocket;
import org.scribble.net.session.SSLSocketChannelWrapper;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import test.smtp.Smtp.Smtp.Smtp;
import test.smtp.Smtp.Smtp.channels.C.Smtp_C_1;
import test.smtp.Smtp.Smtp.channels.C.Smtp_C_1_Future;
import test.smtp.Smtp.Smtp.channels.C.ioifaces.Branch_C_S_250__S_250d;
import test.smtp.Smtp.Smtp.channels.C.ioifaces.Case_C_S_250__S_250d;
import test.smtp.Smtp.Smtp.channels.C.ioifaces.Receive_C_S_220;
import test.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S_Ehlo;
import test.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S_Quit;
import test.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S_StartTls;
import test.smtp.Smtp.Smtp.channels.C.ioifaces.Succ_In_S_250;
import test.smtp.Smtp.Smtp.roles.C;
import test.smtp.message.SmtpMessageFormatter;
import test.smtp.message.client.Ehlo;
import test.smtp.message.client.Quit;
import test.smtp.message.client.StartTls;
import test.smtp.message.server._250;
import test.smtp.message.server._250d;

public class Client
{
	public Client() throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		run();
	}

	public static void main(String[] args) throws Exception
	{
		new Client();
	}

	public void run() throws ScribbleRuntimeException, IOException, ClassNotFoundException
	{
		String host = "smtp.cc.ic.ac.uk";
		int port = 25;

		Smtp smtp = new Smtp();
		try (SessionEndpoint<Smtp, C> se = new SessionEndpoint<>(smtp, Smtp.C, new SmtpMessageFormatter()))
		{
			se.connect(Smtp.S, SocketChannelEndpoint::new, host, port);

			Smtp_C_1 s1 = new Smtp_C_1(se);

			Buf<Smtp_C_1_Future> b1 = new Buf<>();
			//Buf<Smtp_C_5_Future> b2 = new Buf<>();  -- only supported by concrete state chans
			
			doEhloAnd250(
				LinearSocket.wrapClient(
					doEhloAnd250(s1.async(Smtp.S, Smtp._220, b1))
						.to(Select_C_S_StartTls.cast)  // Run-time cast
						.send(Smtp.S, new StartTls())
						.to(Receive_C_S_220.cast)
						//.receive(Smtp.S, Smtp._220, new Buf<>())
						.async(Smtp.S, Smtp._220)
						.to(Select_C_S_Ehlo.cast)  // Safe cast
				, Smtp.S, SSLSocketChannelWrapper::new)
			)
			.to(Select_C_S_Quit.cast)  // Run-time cast
			.send(Smtp.S, new Quit());
			
			System.out.println("b1: " + b1.val.sync().msg);
			//System.out.println("b2: " + b2.val.sync().msg);
		}
	}

	private Succ_In_S_250 doEhloAnd250(Select_C_S_Ehlo<?> s) throws ClassNotFoundException, ScribbleRuntimeException, IOException
	{
		Branch_C_S_250__S_250d<?, ?> b =
				s.send(Smtp.S, new Ehlo("test"))
				 .to(Branch_C_S_250__S_250d.cast);  // Safe cast
		Buf<_250> b1 = new Buf<>();
		Buf<_250d> b2 = new Buf<>();
		while (true)
		{
			Case_C_S_250__S_250d<?, ?> c = b.branch(Smtp.S);
			switch (c.getOp())
			{
				case _250d:
				{
					b = c.receive(Smtp.S, Smtp._250d, b2).to(Branch_C_S_250__S_250d.cast);
					System.out.print(b2.val);
					break;
				}
				case _250:
				{
					Succ_In_S_250 succ = c.receive(Smtp.S, Smtp._250, b1);
					System.out.println(b1.val);
					return succ;
				}
			}
		}
	}
	
	/*private Succ_In_S_250 doInitiation(Receive_C_S_220<?> s) throws ClassNotFoundException, ScribbleRuntimeException, IOException
	{
		Branch_C_S_250d__S_250<?, ?> b =
				s.receive(Smtp.S, Smtp._220, new Buf<>()).to(Select_C_S_Ehlo.cast)
				 .send(Smtp.S, new Ehlo("test"))
				 .to(Branch_C_S_250d__S_250.cast);  // Safe cast
		Buf<_250> b1 = new Buf<>();
		Buf<_250_> b2 = new Buf<>();
		while (true)
		{
			Case_C_S_250d__S_250<?, ?> c = b.branch(Smtp.S);
			switch (c.getOp())
			{
				case _250d:
				{
					b = c.receive(Smtp.S, Smtp._250d, b2).to(Branch_C_S_250d__S_250.cast);
					System.out.print(b2.val);
					break;
				}
				case _250:
				{
					Succ_In_S_250 succ = c.receive(Smtp.S, Smtp._250, b1);
					System.out.println(b1.val);
					return succ;
				}
			}
		}
	}*/
}
