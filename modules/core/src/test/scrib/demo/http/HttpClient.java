package demo.http;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;
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

public class HttpClient
{
	public HttpClient() throws ScribbleRuntimeException, IOException
	{
		run();
	}

	public static void main(String[] args) throws Exception
	{
		new HttpClient();
	}

	public void run() throws ScribbleRuntimeException, IOException
	{
		Buf<HttpVersion> b_vers = new Buf<>();
		Buf<ContentLength> b_clen = new Buf<>();
		Buf<ContentType> b_ctype = new Buf<>();
		Buf<Body> b_body = new Buf<>();
		Buf<Server> b_serv = new Buf<>();
		
		Http http = new Http();
		try (SessionEndpoint<Http, C> se = new SessionEndpoint<>(http, Http.C, new HttpMessageFormatter()))
		{
			String host = "www.doc.ic.ac.uk";
			int port = 80;
			//String host = "localhost";
			//int port = 8080;
		
			se.connect(SocketChannelEndpoint::new, Http.S, host, port);
			
			Http_C_1 s1 = new Http_C_1(se);

			Http_C_4_Cases s4 =
					s1.send(Http.S, new RequestLine("/~rhu/", "1.1"))
					  .send(Http.S, new Host(host))
					  .send(Http.S, new Body(""))
					  .receive(Http.S, Http.HTTPV, b_vers)
					  .branch(Http.S);
			Http_C_5 s5 = 
					  (s4.op == Http_C_4Enum._200) ? s4.receive(Http._200)
					: (s4.op == Http_C_4Enum._404) ? s4.receive(Http._404)
					: new Caller().call(() -> { throw new RuntimeException("Unknown status code: " + s4.op); });

			Y: while (true)
			{
				Http_C_5_Cases cases = s5.branch(Http.S);
				switch (cases.op)
				{
					case ACCEPTR:
					{
						s5 = cases.receive(Http.ACCEPTR);
						break;
					}
					case BODY:
					{
						cases.receive(Http.BODY, b_body);
						System.out.println(b_body.val.getBody());
						break Y;
					}
					case CONTENTL:
					{
						s5 = cases.receive(Http.CONTENTL, b_clen);
						break;
					}
					case CONTENTT:
					{
						s5 = cases.receive(Http.CONTENTT, b_ctype);
						break;
					}
					case DATE:
					{
						s5 = cases.receive(Http.DATE);
						break;
					}
					case ETAG:
					{
						s5 = cases.receive(Http.ETAG);
						break;
					}
					case LASTM:
					{
						s5 = cases.receive(Http.LASTM);
						break;
					}
					case SERVER:
					{
						s5 = cases.receive(Http.SERVER, b_serv);
						break;
					}
					case VARY:
					{
						s5 = cases.receive(Http.VARY);
						break;
					}
					case VIA:
					{
						s5  = cases.receive(Http.VIA);
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
