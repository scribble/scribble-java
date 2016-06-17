package demo.http.longvers;

import org.scribble.net.Buf;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.SessionEndpoint;

import demo.http.longvers.HttpLong.Http.Http;
import demo.http.longvers.HttpLong.Http.channels.S.Http_S_1;
import demo.http.longvers.HttpLong.Http.channels.S.Http_S_2;
import demo.http.longvers.HttpLong.Http.channels.S.Http_S_2_Cases;
import demo.http.longvers.HttpLong.Http.roles.S;
import demo.http.longvers.message.Body;
import demo.http.longvers.message.HttpLongMessageFormatter;
import demo.http.longvers.message.client.Accept;
import demo.http.longvers.message.client.AcceptEncoding;
import demo.http.longvers.message.client.AcceptLanguage;
import demo.http.longvers.message.client.Connection;
import demo.http.longvers.message.client.DoNotTrack;
import demo.http.longvers.message.client.Host;
import demo.http.longvers.message.client.RequestLine;
import demo.http.longvers.message.client.UserAgent;
import demo.http.longvers.message.server.ContentLength;
import demo.http.longvers.message.server.HttpVersion;
import demo.http.longvers.message.server._200;

public class HttpServer
{
	public HttpServer()
	{

	}

	public static void main(String[] args) throws Exception
	{
		Buf<RequestLine> b_reql = new Buf<>();
		Buf<Host> b_host = new Buf<>();
		Buf<UserAgent> b_usera = new Buf<>();
		Buf<Accept> b_acc = new Buf<>();
		Buf<AcceptLanguage> b_accl = new Buf<>();
		Buf<AcceptEncoding> b_acce = new Buf<>();
		Buf<DoNotTrack> b_dnt = new Buf<>();
		Buf<Connection> b_conn = new Buf<>();
		Buf<Body> b_body = new Buf<>();
		
		try (ScribServerSocket ss = new SocketChannelServer(8080))
		{
			while (true)	
			{
				Http http = new Http();
				try (SessionEndpoint<Http, S> se = new SessionEndpoint<>(http, Http.S, new HttpLongMessageFormatter()))
				{
					se.accept(ss, Http.C);
				
					Http_S_1 s1 = new Http_S_1(se);
					Http_S_2 s2 = s1.receive(Http.C, Http.REQUESTL, b_reql);
					
					System.out.println("Requested: " + b_reql.val);
					
					X: while (true)
					{
						Http_S_2_Cases s2cases = s2.branch(Http.C);
						switch (s2cases.op)
						{
							case ACCEPT:
							{
								s2 = s2cases.receive(Http.ACCEPT, b_acc);
								break;
							}
							case ACCEPTE:
							{
								s2 = s2cases.receive(Http.ACCEPTE, b_acce);
								break;
							}
							case ACCEPTL:
							{
								s2 = s2cases.receive(Http.ACCEPTL, b_accl);
								break;
							}
							case BODY:
							{
								//String body = "";
								String body = "<html><body>Hello</body></html>";
								s2cases.receive(Http.BODY, b_body)
									.send(Http.C, new HttpVersion("1.1"))
									.send(Http.C, new _200("OK"))
									.send(Http.C, new ContentLength(body.length()))
									.send(Http.C, new Body(body));
								break X;
							}
							case CONNECTION:
							{
								s2 = s2cases.receive(Http.CONNECTION, b_conn);
								break;
							}
							case DNT:
							{
								s2 = s2cases.receive(Http.DNT, b_dnt);
								break;
							}
							case HOST:
							{
								s2 = s2cases.receive(Http.HOST, b_host);
								break;
							}
							case USERA:
							{
								s2 = s2cases.receive(Http.USERA, b_usera);
								break;
							}
						}
					}
				}
			}
		}
	}
}
