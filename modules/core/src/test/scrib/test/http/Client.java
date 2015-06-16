package test.http;

import java.io.IOException;
import java.util.concurrent.Callable;

import org.scribble2.net.Buff;
import org.scribble2.net.session.SessionEndpoint;
import org.scribble2.util.ScribbleRuntimeException;

import test.http.Http_C_4.Http_C_4Enum;
import test.http.message.Body;
import test.http.message.HttpMessageFormatter;
import test.http.message.client.Host;
import test.http.message.client.RequestLine;
import test.http.message.server.AcceptRanges;
import test.http.message.server.ContentLength;
import test.http.message.server.ContentType;
import test.http.message.server.Date;
import test.http.message.server.ETag;
import test.http.message.server.HttpVersion;
import test.http.message.server.LastModified;
import test.http.message.server.Server;
import test.http.message.server.Vary;
import test.http.message.server.Via;
import test.http.message.server._200;
import test.http.message.server._404;

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
		Buff<AcceptRanges> b_acc = new Buff<>();
		Buff<ContentLength> b_clen = new Buff<>();
		Buff<ContentType> b_ctype = new Buff<>();
		Buff<Body> b_body = new Buff<>();
		Buff<Date> b_date = new Buff<>();
		Buff<ETag> b_etag = new Buff<>();
		Buff<LastModified> b_lastm = new Buff<>();
		Buff<Server> b_serv = new Buff<>();
		Buff<Vary> b_vary = new Buff<>();
		Buff<Via> b_via = new Buff<>();
		Buff<_200> b_200 = new Buff<>();
		Buff<_404> b_404 = new Buff<>();
		
		Caller c = new Caller();
		
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
			Http_C_7 s7 =
					s1.send(Http.S, new RequestLine("/~rhu/", "1.1"))
					  .send(Http.S, new Host(host))
					  .send(Http.S, new Body(""))
					  .receive(Http.HTTPV, b_vers)
					  .branch();
			Http_C_5 s5 = 
					  (s7.op == Http_C_4Enum._200) ? s7.receive(Http._200, b_200)
					: (s7.op == Http_C_4Enum._404) ? s7.receive(Http._404, b_404)
					: c.call(() -> { throw new RuntimeException("Unknown status code: " + s7.op); });

			Y: while (true)
			{
				Http_C_8 s8 = s5.branch();
				switch (s8.op)
				{
					case ACCEPTR:
					{
						s5 = s8.receive(Http.ACCEPTR, b_acc);
						break;
					}
					case BODY:
					{
						s8.receive(Http.BODY, b_body)
						  .end();
						System.out.println(b_body.val.getBody());
						break Y;
					}
					case CONTENTL:
					{
						s5 = s8.receive(Http.CONTENTL, b_clen);
						break;
					}
					case CONTENTT:
					{
						s5 = s8.receive(Http.CONTENTT, b_ctype);
						break;
					}
					case DATE:
					{
						s5 = s8.receive(Http.DATE, b_date);
						break;
					}
					case ETAG:
					{
						s5 = s8.receive(Http.ETAG, b_etag);
						break;
					}
					case LASTM:
					{
						s5 = s8.receive(Http.LASTM, b_lastm);
						break;
					}
					case SERVER:
					{
						s5 = s8.receive(Http.SERVER, b_serv);
						break;
					}
					case VARY:
					{
						s5 = s8.receive(Http.VARY, b_vary);
						break;
					}
					case VIA:
					{
						s5  = s8.receive(Http.VIA, b_via);
						break;
					}
				}
			}
		}
		catch (IOException | ClassNotFoundException | ScribbleRuntimeException e)
		{
			e.printStackTrace();
		}
	}
	
	class Caller
	{
		public <T> T call(Callable<T> c)
		{
			try
			{
				return c.call();
			}
			catch (Exception e)
			{
				throw new RuntimeException(e);
			}
		}
	}
}
