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
import test.smtp.Smtp.Smtp.channels.C.ioifaces.Branch_C_S$250d$_S$250;
import test.smtp.Smtp.Smtp.channels.C.ioifaces.Case_C_S$250d$_S$250;
import test.smtp.Smtp.Smtp.channels.C.ioifaces.Receive_C_S$220;
import test.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S$Ehlo;
import test.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S$Quit;
import test.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S$StartTls;
import test.smtp.Smtp.Smtp.channels.C.ioifaces.Succ_In_S$250;
import test.smtp.Smtp.Smtp.roles.C;
import test.smtp.message.SmtpMessageFormatter;
import test.smtp.message.client.Ehlo;
import test.smtp.message.client.Quit;
import test.smtp.message.client.StartTls;
import test.smtp.message.server._250;
import test.smtp.message.server._250_;

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

			doInitiation(
					LinearSocket.wrapClient(
							doInitiation(s1.receive(Smtp.S, Smtp._220, new Buf<>()))
									.to(Select_C_S$StartTls.cast)
									.send(Smtp.S, new StartTls())
									.to(Receive_C_S$220.cast)
									.receive(Smtp.S, Smtp._220, new Buf<>())
									.to(Select_C_S$Ehlo.cast)
					, Smtp.S, SSLSocketChannelWrapper::new)
			)
			.to(Select_C_S$Quit.cast)
			.send(Smtp.S, new Quit());
		}
	}
	
	private Succ_In_S$250 doInitiation(Select_C_S$Ehlo<?> s) throws ClassNotFoundException, ScribbleRuntimeException, IOException
	{
		
		// TODO: add discard methods etc to I/O interfaces
		
		Branch_C_S$250d$_S$250<?, ?> b =
				//s.receive(Smtp.S, Smtp._220, new Buf<>()).to(Select_C_S$Ehlo.cast)
				s.send(Smtp.S, new Ehlo("test"))
				 .to(Branch_C_S$250d$_S$250.cast);
		Buf<_250> b1 = new Buf<>();
		Buf<_250_> b2 = new Buf<>();
		while (true)
		{
			Case_C_S$250d$_S$250<?, ?> c = b.branch(Smtp.S);
			switch (c.getOp())
			{
				case _250d:
				{
					b = c.receive(Smtp.S, Smtp._250d, b2).to(Branch_C_S$250d$_S$250.cast);
					System.out.print(b2.val);
					break;
				}
				case _250:
				{
					Succ_In_S$250 succ = c.receive(Smtp.S, Smtp._250, b1);
					System.out.println(b1.val);
					return succ;
				}
			}
		}
	}
}
