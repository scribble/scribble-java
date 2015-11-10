//$ java -cp modules/cli/target/classes/';'modules/core/target/classes';'modules/trace/target/classes';'modules/parser/target/classes';c:\Users\Raymond\.m2\repository\org\antlr\antlr-runtime\3.2\antlr-runtime-3.2.jar;'modules/validation/target/classes/';'modules/projection/target/classes/';C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-mapper-asl\1.9.9\jackson-mapper-asl-1.9.9.jar;C:\Users\Raymond\.m2\repository\org\codehaus\jackson\jackson-core-asl\1.9.9\jackson-core-asl-1.9.9.jar' demo.smtp.SimpleClient


package demo.smtp;

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
import demo.smtp.Smtp.Smtp.channels.C.Smtp_C_2;
import demo.smtp.Smtp.Smtp.channels.C.Smtp_C_3;
import demo.smtp.Smtp.Smtp.channels.C.Smtp_C_3_Cases;
import demo.smtp.Smtp.Smtp.channels.C.Smtp_C_4;
import demo.smtp.Smtp.Smtp.channels.C.Smtp_C_6;
import demo.smtp.Smtp.Smtp.channels.C.Smtp_C_7;
import demo.smtp.Smtp.Smtp.channels.C.Smtp_C_7_Cases;
import demo.smtp.Smtp.Smtp.channels.C.Smtp_C_8;
import demo.smtp.Smtp.Smtp.channels.C.Smtp_C_9_Cases;
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

import static demo.smtp.Smtp.Smtp.Smtp.*;

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
											doEhlo(new Smtp_C_1(se).async(S, _220), ehlo))
							, ehlo))
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
						//.async(S, _250)
						.receive(S, _250, new Buf<>())  // Final sync needed for session to be successful?
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

	private Smtp_C_4 doEhlo(Smtp_C_2 s2, String ehlo) throws ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	{
		Smtp_C_3 s3 = s2.send(S, new Ehlo(ehlo));
		while (true)
		{	
			Smtp_C_3_Cases cases = s3.branch(S);
			switch (cases.op)
			{
				case _250:
				{
					return cases.receive(_250);
				}
				case _250d:
				{
					s3 = cases.receive(_250d);
					break;
				}
			}
		}
	}

	private Smtp_C_6 doStartTls(Smtp_C_4 s4) throws ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	{
		return LinearSocket.wrapClient(
						s4.send(S, new StartTls())
							.async(S, _220)
				, S, SSLSocketChannelWrapper::new);
	}

	//... FIXME: factor out with other doEhlo ...
	private Smtp_C_8 doEhlo(Smtp_C_6 s6, String ehlo) throws ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	{
		Smtp_C_7 s7 = s6.send(S, new Ehlo(ehlo));
		while (true)
		{	
			Smtp_C_7_Cases s7cases = s7.branch(S);
			switch(s7cases.op)
			{
				case _250d:
				{
					s7 = s7cases.receive(_250d);
					break;
				}
				case _250:
				{
					return s7cases.receive(_250);
				}
			}
		}
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

	protected static String getAuthPlain() throws IOException
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
