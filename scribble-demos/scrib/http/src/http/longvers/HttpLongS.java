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

import org.scribble.net.Buf;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.MPSTEndpoint;

import http.longvers.HttpLong.Http.Http;
import http.longvers.HttpLong.Http.channels.S.Http_S_1;
import http.longvers.HttpLong.Http.channels.S.Http_S_2;
import http.longvers.HttpLong.Http.channels.S.Http_S_2_Cases;
import http.longvers.HttpLong.Http.roles.S;
import http.longvers.message.Body;
import http.longvers.message.HttpLongMessageFormatter;
import http.longvers.message.client.Accept;
import http.longvers.message.client.AcceptEncoding;
import http.longvers.message.client.AcceptLanguage;
import http.longvers.message.client.Connection;
import http.longvers.message.client.DoNotTrack;
import http.longvers.message.client.Host;
import http.longvers.message.client.RequestLine;
import http.longvers.message.client.UpgradeInsecureRequests;
import http.longvers.message.client.UserAgent;
import http.longvers.message.server.ContentLength;
import http.longvers.message.server.HttpVersion;
import http.longvers.message.server._200;

public class HttpLongS
{
	public HttpLongS()
	{

	}

	public static void main(String[] args) throws Exception
	{
		Buf<RequestLine> b_reql = new Buf<>();
		Buf<Host> b_host = new Buf<>();
		Buf<UserAgent> b_usera = new Buf<>();
		Buf<Accept> b_acc = new Buf<>();
		Buf<AcceptLanguage> b_accl = new Buf<>();
		Buf<AcceptEncoding> b_acce = new Buf<>();
		Buf<DoNotTrack> b_dnt = new Buf<>();
		Buf<Connection> b_conn = new Buf<>();
		Buf<UpgradeInsecureRequests> b_upgradeir = new Buf<>();
		Buf<Body> b_body = new Buf<>();
		
		try (ScribServerSocket ss = new SocketChannelServer(8080))
		{
			while (true)	
			{
				Http http = new Http();
				try (MPSTEndpoint<Http, S> se = new MPSTEndpoint<>(http, Http.S, new HttpLongMessageFormatter()))
				{
					se.accept(ss, Http.C);
				
					Http_S_1 s1 = new Http_S_1(se);
					Http_S_2 s2 = s1.receive(Http.C, Http.REQUESTL, b_reql);
					
					System.out.println("Requested: " + b_reql.val);
					
					X: while (true)
					{
						Http_S_2_Cases s2cases = s2.branch(Http.C);
						switch (s2cases.op)
						{
							case ACCEPT:
							{
								s2 = s2cases.receive(Http.ACCEPT, b_acc);
								break;
							}
							case ACCEPTE:
							{
								s2 = s2cases.receive(Http.ACCEPTE, b_acce);
								break;
							}
							case ACCEPTL:
							{
								s2 = s2cases.receive(Http.ACCEPTL, b_accl);
								break;
							}
							case BODY:
							{
								//String body = "";
								String body = "<html><body>Hello, world!</body></html>";
								s2cases.receive(Http.BODY, b_body)
									.send(Http.C, new HttpVersion("1.1"))
									.send(Http.C, new _200("OK"))
									.send(Http.C, new ContentLength(body.length()))
									.send(Http.C, new Body(body));
								break X;
							}
							case CONNECTION:
							{
								s2 = s2cases.receive(Http.CONNECTION, b_conn);
								break;
							}
							case DNT:
							{
								s2 = s2cases.receive(Http.DNT, b_dnt);
								break;
							}
							case UPGRADEIR:
							{
								s2 = s2cases.receive(Http.UPGRADEIR, b_upgradeir);
								break;
							}
							case HOST:
							{
								s2 = s2cases.receive(Http.HOST, b_host);
								break;
							}
							case USERA:
							{
								s2 = s2cases.receive(Http.USERA, b_usera);
								break;
							}
						}
					}
				}
			}
		}
	}
}
