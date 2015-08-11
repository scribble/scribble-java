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

import demo.smtp.message.SmtpMessageFormatter;
import demo.smtp.message.client.Auth;
import demo.smtp.message.client.Ehlo;
import demo.smtp.message.client.Quit;
import demo.smtp.message.client.StartTls;

public class SmtpClient
{
	public SmtpClient() throws ScribbleRuntimeException
	{
		run();
	}

	public static void main(String[] args) throws ScribbleRuntimeException
	{
		new SmtpClient();
	}

	public void run() throws ScribbleRuntimeException
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
			System.out.print("Greeting: " + b220.val.sync());  // FIXME

			SMTP_C_4 s4 = doEhlo(s2);
			SMTP_C_6 s6 = doStartTls(s4);
			SMTP_C_8 s8 = doEhlo(s6);
			SMTP_C_10 s10 = doAuth(s8);
			s10.send(SMTP.S, new Quit());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private SMTP_C_10 doAuth(SMTP_C_8 s8) throws ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	{
		SMTP_C_19 s19 = s8.send(SMTP.S, new Auth(getAuthPlain())).branch();
		
		switch (s19.op)
		{
			case _235:
				Buff<demo.smtp.message.server._235> b235 = new Buff<>();
				SMTP_C_10 s10 = s19.receive(SMTP._235, b235);
				System.out.print("Auth: " + b235.val);
				return s10;
			case _535:
				Buff<demo.smtp.message.server._535> b535 = new Buff<>();
				s19.receive(SMTP._535, b535).send(SMTP.S, new Quit());
					System.out.print("Auth: " + b535.val);
				System.exit(0);
			default:
				throw new RuntimeException("Shouldn't get in here: " + s19.op);
		}
	}

	// FIXME: factor out with other doEhlo
	private SMTP_C_8 doEhlo(SMTP_C_6 s6) throws ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	{
		SMTP_C_7 s7 = s6.send(SMTP.S, new Ehlo("testing2"));
		while (true)
		{	
			SMTP_C_18 s18 = s7.branch();
			switch(s18.op)
			{
				case _250:
				{
					Buff<demo.smtp.message.server._250> b250 = new Buff<>();
					SMTP_C_8 s8 = s18.receive(SMTP._250, b250);
					System.out.print("Ehlo: " + b250.val);
					return s8;
				}
				case _250_:
				{
					Buff<demo.smtp.message.server._250_> b250_ = new Buff<>();
					s7 = s18.receive(SMTP._250_, b250_);
					System.out.print("Ehlo: " + b250_.val);
					break;
				}
			}
		}
	}

	private SMTP_C_6 doStartTls(SMTP_C_4 s4) throws ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	{
		Buff<demo.smtp.message.server._220> b220 = new Buff<>();
		SMTP_C_6 s6 = s4.send(SMTP.S, new StartTls()).receive(SMTP._220, b220);
		System.out.print("StartTLS: " + b220.val);
		s6.wrapClient(SSLSocketChannelWrapper::new, SMTP.S);
		return s6;
	}

	private SMTP_C_4 doEhlo(SMTP_C_2 s2) throws ScribbleRuntimeException, IOException, ClassNotFoundException, ExecutionException, InterruptedException
	{
		SMTP_C_3 s3 = s2.send(SMTP.S, new Ehlo("testing1"));
		while (true)
		{	
			SMTP_C_17 s17 = s3.branch();
			switch (s17.op)
			{
				case _250:
				{
					Buff<demo.smtp.message.server._250> b250 = new Buff<>();
					SMTP_C_4 s4 = s17.receive(SMTP._250, b250);
					System.out.print("Ehlo: " + b250.val);
					return s4;
				}
				case _250_:
				{
					Buff<demo.smtp.message.server._250_> b250_ = new Buff<>();
					s3 = s17.receive(SMTP._250_, b250_);
					System.out.print("Ehlo: " + b250_.val);
					break;
				}
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
