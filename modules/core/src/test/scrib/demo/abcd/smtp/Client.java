//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' demo.abcd.smtp.Client


package demo.abcd.smtp;

import org.scribble.net.Buf;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.abcd.smtp.Smtp.Smtp.Smtp;
import demo.abcd.smtp.Smtp.Smtp.channels.C.Smtp_C_1;
import demo.abcd.smtp.Smtp.Smtp.channels.C.ioifaces.Out_S_Ehlo;
import demo.abcd.smtp.Smtp.Smtp.channels.C.ioifaces.Succ_In_S_250;
import demo.abcd.smtp.Smtp.Smtp.roles.C;
import demo.abcd.smtp.message.SmtpMessageFormatter;
import demo.abcd.smtp.message.server._250;
import demo.abcd.smtp.message.server._250d;

import static demo.abcd.smtp.Smtp.Smtp.Smtp.*;

public class Client
{
	public Client() throws Exception
	{
		run();
	}

	public static void main(String[] args) throws Exception
	{
		new Client();
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
			doInit(s1.receive(S, Smtp._220, new Buf<>()));
		}
	}

	//private Succ_In_S_250 doInit(Select_C_S_Ehlo<?> s) throws Exception
	private <S extends Out_S_Ehlo<?>> Succ_In_S_250 doInit(S s) throws Exception
	{
		Buf<_250> b1 = new Buf<>();
		Buf<_250d> b2 = new Buf<>();
		return null;
	}
}
