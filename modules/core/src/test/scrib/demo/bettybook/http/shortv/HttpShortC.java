package demo.bettybook.http.shortv;

import static demo.bettybook.http.shortv.HttpShort.Http.Http.C;
import static demo.bettybook.http.shortv.HttpShort.Http.Http.Response;
import static demo.bettybook.http.shortv.HttpShort.Http.Http.S;

import org.scribble.net.Buf;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.bettybook.http.shortv.HttpShort.Http.Http;
import demo.bettybook.http.shortv.HttpShort.Http.channels.C.Http_C_1;
import demo.bettybook.http.shortv.HttpShort.Http.roles.C;
import demo.bettybook.http.shortv.message.HttpShortMessageFormatter;
import demo.bettybook.http.shortv.message.client.Request;
import demo.bettybook.http.shortv.message.server.Response;

public class HttpShortC {

	public static void main(String[] args) throws Exception {
		Http http = new Http();
		try (MPSTEndpoint<Http, C> client = new MPSTEndpoint<>(http, C, new HttpShortMessageFormatter())) {
			String host = "www.doc.ic.ac.uk"; int port = 80;
			//String host = "summerschool2016.behavioural-types.eu"; int port = 80;
			//String host = "localhost"; int port = 8080;

			client.connect(S, SocketChannelEndpoint::new, host, port);
			new HttpShortC().run(client, host);
		}
	}

	private void run(MPSTEndpoint<Http, C> client, String host) throws Exception {
		Buf<Response> buf = new Buf<>();
		Http_C_1 c = new Http_C_1(client);


		c.send(S, new Request("/~rhu/", "1.1", host))
		 .receive(S, Response, buf);
		

		System.out.println("Response:\n" + buf.val);
	}



















	
	
	/*private void run(Http_C_1 c1, String host) throws Exception {
		Buf<Response> buf = new Buf<>();
		c1.send(S, new Request("/~rhu/", "1.1", host))
			//.send(S, new Response("1.1", "..body.."))
			//.send(S, new Request("/~rhu/", "1.1", host))
			.receive(S, RESPONSE, buf);
	}*/
}
