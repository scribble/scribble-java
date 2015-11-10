//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' demo.smtp.SimpleClient


package demo.smtp;

import static demo.smtp.Smtp.Smtp.Smtp.C;
import static demo.smtp.Smtp.Smtp.Smtp.S;
import static demo.smtp.Smtp.Smtp.Smtp._220;
import static demo.smtp.Smtp.Smtp.Smtp._235;
import static demo.smtp.Smtp.Smtp.Smtp._250;
import static demo.smtp.Smtp.Smtp.Smtp._250d;
import static demo.smtp.Smtp.Smtp.Smtp._354;
import static demo.smtp.Smtp.Smtp.Smtp._501;
import static demo.smtp.Smtp.Smtp.Smtp._535;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.scribsock.LinearSocket;
import org.scribble.net.session.SSLSocketChannelWrapper;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.smtp.Smtp.Smtp.Smtp;
import demo.smtp.Smtp.Smtp.channels.C.Smtp_C_1;
import demo.smtp.Smtp.Smtp.channels.C.Smtp_C_10;
import demo.smtp.Smtp.Smtp.channels.C.Smtp_C_11_Cases;
import demo.smtp.Smtp.Smtp.channels.C.Smtp_C_4;
import demo.smtp.Smtp.Smtp.channels.C.Smtp_C_6;
import demo.smtp.Smtp.Smtp.channels.C.Smtp_C_8;
import demo.smtp.Smtp.Smtp.channels.C.Smtp_C_9_Cases;
import demo.smtp.Smtp.Smtp.channels.C.ioifaces.Branch_C_S_250d__S_250;
import demo.smtp.Smtp.Smtp.channels.C.ioifaces.Case_C_S_250d__S_250;
import demo.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S_Auth__S_Quit;
import demo.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S_Ehlo__S_Quit;
import demo.smtp.Smtp.Smtp.channels.C.ioifaces.Select_C_S_StartTls__S_Quit;
import demo.smtp.Smtp.Smtp.channels.C.ioifaces.Succ_In_S_250;
import demo.smtp.Smtp.Smtp.channels.C.ioifaces.Succ_Out_S_Ehlo;
import demo.smtp.Smtp.Smtp.roles.C;
import demo.smtp.message.SmtpMessageFormatter;
import demo.smtp.message.client.Auth;
import demo.smtp.message.client.Data;
import demo.smtp.message.client.DataLine;
import demo.smtp.message.client.Ehlo;
import demo.smtp.message.client.EndOfData;
import demo.smtp.message.client.Mail;
import demo.smtp.message.client.Quit;
import demo.smtp.message.client.Rcpt;
import demo.smtp.message.client.StartTls;
import demo.smtp.message.client.Subject;

public class SimpleClient
{
	public SimpleClient() throws ScribbleRuntimeException, IOException
	{
		run();
	}

	public void run() throws ScribbleRuntimeException, IOException
	{
		String host = "smtp.cc.ic.ac.uk";
		int port = 25;

		String ehlo = "user.testing.com";
		String mail = "rhu@doc.ic.ac.uk";  // Sender
		String rcpt = "raymond.hu05@imperial.ac.uk";
		String subj = "test";
		String body = "body";

		Smtp smtp = new Smtp();
		try (SessionEndpoint<Smtp, C> se = new SessionEndpoint<>(smtp, C, new SmtpMessageFormatter()))
		{
			se.connect(S, SocketChannelEndpoint::new, host, port);

			Smtp_C_11_Cases cases =
					doAuth(
						doEhlo(
							doStartTls(
								doEhlo(new Smtp_C_1(se).async(S, _220), ehlo).to(Select_C_S_StartTls__S_Quit.cast).to(Smtp_C_4.cast)
							)
						, ehlo).to(Select_C_S_Auth__S_Quit.cast).to(Smtp_C_8.cast))
					.send(S, new Mail(mail))
					.branch(S);
			switch (cases.getOp())
			{
				case _250:
				{
					cases.receive(_250)
						.send(S, new Rcpt(rcpt))
						.async(S, _250)
						.send(S, new Data())
						.async(S, _354)
						.send(S, new Subject(subj))
						.send(S, new DataLine(body))
						.send(S, new EndOfData())
						.receive(S, _250, new Buf<>())
						//.async(S, _250)
						.send(S, new Quit());
					break;
				}
				case _501:
				{
					cases.receive(_501).send(S, new Quit());
				}
			}
		}
		catch (Exception e)  // FIXME
		{
			if (e instanceof ScribbleRuntimeException)
			{
				throw (ScribbleRuntimeException) e;
			}
			throw new ScribbleRuntimeException(e);
		}
	}

	private Succ_In_S_250 doEhlo(Select_C_S_Ehlo__S_Quit<?, ?> s, String ehlo) throws ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	{
		Branch_C_S_250d__S_250<?, ?> bra = s.send(S, new Ehlo(ehlo)).to(Branch_C_S_250d__S_250.cast);
		while (true)
		{	
			Case_C_S_250d__S_250<?, ?> cases = bra.branch(S);
			switch (cases.getOp())
			{
				case _250:
				{
					return cases.receive(_250);
				}
				case _250d:
				{
					bra = cases.receive(_250d).to(Branch_C_S_250d__S_250.cast);
					break;
				}
			}
		}
	}

	private Smtp_C_6 doStartTls(Smtp_C_4 s4) throws ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	//private Select_C_S_Ehlo__S_Quit<?, ?> doStartTls(Out_S_StartTls<?> s4) throws ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	{
		return
				LinearSocket.wrapClient(
						s4.send(S, new StartTls())//.to(Receive_C_S_220.cast)
							.async(S, _220)
				, S, SSLSocketChannelWrapper::new);//.to(Select_C_S_Ehlo__S_Quit.cast);
	}

	private Smtp_C_10 doAuth(Smtp_C_8 s8) throws ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	{
		Smtp_C_9_Cases s9cases = s8.send(S, new Auth(getAuthPlain())).branch(S);
		switch (s9cases.op)
		{
			case _235:
			{
				return s9cases.receive(_235);
			}
			case _535:
			{
				s9cases.receive(_535).send(S, new Quit());
				System.exit(0);
			}
			default:  // To satisfy Java typing for return
			{
				throw new RuntimeException("Won't get in here: " + s9cases.op);
			}
		}
	}

	private String getAuthPlain() throws IOException
	{
		return myGetAuthPlain();
	}

	private static String myGetAuthPlain() throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String user;
		String pw;

		System.out.println("User: ");
		user = br.readLine();

		System.out.println("Password: ");
		EraserThread et = new EraserThread();
		et.start();
		pw = br.readLine();
		et.stopMasking();

		byte[] u = user.getBytes("utf-8");
		byte[] p = pw.getBytes("utf-8");
		byte[] bs = new byte[u.length + p.length + 2];
		System.arraycopy(u, 0, bs, 1, u.length);
		System.arraycopy(p, 0, bs, u.length + 2, p.length);

		return Base64.getEncoder().encodeToString(bs);
	}

	public static void main(String[] args) throws Exception
	{
		new SimpleClient();
	}
}
