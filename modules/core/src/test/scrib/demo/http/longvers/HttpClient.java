package demo.http.longvers;

import static demo.http.longvers.HttpLong.Http.Http.ACCEPTR;
import static demo.http.longvers.HttpLong.Http.Http.BODY;
import static demo.http.longvers.HttpLong.Http.Http.CONTENTL;
import static demo.http.longvers.HttpLong.Http.Http.CONTENTT;
import static demo.http.longvers.HttpLong.Http.Http.DATE;
import static demo.http.longvers.HttpLong.Http.Http.ETAG;
import static demo.http.longvers.HttpLong.Http.Http.HTTPV;
import static demo.http.longvers.HttpLong.Http.Http.LASTM;
import static demo.http.longvers.HttpLong.Http.Http.S;
import static demo.http.longvers.HttpLong.Http.Http.SERVER;
import static demo.http.longvers.HttpLong.Http.Http.STRICTTS;
import static demo.http.longvers.HttpLong.Http.Http.VARY;
import static demo.http.longvers.HttpLong.Http.Http.VIA;
import static demo.http.longvers.HttpLong.Http.Http._200;
import static demo.http.longvers.HttpLong.Http.Http._404;

import org.scribble.net.Buf;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;
import org.scribble.util.Caller;

import demo.http.longvers.HttpLong.Http.Http;
import demo.http.longvers.HttpLong.Http.channels.C.Http_C_1;
import demo.http.longvers.HttpLong.Http.channels.C.Http_C_4_Cases;
import demo.http.longvers.HttpLong.Http.channels.C.Http_C_5;
import demo.http.longvers.HttpLong.Http.channels.C.Http_C_5_Cases;
import demo.http.longvers.HttpLong.Http.channels.C.ioifaces.Branch_C_S_200__S_404.Branch_C_S_200__S_404_Enum;
import demo.http.longvers.HttpLong.Http.roles.C;
import demo.http.longvers.message.Body;
import demo.http.longvers.message.HttpLongMessageFormatter;
import demo.http.longvers.message.client.Host;
import demo.http.longvers.message.client.RequestLine;
import demo.http.longvers.message.server.ContentLength;
import demo.http.longvers.message.server.ContentType;
import demo.http.longvers.message.server.HttpVersion;
import demo.http.longvers.message.server.Server;

public class HttpClient
{
	public HttpClient() throws Exception
	{
		run();
	}

	public static void main(String[] args) throws Exception
	{
		new HttpClient();
	}

	public void run() throws Exception
	{
		Buf<HttpVersion> b_vers = new Buf<>();
		Buf<ContentLength> b_clen = new Buf<>();
		Buf<ContentType> b_ctype = new Buf<>();
		Buf<Body> b_body = new Buf<>();
		Buf<Server> b_serv = new Buf<>();
		
		Http http = new Http();
		try (SessionEndpoint<Http, C> se = new SessionEndpoint<>(http, Http.C, new HttpLongMessageFormatter()))
		{
			String host = "www.doc.ic.ac.uk"; int port = 80;
			//String host = "localhost"; int port = 8080;
		
			se.connect(Http.S, SocketChannelEndpoint::new, host, port);
			
			Http_C_1 s1 = new Http_C_1(se);

			Http_C_4_Cases s4 =
					s1.send(S, new RequestLine("/~rhu/", "1.1"))
					  .send(S, new Host(host))
					  .send(S, new Body(""))
					  .receive(S, HTTPV, b_vers)
					  .branch(S);
			Http_C_5 s5 =  // S?{ 200: ..., 404: ..., ... }
					  (s4.op == Branch_C_S_200__S_404_Enum._200) ? s4.receive(_200)
					: (s4.op == Branch_C_S_200__S_404_Enum._404) ? s4.receive(_404)
					: new Caller().call(() -> { throw new RuntimeException("Unknown status code: " + s4.op); });

			Y: while (true)
			{
				Http_C_5_Cases cases = s5.branch(S);
				switch (cases.op)
				{
					case ACCEPTR:
					{
						s5 = cases.receive(ACCEPTR);
						break;
					}
					case BODY:
					{
						cases.receive(BODY, b_body);
						System.out.println(b_body.val.getBody());
						break Y;
					}
					case CONTENTL:
					{
						s5 = cases.receive(CONTENTL, b_clen);
						break;
					}
					case CONTENTT:
					{
						s5 = cases.receive(CONTENTT, b_ctype);
						break;
					}
					case DATE:
					{
						s5 = cases.receive(DATE);
						break;
					}
					case ETAG:
					{
						s5 = cases.receive(ETAG);
						break;
					}
					case LASTM:
					{
						s5 = cases.receive(LASTM);
						break;
					}
					case SERVER:
					{
						s5 = cases.receive(SERVER, b_serv);
						break;
					}
					case STRICTTS:
					{
						s5 = cases.receive(STRICTTS);
						break;
					}
					case VARY:
					{
						s5 = cases.receive(VARY);
						break;
					}
					case VIA:
					{
						s5  = cases.receive(VIA);
						break;
					}
				}
			}
		}
	}
}
