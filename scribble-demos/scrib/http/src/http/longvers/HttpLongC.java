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
package http.longvers;

import static http.longvers.HttpLong.Http.Http.ACCEPTR;
import static http.longvers.HttpLong.Http.Http.BODY;
import static http.longvers.HttpLong.Http.Http.C;
import static http.longvers.HttpLong.Http.Http.CONTENTL;
import static http.longvers.HttpLong.Http.Http.CONTENTT;
import static http.longvers.HttpLong.Http.Http.DATE;
import static http.longvers.HttpLong.Http.Http.ETAG;
import static http.longvers.HttpLong.Http.Http.HTTPV;
import static http.longvers.HttpLong.Http.Http.LASTM;
import static http.longvers.HttpLong.Http.Http.S;
import static http.longvers.HttpLong.Http.Http.SERVER;
import static http.longvers.HttpLong.Http.Http.STRICTTS;
import static http.longvers.HttpLong.Http.Http.VARY;
import static http.longvers.HttpLong.Http.Http.VIA;
import static http.longvers.HttpLong.Http.Http._200;
import static http.longvers.HttpLong.Http.Http._404;

import org.scribble.runtime.net.Buf;
import org.scribble.runtime.net.session.MPSTEndpoint;
import org.scribble.runtime.net.session.SocketChannelEndpoint;
import org.scribble.util.Caller;

import http.longvers.HttpLong.Http.Http;
import http.longvers.HttpLong.Http.channels.C.Http_C_1;
import http.longvers.HttpLong.Http.channels.C.Http_C_3;
import http.longvers.HttpLong.Http.channels.C.Http_C_4_Cases;
import http.longvers.HttpLong.Http.channels.C.Http_C_5;
import http.longvers.HttpLong.Http.channels.C.Http_C_5_Cases;
import http.longvers.HttpLong.Http.channels.C.ioifaces.Branch_C_S_200__S_404.Branch_C_S_200__S_404_Enum;
import http.longvers.HttpLong.Http.roles.C;
import http.longvers.message.Body;
import http.longvers.message.HttpLongMessageFormatter;
import http.longvers.message.client.Host;
import http.longvers.message.client.RequestLine;
import http.longvers.message.server.ContentLength;
import http.longvers.message.server.ContentType;
import http.longvers.message.server.HttpVersion;
import http.longvers.message.server.Server;

public class HttpLongC
{
	public HttpLongC() throws Exception
	{
		run();
	}

	public static void main(String[] args) throws Exception
	{
		new HttpLongC();
	}

	public void run() throws Exception
	{
		Http http = new Http();
		try (MPSTEndpoint<Http, C> client = new MPSTEndpoint<>(http, C, new HttpLongMessageFormatter()))
		{
			String host = "www.doc.ic.ac.uk"; int port = 80;
			//String host = "localhost"; int port = 8080;
		
			client.connect(S, SocketChannelEndpoint::new, host, port);

			doResponse(
					doRequest(new Http_C_1(client), host)
			);
		}
	}
	
	private Http_C_3 doRequest(Http_C_1 s1, String host) throws Exception
	{
		return s1.send(S, new RequestLine("/~rhu/", "1.1"))
			.send(S, new Host(host))
			.send(S, new Body(""));
	}

	private void doResponse(Http_C_3 s3) throws Exception
	{
		Http_C_4_Cases s4cases = s3.async(S, HTTPV).branch(S);
		switch (s4cases.op)
		{
			case _200: doResponseAux(s4cases.receive(_200)); break;
			case _404: doResponseAux(s4cases.receive(_404)); break;
			default: throw new RuntimeException("[TODO]: " + s4cases.op);
		}
	}

	private void doResponseAux(Http_C_5 s5) throws Exception
	{
		Http_C_5_Cases cases = s5.branch(S);
		switch (cases.op)
		{
			case ACCEPTR:  doResponseAux(cases.receive(ACCEPTR));  break;
			case CONTENTL: doResponseAux(cases.receive(CONTENTL)); break;
			case CONTENTT: doResponseAux(cases.receive(CONTENTT)); break;
			case DATE:     doResponseAux(cases.receive(DATE));     break;
			case ETAG:     doResponseAux(cases.receive(ETAG));     break;
			case LASTM:    doResponseAux(cases.receive(LASTM));    break;
			case SERVER:   doResponseAux(cases.receive(SERVER));   break;
			case STRICTTS: doResponseAux(cases.receive(STRICTTS)); break;
			case VARY:     doResponseAux(cases.receive(VARY));     break;
			case VIA:      doResponseAux(cases.receive(VIA));      break;
			case BODY:
			{
				Buf<Body> buf_body = new Buf<>();
				cases.receive(BODY, buf_body);
				System.out.println(buf_body.val.getBody());
				return;
			}
			default: throw new RuntimeException("[TODO]: " + cases.op);
		}
	}

	public void run1() throws Exception
	{
		Buf<HttpVersion> b_vers = new Buf<>();
		Buf<ContentLength> b_clen = new Buf<>();
		Buf<ContentType> b_ctype = new Buf<>();
		Buf<Body> b_body = new Buf<>();
		Buf<Server> b_serv = new Buf<>();
		
		Http http = new Http();
		try (MPSTEndpoint<Http, C> se = new MPSTEndpoint<>(http, Http.C, new HttpLongMessageFormatter()))
		{
			String host = "www.doc.ic.ac.uk"; int port = 80;
			//String host = "localhost"; int port = 8080;
		
			se.connect(S, SocketChannelEndpoint::new, host, port);

			Http_C_1 s1 = new Http_C_1(se);

			Http_C_4_Cases s4cases =
					s1.send(S, new RequestLine("/~rhu/", "1.1"))
					  .send(S, new Host(host))
					  .send(S, new Body(""))
					  .receive(S, HTTPV, b_vers)
					  .branch(S);
			Http_C_5 s5 =  // S?{ 200: ..., 404: ..., ... }
					  (s4cases.op == Branch_C_S_200__S_404_Enum._200) ? s4cases.receive(_200)
					: (s4cases.op == Branch_C_S_200__S_404_Enum._404) ? s4cases.receive(_404)
					: new Caller().call(() -> { throw new RuntimeException("Unknown status code: " + s4cases.op); });

			Y: while (true)
			{
				Http_C_5_Cases cases = s5.branch(S);
				switch (cases.op)
				{
					case ACCEPTR:
					{
						s5 = cases.receive(ACCEPTR);
						break;
					}
					case BODY:
					{
						cases.receive(BODY, b_body);
						System.out.println(b_body.val.getBody());
						break Y;
					}
					case CONTENTL:
					{
						s5 = cases.receive(CONTENTL, b_clen);
						break;
					}
					case CONTENTT:
					{
						s5 = cases.receive(CONTENTT, b_ctype);
						break;
					}
					case DATE:
					{
						s5 = cases.receive(DATE);
						break;
					}
					case ETAG:
					{
						s5 = cases.receive(ETAG);
						break;
					}
					case LASTM:
					{
						s5 = cases.receive(LASTM);
						break;
					}
					case SERVER:
					{
						s5 = cases.receive(SERVER, b_serv);
						break;
					}
					case STRICTTS:
					{
						s5 = cases.receive(STRICTTS);
						break;
					}
					case VARY:
					{
						s5 = cases.receive(VARY);
						break;
					}
					case VIA:
					{
						s5  = cases.receive(VIA);
						break;
					}
				}
			}
		}
	}
}
