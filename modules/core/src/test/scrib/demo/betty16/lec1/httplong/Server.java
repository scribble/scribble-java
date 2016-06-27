package demo.betty16.lec1.httplong;

import static demo.betty16.lec1.httplong.HttpLong.Http.Http.ACCEPT;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.ACCEPTE;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.ACCEPTL;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.BODY;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.C;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.CONNECTION;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.DNT;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.HOST;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.REQUESTL;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.S;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.USERA;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
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
import demo.betty16.lec1.httplong.message.server.ContentLength;
import demo.betty16.lec1.httplong.message.server.HttpVersion;
import demo.betty16.lec1.httplong.message.server._200;

public class Server
{
	public static void main(String[] args) throws Exception
	{
		try (ScribServerSocket ss = new SocketChannelServer(8080)) {
			while (true)	{
				Http http = new Http();
				try (SessionEndpoint<Http, S> server = new SessionEndpoint<>(http, S, new HttpLongMessageFormatter())) {
					server.accept(ss, C);
				
					run(new Http_S_1(server));
				}
				catch (IOException | ClassNotFoundException | ScribbleRuntimeException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void run(Http_S_1 s1) throws ClassNotFoundException, ScribbleRuntimeException, IOException {
		Buf<Object> buf = new Buf<>();
		
		Http_S_2 s2 = s1.receive(C, REQUESTL, buf);
		System.out.println("Requested: " + buf.val);
		
		Y: while (true) {
			Http_S_2_Cases cases = s2.branch(C);
			switch (cases.op) {
				case ACCEPT:  s2 = cases.receive(ACCEPT, buf); break;
				case ACCEPTE: s2 = cases.receive(ACCEPTE, buf); break;
				case ACCEPTL: s2 = cases.receive(ACCEPTL, buf); break;
				case BODY:
				{
					String body = "<html><body>Hello, World!</body></html>";
					cases.receive(BODY, buf)
						.send(C, new HttpVersion("1.1"))
						.send(C, new _200("OK"))
						.send(C, new ContentLength(body.length()))
						.send(C, new Body(body));
					break Y;
				}
				case CONNECTION: s2 = cases.receive(CONNECTION, buf); break;
				case DNT:        s2 = cases.receive(DNT, buf); break;
				case HOST:       s2 = cases.receive(HOST, buf); break;
				case USERA:      s2 = cases.receive(USERA, buf); break;
			}
		}
	}
}
