package demo.http.shortvers;

import org.scribble.net.Buf;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.http.shortvers.HttpShort.Http.Http;
import demo.http.shortvers.HttpShort.Http.channels.C.Http_C_1;
import demo.http.shortvers.HttpShort.Http.roles.C;
import demo.http.shortvers.message.HttpShortMessageFormatter;
import demo.http.shortvers.message.client.Request;
import demo.http.shortvers.message.server.Response;

import static demo.http.shortvers.HttpShort.Http.Http.*;

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
		Http http = new Http();
		try (SessionEndpoint<Http, C> client = new SessionEndpoint<>(http, C, new HttpShortMessageFormatter()))
		{
			String host = "www.doc.ic.ac.uk"; int port = 80;
			//String host = "localhost"; int port = 8080;
		
			client.connect(S, SocketChannelEndpoint::new, host, port);
			
			Buf<Response> buf = new Buf<>();
			new Http_C_1(client)
				.send(S, new Request("/~rhu/", "1.1", host))
				//.send(S, new Response("1.1", "..body.."))
				//.send(S, new Request("/~rhu/", "1.1", host))
				.receive(S, RESPONSE, buf);
			
			System.out.println("Response:\n" + buf.val);
		}
	}
}
