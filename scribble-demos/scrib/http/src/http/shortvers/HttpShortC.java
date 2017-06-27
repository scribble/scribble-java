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
