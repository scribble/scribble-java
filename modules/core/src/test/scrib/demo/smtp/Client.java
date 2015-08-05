package demo.smtp;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buff;
import org.scribble.net.session.SessionEndpoint;

import demo.smtp.message.SmtpMessageFormatter;
import demo.smtp.message.client.Ehlo;
import demo.smtp.message.client.Quit;
import demo.smtp.message.client.StartTls;

public class Client
{
	public Client() throws ScribbleRuntimeException
	{
		run();
	}

	public static void main(String[] args) throws ScribbleRuntimeException
	{
		new Client();
	}

	public void run() throws ScribbleRuntimeException
	{
		SMTP smtp = new SMTP();
		SessionEndpoint se = smtp.project(SMTP.C, new SmtpMessageFormatter());
		
		String host = "smtp.cc.ic.ac.uk";
		int port = 25;
		//String host = "localhost";
		//int port = 8080;
		
		try (SMTP_C_0 init = new SMTP_C_0(se))
		{
			init.connect(SMTP.S, host, port);
			SMTP_C_1 s1 = init.init();

			/*Buff<demo.smtp.message.server._220> b220 = new Buff<>();
			s1.receive(SMTP._220, b220).send(SMTP.S, new Quit());
			System.out.println("_220: " + b220.val);*/
			
			Buff<demo.smtp.message.server._220> b220 = new Buff<>();
			SMTP_C_17 s17 = s1.receive(SMTP._220, b220)
					.send(SMTP.S, new Ehlo("testing"))
					.branch();

			System.out.println("_220: " + b220.val);

			SMTP_C_4 s4 = null; 
			EHLO: while (true)
			{	
				switch (s17.op)
				{
					case _250:
					{
						Buff<demo.smtp.message.server._250> b250 = new Buff<>();
						s4 = s17.receive(SMTP._250, b250);
						System.out.println("_250: " + b250.val);
						break EHLO;
					}
					case _250_:
					{
						Buff<demo.smtp.message.server._250_> b250_ = new Buff<>();
						s17 = s17.receive(SMTP._250_, b250_).branch();
						System.out.println("_250_: " + b250_.val);
						break;
					}
				}
			}
			
			s4.send(SMTP.S, new Quit());
			
			/*SMTP_C_6 s6 = s4.send(SMTP.S, new StartTls()).receive(SMTP._220, b220);
			System.out.println("tlsok: " + b220.val);
			
			System.out.println("a: ");

			SocketWrapper sw = se.getSocketEndpoint(SMTP.S).getSocketWrapper();
			SSLSocketFactory fact = (SSLSocketFactory) SSLSocketFactory.getDefault();
			Socket s = sw.getSocket();  // FIXME: check already connected
			SSLSocket s2 = (SSLSocket) fact.createSocket(s, s.getInetAddress().getHostAddress(), s.getPort(), true);
			sw.dis.close();
			
			System.out.println("b: ");

			DataOutputStream dos = new DataOutputStream(s2.getOutputStream());
			DataInputStream dis = new DataInputStream(s2.getInputStream());
			System.out.println("c: ");
			dos.write(("quit\r\n").getBytes("utf-8"));
			System.out.println("d: ");
			dos.flush();

			System.out.println("w: ");*/
			
			/*s6.reconnect(SMTP.S, host, port);  // FIXME: using same message formatter, so consider statefulness of formatter

			System.out.println("b: ");

			//s6.send(SMTP.S, new Quit());
			SocketWrapper sw = se.getSocketEndpoint(SMTP.S).getSocketWrapper();
			sw.dos.write(("quit\r\n").getBytes("utf-8"));
			sw.dos.flush();*/

			/*SMTP_C_7 s7 = s6.send(SMTP.S, new Ehlo("testing2"));

			System.out.println("b: ");

			SMTP_C_18 s18 = s7.branch();
			SMTP_C_8 s8 = null; 
			EHLO: while (true)
			{	
				switch (s18.op)
				{
					case _250:
					{
						Buff<demo.smtp.message.server._250> b250 = new Buff<>();
						s8 = s18.receive(SMTP._250, b250);
						System.out.println("a _250: " + b250.val);
						break EHLO;
					}
					case _250_:
					{
						Buff<demo.smtp.message.server._250_> b250_ = new Buff<>();
						s18 = s18.receive(SMTP._250_, b250_).branch();
						System.out.println("a _250_: " + b250_.val);
						break;
					}
				}
			}
			
			s8.send(SMTP.S, new Quit());*/
		}
		//catch (IOException | ScribbleRuntimeException | ClassNotFoundException | ExecutionException | InterruptedException e)
		catch (Exception e)
		{
			//throw new ScribbleRuntimeException(e.getMessage());
			e.printStackTrace();
		}
	}
}
