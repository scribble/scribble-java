package demo.http;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.http.message.Body;
import demo.http.message.HttpMessageFormatter;
import demo.http.message.client.Accept;
import demo.http.message.client.AcceptEncoding;
import demo.http.message.client.AcceptLanguage;
import demo.http.message.client.Connection;
import demo.http.message.client.DoNotTrack;
import demo.http.message.client.Host;
import demo.http.message.client.RequestLine;
import demo.http.message.client.UserAgent;
import demo.http.message.server.ContentLength;
import demo.http.message.server.HttpVersion;
import demo.http.message.server._200;

public class HttpServer
{
	public HttpServer()
	{

	}

	public static void main(String[] args) throws ScribbleRuntimeException, IOException
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
				SessionEndpoint se = http.project(Http.C, new HttpMessageFormatter(), ss);
				Http_S_0 init = new Http_S_0(se);
				init.accept(Http.C);

				try (Http_S_0 s0 = init)
				{
					Http_S_1 s1 = s0.init();
					Http_S_2 s2 = s1.receive(Http.REQUESTL, b_reql);
					
					System.out.println("Requested: " + b_reql.val);
					
					X: while (true)
					{
						Http_S_6 s6 = s2.branch();
						switch (s6.op)
						{
							case ACCEPT:
							{
								s2 = s6.receive(Http.ACCEPT, b_acc);
								break;
							}
							case ACCEPTE:
							{
								s2 = s6.receive(Http.ACCEPTE, b_acce);
								break;
							}
							case ACCEPTL:
							{
								s2 = s6.receive(Http.ACCEPTL, b_accl);
								break;
							}
							case BODY:
							{
								//String body = "";
								String body = "<html><body>Hello</body></html>";
								s6.receive(Http.BODY, b_body)
									.send(Http.C, new HttpVersion("1.1"))
									.send(Http.C, new _200("OK"))
									.send(Http.C, new ContentLength(body.length()))
									.send(Http.C, new Body(body));
								break X;
							}
							case CONNECTION:
							{
								s2 = s6.receive(Http.CONNECTION, b_conn);
								break;
							}
							case DNT:
							{
								s2 = s6.receive(Http.DNT, b_dnt);
								break;
							}
							case HOST:
							{
								s2 = s6.receive(Http.HOST, b_host);
								break;
							}
							case USERA:
							{
								s2 = s6.receive(Http.USERA, b_usera);
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
	}
}
