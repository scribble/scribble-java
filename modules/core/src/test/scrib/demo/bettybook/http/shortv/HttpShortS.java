package demo.bettybook.http.shortv;

import static demo.bettybook.http.shortv.HttpShort.Http.Http.C;
import static demo.bettybook.http.shortv.HttpShort.Http.Http.Req;
import static demo.bettybook.http.shortv.HttpShort.Http.Http.S;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.MPSTEndpoint;

import demo.bettybook.http.shortv.HttpShort.Http.Http;
import demo.bettybook.http.shortv.HttpShort.Http.channels.S.Http_S_1;
import demo.bettybook.http.shortv.HttpShort.Http.channels.S.Http_S_2;
import demo.bettybook.http.shortv.HttpShort.Http.roles.S;
import demo.bettybook.http.shortv.message.HttpShortMessageFormatter;
import demo.bettybook.http.shortv.message.client.Req;
import demo.bettybook.http.shortv.message.server.Resp;

public class HttpShortS {

	public static void main(String[] args) throws IOException {
		try (ScribServerSocket ss = new SocketChannelServer(8080)) {
			while (true)	{
				Http http = new Http();
				try (MPSTEndpoint<Http, S> server = new MPSTEndpoint<>(http, S, new HttpShortMessageFormatter())) {
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
		Buf<Req> buf = new Buf<>();

		Http_S_2 s2 = s1.receive(C, Req, buf);
		System.out.println("Request:\n" + buf.val);
		s2.send(C, new Resp("1.1", "<html><body>Hello, World!</body></html>"));
	}
}
