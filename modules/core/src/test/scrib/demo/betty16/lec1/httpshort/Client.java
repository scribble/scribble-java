package demo.betty16.lec1.httpshort;

import static demo.betty16.lec1.httpshort.HttpShort.Http.Http.C;
import static demo.betty16.lec1.httpshort.HttpShort.Http.Http.Response;
import static demo.betty16.lec1.httpshort.HttpShort.Http.Http.S;

import org.scribble.net.Buf;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.betty16.lec1.httpshort.HttpShort.Http.Http;
import demo.betty16.lec1.httpshort.HttpShort.Http.channels.C.Http_C_1;
import demo.betty16.lec1.httpshort.HttpShort.Http.roles.C;
import demo.betty16.lec1.httpshort.message.HttpShortMessageFormatter;
import demo.betty16.lec1.httpshort.message.client.Request;
import demo.betty16.lec1.httpshort.message.server.Response;

public class Client {

	public static void main(String[] args) throws Exception {
		Http http = new Http();
		try (SessionEndpoint<Http, C> client = new SessionEndpoint<>(http, C, new HttpShortMessageFormatter())) {
			String host = "www.doc.ic.ac.uk"; int port = 80;
			//String host = "summerschool2016.behavioural-types.eu"; int port = 80;
			//String host = "localhost"; int port = 8080;

			client.connect(S, SocketChannelEndpoint::new, host, port);
			new Client().run(client, host);
		}
	}

	private void run(SessionEndpoint<Http, C> client, String host) throws Exception {
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
