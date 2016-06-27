package demo.betty16.lec1.httpshort;

import static demo.betty16.lec1.httpshort.HttpShort.Http.Http.C;
import static demo.betty16.lec1.httpshort.HttpShort.Http.Http.Request;
import static demo.betty16.lec1.httpshort.HttpShort.Http.Http.S;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.SessionEndpoint;

import demo.betty16.lec1.httpshort.HttpShort.Http.Http;
import demo.betty16.lec1.httpshort.HttpShort.Http.channels.S.Http_S_1;
import demo.betty16.lec1.httpshort.HttpShort.Http.channels.S.Http_S_2;
import demo.betty16.lec1.httpshort.HttpShort.Http.roles.S;
import demo.betty16.lec1.httpshort.message.HttpShortMessageFormatter;
import demo.betty16.lec1.httpshort.message.client.Request;
import demo.betty16.lec1.httpshort.message.server.Response;

public class Server {

	public static void main(String[] args) throws IOException {
		try (ScribServerSocket ss = new SocketChannelServer(8080)) {
			while (true)	{
				Http http = new Http();
				try (SessionEndpoint<Http, S> server = new SessionEndpoint<>(http, S, new HttpShortMessageFormatter())) {
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
		Buf<Request> buf = new Buf<>();

		Http_S_2 s2 = s1.receive(C, Request, buf);
		System.out.println("Request:\n" + buf.val);
		s2.send(C, new Response("1.1", "<html><body>Hello, World!</body></html>"));
	}
}
