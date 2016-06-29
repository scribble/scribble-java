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
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.ioifaces.Branch_C_S_250__S_250d;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.ioifaces.Case_C_S_250__S_250d;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.ioifaces.Receive_C_S_220;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S_Ehlo;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S_Quit;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S_StartTls;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.ioifaces.Succ_In_S_220;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.ioifaces.Succ_In_S_250;
import demo.betty16.lec2.smtp.Smtp.Smtp.roles.C;
import demo.betty16.lec2.smtp.message.SmtpMessageFormatter;
import demo.betty16.lec2.smtp.message.client.Ehlo;
import demo.betty16.lec2.smtp.message.client.Quit;
import demo.betty16.lec2.smtp.message.client.StartTls;

public class Client2 {

	public static void main(String[] args) throws Exception {
		String host = "smtp.cc.ic.ac.uk";
		int port = 25;

		Smtp smtp = new Smtp();
		try (SessionEndpoint<Smtp, C> client = new SessionEndpoint<>(smtp, C, new SmtpMessageFormatter())) {
			client.connect(S, SocketChannelEndpoint::new, host, port);
			new Client2().run(new Smtp_C_1(client));
		}
	}

	private EndSocket run(Smtp_C_1 c1) throws Exception {
		return
			doInit(
					doStartTls(
							doInit(c1.async(S, _220)).to(Select_C_S_StartTls.cast)
					).to(Select_C_S_Ehlo.cast)
			)
			.to(Select_C_S_Quit.cast)
			.send(S, new Quit())
			.to(EndSocket.cast);
	}

	private Succ_In_S_250 doInit(Select_C_S_Ehlo<?> c) throws Exception {
		Branch_C_S_250__S_250d<?, ?> b =
				c.send(S, new Ehlo("test")).to(Branch_C_S_250__S_250d.cast);
		Buf<Object> buf = new Buf<>();
		for (Case_C_S_250__S_250d<?, ?> cases = b.branch(S); true; cases = b.branch(S)) {
			switch (cases.getOp()) {
				case _250:  return printlnBuf(cases.receive(S, _250, buf), buf);
				case _250d: b = cases.receive(S, _250d, buf).to(Branch_C_S_250__S_250d.cast); System.out.print(buf.val); break;
			}
		}
	}

	private Succ_In_S_220 doStartTls(Select_C_S_StartTls<?> c) throws Exception {
		return
				LinearSocket.wrapClient(
						c.send(S, new StartTls()).to(Receive_C_S_220.cast)
							.async(S, _220)
				, S, SSLSocketChannelWrapper::new);
	}

	private static <S, B extends Buf<?>> S printlnBuf(S s, B b) {
		System.out.println(b.val);
		return s;
	}

	/*private static <S, B extends Buf<?>> S printBuf(S s, B b) {
		System.out.print(b.val);
		return s;
	}*/
}
