package demo.http;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buff;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.util.Caller;

import demo.http.Http_C_4.Http_C_4Enum;
import demo.http.message.Body;
import demo.http.message.HttpMessageFormatter;
import demo.http.message.client.Host;
import demo.http.message.client.RequestLine;
import demo.http.message.server.ContentLength;
import demo.http.message.server.ContentType;
import demo.http.message.server.HttpVersion;
import demo.http.message.server.Server;

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
		Buff<HttpVersion> b_vers = new Buff<>();
		Buff<ContentLength> b_clen = new Buff<>();
		Buff<ContentType> b_ctype = new Buff<>();
		Buff<Body> b_body = new Buff<>();
		Buff<Server> b_serv = new Buff<>();
		
		Http http = new Http();
		SessionEndpoint se = http.project(Http.C, new HttpMessageFormatter());
		
		String host = "www.doc.ic.ac.uk";
		int port = 80;
		//String host = "localhost";
		//int port = 8080;
		
		try (Http_C_0 init = new Http_C_0(se))
		{
			init.connect(Http.S, host, port);
			Http_C_1 s1 = init.init();
			Http_C_6 s6 =
					s1.send(Http.S, new RequestLine("/~rhu/", "1.1"))
					  .send(Http.S, new Host(host))
					  .send(Http.S, new Body(""))
					  .receive(Http.HTTPV, b_vers)
					  .branch();
			Http_C_5 s5 = 
					  (s6.op == Http_C_4Enum._200) ? s6.receive(Http._200)
					: (s6.op == Http_C_4Enum._404) ? s6.receive(Http._404)
					: new Caller().call(() -> { throw new RuntimeException("Unknown status code: " + s6.op); });

			Y: while (true)
			{
				Http_C_7 s7 = s5.branch();
				switch (s7.op)
				{
					case ACCEPTR:
					{
						s5 = s7.receive(Http.ACCEPTR);
						break;
					}
					case BODY:
					{
						s7.receive(Http.BODY, b_body);
						System.out.println(b_body.val.getBody());
						break Y;
					}
					case CONTENTL:
					{
						s5 = s7.receive(Http.CONTENTL, b_clen);
						break;
					}
					case CONTENTT:
					{
						s5 = s7.receive(Http.CONTENTT, b_ctype);
						break;
					}
					case DATE:
					{
						s5 = s7.receive(Http.DATE);
						break;
					}
					case ETAG:
					{
						s5 = s7.receive(Http.ETAG);
						break;
					}
					case LASTM:
					{
						s5 = s7.receive(Http.LASTM);
						break;
					}
					case SERVER:
					{
						s5 = s7.receive(Http.SERVER, b_serv);
						break;
					}
					case VARY:
					{
						s5 = s7.receive(Http.VARY);
						break;
					}
					case VIA:
					{
						s5  = s7.receive(Http.VIA);
						break;
					}
				}
			}
		}
		catch (IOException | ClassNotFoundException | ScribbleRuntimeException | ExecutionException | InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
