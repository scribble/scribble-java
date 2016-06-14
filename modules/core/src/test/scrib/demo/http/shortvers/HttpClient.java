package demo.http.shortvers;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.http.shortvers.HttpShort.Http.Http;
import demo.http.shortvers.HttpShort.Http.channels.C.Http_C_1;
import demo.http.shortvers.HttpShort.Http.roles.C;
import demo.http.shortvers.message.HttpMessageFormatter;
import demo.http.shortvers.message.client.Request;
import demo.http.shortvers.message.server.Response;

public class HttpClient
{
	public HttpClient() throws ScribbleRuntimeException, IOException
	{
		run();
	}

	public static void main(String[] args) throws Exception
	{
		new HttpClient();
	}

	public void run() throws ScribbleRuntimeException, IOException
	{
		Http http = new Http();
		try (SessionEndpoint<Http, C> se = new SessionEndpoint<>(http, Http.C, new HttpMessageFormatter()))
		{
			String host = "www.doc.ic.ac.uk"; int port = 80;
			//String host = "localhost"; int port = 8080;
		
			se.connect(Http.S, SocketChannelEndpoint::new, host, port);
			
			Http_C_1 s1 = new Http_C_1(se);
			
			Buf<Response> buf = new Buf<>();
			s1.send(Http.S, new Request("/~rhu/", "1.1", host)).receive(Http.S, Http.RESPONSE, buf);
			
			System.out.println("Response:\n" + buf.val);
		}
		catch (IOException | ClassNotFoundException | ScribbleRuntimeException e)
		{
			e.printStackTrace();
		}
	}
}
