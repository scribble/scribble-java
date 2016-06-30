package demo.betty16.lec2.smtp;

import static demo.betty16.lec2.smtp.Smtp.Smtp.Smtp.*;

import org.scribble.net.scribsock.LinearSocket;
import org.scribble.net.session.SSLSocketChannelWrapper;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.betty16.lec2.smtp.Smtp.Smtp.Smtp;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.EndSocket;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_1;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_4;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.Smtp_C_6;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S_Ehlo;
import demo.betty16.lec2.smtp.Smtp.Smtp.channels.C.ioifaces.Succ_In_S_250;
import demo.betty16.lec2.smtp.Smtp.Smtp.roles.C;
import demo.betty16.lec2.smtp.message.SmtpMessageFormatter;
import demo.betty16.lec2.smtp.message.client.Quit;
import demo.betty16.lec2.smtp.message.client.StartTls;

public class Client {

	public static void main(String[] args) throws Exception {
		String host = "smtp.cc.ic.ac.uk";
		int port = 25;

		Smtp smtp = new Smtp();
		try (SessionEndpoint<Smtp, C> client = new SessionEndpoint<>(smtp, C, new SmtpMessageFormatter())) {
			client.connect(S, SocketChannelEndpoint::new, host, port);
			new Client().run(new Smtp_C_1(client));
		}
	}

	private EndSocket run(Smtp_C_1 c1) throws Exception {
		/*

		return
			doInit(
					doStartTls(
							doInit(c1.async(S, _220)))
			)
			.send(S, new Quit());

		/*/
		throw new RuntimeException("[TODO]: ");
		//*/
	}

	private 
	//  succ(S?250)  <-  S!{ Ehlo: ?? }
	Succ_In_S_250 doInit(Select_C_S_Ehlo<?> c) throws Exception {
		
		throw new RuntimeException("[TODO]: ");
		
	}

	private Smtp_C_6 doStartTls(Smtp_C_4 c4) throws Exception {
		return
				LinearSocket.wrapClient(
						c4.send(S, new StartTls())
							.async(S, _220)
				, S, SSLSocketChannelWrapper::new);
	}
}
