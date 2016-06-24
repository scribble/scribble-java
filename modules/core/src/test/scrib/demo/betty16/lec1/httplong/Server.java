package demo.betty16.lec1.httplong;

import org.scribble.net.Buf;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.SessionEndpoint;

import demo.betty16.lec1.httplong.HttpLong.Http.Http;
import demo.betty16.lec1.httplong.HttpLong.Http.channels.S.Http_S_1;
import demo.betty16.lec1.httplong.HttpLong.Http.channels.S.Http_S_2;
import demo.betty16.lec1.httplong.HttpLong.Http.channels.S.Http_S_2_Cases;
import demo.betty16.lec1.httplong.HttpLong.Http.roles.S;
import demo.betty16.lec1.httplong.message.Body;
import demo.betty16.lec1.httplong.message.HttpLongMessageFormatter;
import demo.betty16.lec1.httplong.message.client.Accept;
import demo.betty16.lec1.httplong.message.client.AcceptEncoding;
import demo.betty16.lec1.httplong.message.client.AcceptLanguage;
import demo.betty16.lec1.httplong.message.client.Connection;
import demo.betty16.lec1.httplong.message.client.DoNotTrack;
import demo.betty16.lec1.httplong.message.client.Host;
import demo.betty16.lec1.httplong.message.client.RequestLine;
import demo.betty16.lec1.httplong.message.client.UserAgent;
import demo.betty16.lec1.httplong.message.server.ContentLength;
import demo.betty16.lec1.httplong.message.server.HttpVersion;
import demo.betty16.lec1.httplong.message.server._200;

public class Server
{
	public Server()
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
