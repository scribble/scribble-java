package bettybook.http.shortv;

import static bettybook.http.shortv.HttpShort.Http.Http.C;
import static bettybook.http.shortv.HttpShort.Http.Http.Resp;
import static bettybook.http.shortv.HttpShort.Http.Http.S;

import org.scribble.net.Buf;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import bettybook.http.shortv.HttpShort.Http.Http;
import bettybook.http.shortv.HttpShort.Http.channels.C.Http_C_1;
import bettybook.http.shortv.HttpShort.Http.roles.C;
import bettybook.http.shortv.message.HttpShortMessageFormatter;
import bettybook.http.shortv.message.client.Req;
import bettybook.http.shortv.message.server.Resp;

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
		Buf<Resp> buf = new Buf<>();
		Http_C_1 c = new Http_C_1(client);


		c.send(S, new Req("/~rhu/", "1.1", host))
		 .receive(S, Resp, buf);
		

		System.out.println("Response:\n" + buf.val);
	}
}

