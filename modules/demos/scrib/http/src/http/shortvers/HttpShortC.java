package http.shortvers;

import org.scribble.net.Buf;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import http.shortvers.HttpShort.Http.Http;
import http.shortvers.HttpShort.Http.channels.C.Http_C_1;
import http.shortvers.HttpShort.Http.roles.C;
import http.shortvers.message.HttpShortMessageFormatter;
import http.shortvers.message.client.Request;
import http.shortvers.message.server.Response;

import static http.shortvers.HttpShort.Http.Http.*;

public class HttpShortC
{
	public HttpShortC() throws Exception
	{
		run();
	}

	public static void main(String[] args) throws Exception
	{
		new HttpShortC();
	}

	public void run() throws Exception
	{
		Http http = new Http();
		try (MPSTEndpoint<Http, C> client = new MPSTEndpoint<>(http, C, new HttpShortMessageFormatter()))
		{
			String host = "www.doc.ic.ac.uk"; int port = 80;
			//String host = "localhost"; int port = 8080;
		
			client.connect(S, SocketChannelEndpoint::new, host, port);
			
			Buf<Response> buf = new Buf<>();
			new Http_C_1(client)
				.send(S, new Request("/~rhu/", "1.1", host))
				.receive(S, RESPONSE, buf);
			
			System.out.println("Response:\n" + buf.val);
		}
	}
}
