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
package betty16.lec1.httpshort;

import static betty16.lec1.httpshort.HttpShort.Http.Http.C;
import static betty16.lec1.httpshort.HttpShort.Http.Http.Request;
import static betty16.lec1.httpshort.HttpShort.Http.Http.S;

import java.io.IOException;

import org.scribble.main.ScribRuntimeException;
import org.scribble.runtime.net.ScribServerSocket;
import org.scribble.runtime.net.SocketChannelServer;
import org.scribble.runtime.session.MPSTEndpoint;
import org.scribble.runtime.util.Buf;

import betty16.lec1.httpshort.HttpShort.Http.Http;
import betty16.lec1.httpshort.HttpShort.Http.roles.S;
import betty16.lec1.httpshort.HttpShort.Http.statechans.S.Http_S_1;
import betty16.lec1.httpshort.HttpShort.Http.statechans.S.Http_S_2;
import betty16.lec1.httpshort.message.HttpShortMessageFormatter;
import betty16.lec1.httpshort.message.client.Request;
import betty16.lec1.httpshort.message.server.Response;

public class HttpShortS {

	public static void main(String[] args) throws IOException {
		try (ScribServerSocket ss = new SocketChannelServer(8080)) {
			while (true)	{
				Http http = new Http();
				try (MPSTEndpoint<Http, S> server = new MPSTEndpoint<>(http, S,
						new HttpShortMessageFormatter())) {
					server.accept(ss, C);
				
					run(new Http_S_1(server));
				}
				catch (IOException | ClassNotFoundException | ScribRuntimeException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void run(Http_S_1 s1)
			throws ClassNotFoundException, ScribRuntimeException, IOException {
		Buf<Request> buf = new Buf<>();

		Http_S_2 s2 = s1.receive(C, Request, buf);
		System.out.println("Request:\n" + buf.val);
		s2.send(C, new Response("1.1", "<html><body>Hello, World!</body></html>"));
	}
}
