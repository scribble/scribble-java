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
import static betty16.lec1.httpshort.HttpShort.Http.Http.Response;
import static betty16.lec1.httpshort.HttpShort.Http.Http.S;

import org.scribble.runtime.net.SocketChannelEndpoint;
import org.scribble.runtime.session.MPSTEndpoint;
import org.scribble.runtime.util.Buf;

import betty16.lec1.httpshort.HttpShort.Http.Http;
import betty16.lec1.httpshort.HttpShort.Http.roles.C;
import betty16.lec1.httpshort.HttpShort.Http.statechans.C.EndSocket;
import betty16.lec1.httpshort.HttpShort.Http.statechans.C.Http_C_1;
import betty16.lec1.httpshort.message.HttpShortMessageFormatter;
import betty16.lec1.httpshort.message.client.Request;
import betty16.lec1.httpshort.message.server.Response;

public class HttpShortC {

	public static void main(String[] args) throws Exception {
		Http http = new Http();
		try (MPSTEndpoint<Http, C> client = new MPSTEndpoint<>(http, C,
				new HttpShortMessageFormatter())) {
			//String host = "www.doc.ic.ac.uk";  int port = 80;  String file = "/~rhu/";
			String host = "example.com";  int port = 80;  String file = "/";
			//String host = "localhost";  int port = 8080;  String file = "/";

			client.request(S, SocketChannelEndpoint::new, host, port);
			new HttpShortC().run(new Http_C_1(client), host, file);
		}
	}

	private EndSocket run(Http_C_1 c1, String host, String file) throws Exception {
		Buf<Response> buf = new Buf<>();

		EndSocket end =
				c1.send(S, new Request(file, "1.1", host))
				 .receive(S, Response, buf);

		System.out.println("Response:\n" + buf.val);
		
		return end;
	}
}
