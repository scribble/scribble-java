package demo.smtp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buff;
import org.scribble.net.session.SSLSocketChannelWrapper;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.smtp.SMTP_C_11.SMTP_C_11Enum;
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

public class SmtpClient
{
	public SmtpClient() throws ScribbleRuntimeException, IOException
	{
		run();
	}

	public static void main(String[] args) throws Exception
	{
		new SmtpClient();
	}

	public void run() throws ScribbleRuntimeException, IOException
	{
		SMTP smtp = new SMTP();
		SessionEndpoint se = smtp.project(SMTP.C, new SmtpMessageFormatter());
		
		String host = "smtp.cc.ic.ac.uk";
		int port = 25;
		
		try (SMTP_C_0 init = new SMTP_C_0(se))
		{
			init.connect(SocketChannelEndpoint::new, SMTP.S, host, port);
			SMTP_C_1 s1 = init.init();
			
			Buff<Future_SMTP_C_1> b220 = new Buff<>();
			SMTP_C_2 s2 = s1.async(SMTP._220, b220);
			System.out.print("Greeting: " + b220.val.sync().msg);

			SMTP_C_4 s4 = doEhlo(s2);
			SMTP_C_6 s6 = doStartTls(s4);
			SMTP_C_8 s8 = doEhlo(s6);
			SMTP_C_10 s10 = doAuth(s8);

			SMTP_C_20 s20 = s10.send(SMTP.S, new Mail("rhu@doc.ic.ac.uk")).branch();
			SMTP_C_12 s12 = null;
			switch (s20.op)
			{
				case _250:
				{
					s12 = s20.receive(SMTP._250);
					break;
				}
				case _501:  // FIXME: "functional interface" for quit states
				{
					s20.receive(SMTP._501).send(SMTP.S, new Quit());
					System.exit(0);
				}
			}
			s12.send(SMTP.S, new Rcpt("raymond.hu05@imperial.ac.uk"))
			   .async(SMTP._250)
			   .send(SMTP.S, new Data()) 
			   .async(SMTP._354)
			   .send(SMTP.S, new Subject("test"))
			   .send(SMTP.S, new DataLine("body"))
			   .send(SMTP.S, new EndOfData())
			   .receive(SMTP._250, new Buff<>())  // Final sync needed for session to be successful?
			   .send(SMTP.S, new Quit());
		}
		catch (Exception e)
		{
			if (e instanceof ScribbleRuntimeException)
			{
				throw (ScribbleRuntimeException) e;
			}
			throw new ScribbleRuntimeException(e);
		}
	}

	private SMTP_C_4 doEhlo(SMTP_C_2 s2) throws ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	{
		SMTP_C_3 s3 = s2.send(SMTP.S, new Ehlo("testing1"));
		Buff<Object> b = new Buff<>();
		while (true)
		{	
			SMTP_C_17 s17 = s3.branch();
			switch (s17.op)
			{
				case _250:
				{
					SMTP_C_4 s4 = s17.receive(SMTP._250, b);
					System.out.print("Ehlo: " + b.val);
					return s4;
				}
				case _250_:
				{
					s3 = s17.receive(SMTP._250_, b);
					System.out.print("Ehlo: " + b.val);
					break;
				}
			}
		}
	}

	private SMTP_C_6 doStartTls(SMTP_C_4 s4) throws ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	{
		Buff<Object> b = new Buff<>();
		SMTP_C_6 s6 = s4.send(SMTP.S, new StartTls()).receive(SMTP._220, b);
		System.out.print("StartTLS: " + b.val);
		s6.wrapClient(SSLSocketChannelWrapper::new, SMTP.S);
		return s6;
	}

	// FIXME: factor out with other doEhlo
	private SMTP_C_8 doEhlo(SMTP_C_6 s6) throws ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	{
		SMTP_C_7 s7 = s6.send(SMTP.S, new Ehlo("testing2"));
		Buff<Object> b = new Buff<>();
		while (true)
		{	
			SMTP_C_18 s18 = s7.branch();
			switch(s18.op)
			{
				case _250:
				{
					SMTP_C_8 s8 = s18.receive(SMTP._250, b);
					System.out.print("Ehlo: " + b.val);
					return s8;
				}
				case _250_:
				{
					s7 = s18.receive(SMTP._250_, b);
					System.out.print("Ehlo: " + b.val);
					break;
				}
			}
		}
	}

	private SMTP_C_10 doAuth(SMTP_C_8 s8) throws ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	{
		SMTP_C_19 s19 = s8.send(SMTP.S, new Auth(getAuthPlain())).branch();
		Buff<Object> b = new Buff<>();
		switch (s19.op)
		{
			case _235:
			{
				SMTP_C_10 s10 = s19.receive(SMTP._235, b);
				System.out.print("Auth: " + b.val);
				return s10;
			}
			case _535:
			{
				s19.receive(SMTP._535, b).send(SMTP.S, new Quit());
				System.out.print("Auth: " + b.val);
				System.exit(0);
			}
			default:
			{
				throw new RuntimeException("Shouldn't get in here: " + s19.op);
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
}

class EraserThread extends Thread
{
	private boolean stop = true;

	public void run()
	{
		try
		{
			while (this.stop)
			{
				System.out.print("\010 ");
				Thread.sleep(1);
			}
		}
		catch (InterruptedException e)
		{
			throw new RuntimeException(e);
		}
	}

	public void stopMasking()
	{
		this.stop = false;
	}
}
