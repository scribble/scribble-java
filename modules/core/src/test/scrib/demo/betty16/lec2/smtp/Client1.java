package demo.betty16.lec2.smtp;

import static demo.betty16.lec2.smtp.Smtp.Smtp.Smtp.C;
import static demo.betty16.lec2.smtp.Smtp.Smtp.Smtp.S;
import static demo.betty16.lec2.smtp.Smtp.Smtp.Smtp._220;
import static demo.betty16.lec2.smtp.Smtp.Smtp.Smtp._250;
import static demo.betty16.lec2.smtp.Smtp.Smtp.Smtp._250d;

import org.scribble.net.Buf;
import org.scribble.net.scribsock.LinearSocket;
import org.scribble.net.session.SSLSocketChannelWrapper;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.betty16.lec2.smtp.Smtp.Smtp.Smtp;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.EndSocket;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_1;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_2;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_3;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_3_Cases;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_4;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_6;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_7;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_7_Cases;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_8;
import demo.betty16.lec2.smtp.Smtp.Smtp.roles.C;
import demo.betty16.lec2.smtp.message.SmtpMessageFormatter;
import demo.betty16.lec2.smtp.message.client.Ehlo;
import demo.betty16.lec2.smtp.message.client.Quit;
import demo.betty16.lec2.smtp.message.client.StartTls;

public class Client1
{
	public static void main(String[] args) throws Exception {
		String host = "smtp.cc.ic.ac.uk";
		int port = 25;

		Smtp smtp = new Smtp();
		try (SessionEndpoint<Smtp, C> client = new SessionEndpoint<>(smtp, C, new SmtpMessageFormatter())) {
			client.connect(S, SocketChannelEndpoint::new, host, port);
			new Client1().run(new Smtp_C_1(client));
		}
	}

	private EndSocket run(Smtp_C_1 c1) throws Exception {
		return
			doInit(
					doStartTls(
							doInit(c1.async(S, _220)))
			)
			.send(S, new Quit());
	}
	
	private Smtp_C_4 doInit(Smtp_C_2 c2) throws Exception {
		Smtp_C_3 c3 = c2.send(S, new Ehlo("test"));
		Buf<Object> buf = new Buf<>();
		while (true) {
			Smtp_C_3_Cases cases = c3.branch(S);
			switch (cases.getOp()) {
				case _250d: c3 = cases.receive(S, _250d, buf); System.out.print(buf.val); break;
				case _250:  return printlnBuf(cases.receive(S, _250, buf), buf);
			}
		}
	}

	private Smtp_C_6 doStartTls(Smtp_C_4 c4) throws Exception {
		return
				LinearSocket.wrapClient(
						c4.send(S, new StartTls())
							.async(S, _220)
				, S, SSLSocketChannelWrapper::new);
	}

	private Smtp_C_8 doInit(Smtp_C_6 c6) throws Exception {
		Smtp_C_7 c7 = c6.send(S, new Ehlo("test"));
		Buf<Object> buf = new Buf<>();
		while (true) {
			Smtp_C_7_Cases cases = c7.branch(S);
			switch (cases.getOp()) {
				case _250d: c7 = cases.receive(S, _250d, buf); System.out.print(buf.val); break;
				case _250:  return printlnBuf(cases.receive(S, _250, buf), buf);
			}
		}
	}

	private static <S, B extends Buf<?>> S printlnBuf(S s, B b) {
		System.out.println(b.val);
		return s;
	}
}

