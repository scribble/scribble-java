/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package http.shortvers;

import static http.shortvers.HttpShort.Http.Http.C;
import static http.shortvers.HttpShort.Http.Http.REQUEST;
import static http.shortvers.HttpShort.Http.Http.S;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.runtime.net.Buf;
import org.scribble.runtime.net.scribsock.ScribServerSocket;
import org.scribble.runtime.net.scribsock.SocketChannelServer;
import org.scribble.runtime.net.session.MPSTEndpoint;

import http.shortvers.HttpShort.Http.Http;
import http.shortvers.HttpShort.Http.channels.S.Http_S_1;
import http.shortvers.HttpShort.Http.channels.S.Http_S_2;
import http.shortvers.HttpShort.Http.roles.S;
import http.shortvers.message.HttpShortMessageFormatter;
import http.shortvers.message.client.Request;
import http.shortvers.message.server.Response;

// FIXME: (Firefox) Cannot parse header field: Cookie: __utma=111872281.1372348290.1498163262.1498163262.1498163262.1; __utmz=111872281.1498163262.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none) 
public class HttpShortS
{
	public HttpShortS()
	{

	}

	public static void main(String[] args) throws IOException
	{
		try (ScribServerSocket ss = new SocketChannelServer(8080))
		{
			while (true)	
			{
				Http http = new Http();
				try (MPSTEndpoint<Http, S> se = new MPSTEndpoint<>(http, S, new HttpShortMessageFormatter()))
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
