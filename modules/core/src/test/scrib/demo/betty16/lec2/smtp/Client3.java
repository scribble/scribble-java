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
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_1;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.ioifaces.Branch_C_S_250__S_250d;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.ioifaces.Case_C_S_250__S_250d;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S_Ehlo;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.ioifaces.Succ_In_S_250;
import demo.betty16.lec2.smtp.Smtp.Smtp.roles.C;
import demo.betty16.lec2.smtp.message.SmtpMessageFormatter;
import demo.betty16.lec2.smtp.message.client.Ehlo;
import demo.betty16.lec2.smtp.message.client.Quit;
import demo.betty16.lec2.smtp.message.client.StartTls;

public class Client3 {

	public static void main(String[] args) throws Exception {
		String host = "smtp.cc.ic.ac.uk";
		int port = 25;

		Smtp smtp = new Smtp();
		try (SessionEndpoint<Smtp, C> se = new SessionEndpoint<>(smtp, C, new SmtpMessageFormatter())) {
			se.connect(Smtp.S, SocketChannelEndpoint::new, host, port);
			new Client3().run(new Smtp_C_1(se));
		}
	}

	private void run(Smtp_C_1 c1) throws Exception {
		doInit(
				LinearSocket.wrapClient(
						doInit(c1.async(S, _220))
							.send(S, new StartTls())
							.async(S, _220)
				, S, SSLSocketChannelWrapper::new)
		)
		.send(S, new Quit());
	}

	private 
	<
		S1 extends Branch_C_S_250__S_250d<S2, S1>,   // S1 = S?{ 250: S2, 250d: S1 }
		S2 extends Succ_In_S_250                     // S2 = Succ(S?250)
	>
	// S!Ehlo.S1  ->  S2
	S2 doInit(Select_C_S_Ehlo<S1> s) throws Exception {
		Branch_C_S_250__S_250d<S2, S1> b = s.send(S, new Ehlo("test"));
		Buf<Object> buf = new Buf<>();
		for (Case_C_S_250__S_250d<S2, S1> c = b.branch(S); true; c = b.branch(S)) {
			switch (c.getOp()) {
				case _250:  return printlnBuf(c.receive(S, _250, buf), buf);
				case _250d: b = c.receive(S, _250d, buf); System.out.print(buf.val); break;
			}
		}
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
