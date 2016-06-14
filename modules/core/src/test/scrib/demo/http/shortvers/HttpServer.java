package demo.http.shortvers;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.SessionEndpoint;

import demo.http.shortvers.HttpShort.Http.Http;
import demo.http.shortvers.HttpShort.Http.channels.S.Http_S_1;
import demo.http.shortvers.HttpShort.Http.channels.S.Http_S_2;
import demo.http.shortvers.HttpShort.Http.roles.S;
import demo.http.shortvers.message.HttpShortMessageFormatter;
import demo.http.shortvers.message.client.Request;
import demo.http.shortvers.message.server.Response;

import static demo.http.shortvers.HttpShort.Http.Http.*;

public class HttpServer
{
	public HttpServer()
	{

	}

	public static void main(String[] args) throws IOException
	{
		try (ScribServerSocket ss = new SocketChannelServer(8080))
		{
			while (true)	
			{
				Http http = new Http();
				try (SessionEndpoint<Http, S> se = new SessionEndpoint<>(http, S, new HttpShortMessageFormatter()))
				{
					se.accept(ss, C);
				
					Buf<Request> buf = new Buf<>();
					Http_S_2 s2 = new Http_S_1(se).receive(C, REQUEST, buf);

					System.out.println("Request:\n" + buf.val);

					s2.send(C, new Response("1.1", "<html><body>Hello</body></html>"));
				}
				catch (IOException | ClassNotFoundException | ScribbleRuntimeException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
}
