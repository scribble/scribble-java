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
package betty16.lec1.httplong;

import static betty16.lec1.httplong.HttpLong.Http.Http.ACCEPTR;
import static betty16.lec1.httplong.HttpLong.Http.Http.BODY;
import static betty16.lec1.httplong.HttpLong.Http.Http.C;
import static betty16.lec1.httplong.HttpLong.Http.Http.CONTENTL;
import static betty16.lec1.httplong.HttpLong.Http.Http.CONTENTT;
import static betty16.lec1.httplong.HttpLong.Http.Http.DATE;
import static betty16.lec1.httplong.HttpLong.Http.Http.ETAG;
import static betty16.lec1.httplong.HttpLong.Http.Http.HTTPV;
import static betty16.lec1.httplong.HttpLong.Http.Http.LASTM;
import static betty16.lec1.httplong.HttpLong.Http.Http.S;
import static betty16.lec1.httplong.HttpLong.Http.Http.SERVER;
import static betty16.lec1.httplong.HttpLong.Http.Http.STRICTTS;
import static betty16.lec1.httplong.HttpLong.Http.Http.VARY;
import static betty16.lec1.httplong.HttpLong.Http.Http.VIA;
import static betty16.lec1.httplong.HttpLong.Http.Http._200;
import static betty16.lec1.httplong.HttpLong.Http.Http._404;

import org.scribble.runtime.net.Buf;
import org.scribble.runtime.net.session.MPSTEndpoint;
import org.scribble.runtime.net.session.SocketChannelEndpoint;

import betty16.lec1.httplong.HttpLong.Http.Http;
import betty16.lec1.httplong.HttpLong.Http.channels.C.EndSocket;
import betty16.lec1.httplong.HttpLong.Http.channels.C.Http_C_1;
import betty16.lec1.httplong.HttpLong.Http.channels.C.Http_C_3;
import betty16.lec1.httplong.HttpLong.Http.channels.C.Http_C_4_Cases;
import betty16.lec1.httplong.HttpLong.Http.channels.C.Http_C_5;
import betty16.lec1.httplong.HttpLong.Http.channels.C.Http_C_5_Cases;
import betty16.lec1.httplong.HttpLong.Http.roles.C;
import betty16.lec1.httplong.message.Body;
import betty16.lec1.httplong.message.HttpLongMessageFormatter;
import betty16.lec1.httplong.message.client.Host;
import betty16.lec1.httplong.message.client.RequestLine;

public class HttpLongC {

	public static void main(String[] args) throws Exception {
		Http http = new Http();
		try (MPSTEndpoint<Http, C> client = new MPSTEndpoint<>(http, C, new HttpLongMessageFormatter())) {
			String host = "www.doc.ic.ac.uk"; int port = 80;
			//String host = "localhost"; int port = 8080;
		
			client.connect(S, SocketChannelEndpoint::new, host, port);
			new HttpLongC().run(new Http_C_1(client), host);
		}
	}

	public void run(Http_C_1 c, String host) throws Exception {
			doResponse(doRequest(c, host));
	}
	
	private Http_C_3 doRequest(Http_C_1 c1, String host) throws Exception {
		return c1.send(S, new RequestLine("/~rhu/", "1.1"))
		         .send(S, new Host(host))
		         .send(S, new Body(""));
	}

	private void doResponse(Http_C_3 c3) throws Exception {
		Http_C_4_Cases cases = c3.async(S, HTTPV).branch(S);
		switch (cases.op) {
			case _200: doResponseAux(cases.receive(_200)); break;
			case _404: doResponseAux(cases.receive(_404)); break;
			default: throw new RuntimeException("[TODO]: " + cases.op);
		}
	}

	private EndSocket doResponseAux(Http_C_5 c5) throws Exception {
		Http_C_5_Cases cases = c5.branch(S);
		switch (cases.op) {
			case ACCEPTR:  return doResponseAux(cases.receive(ACCEPTR)); 
			case CONTENTL: return doResponseAux(cases.receive(CONTENTL));
			case CONTENTT: return doResponseAux(cases.receive(CONTENTT));
			case DATE:     return doResponseAux(cases.receive(DATE));    
			case ETAG:     return doResponseAux(cases.receive(ETAG));    
			case LASTM:    return doResponseAux(cases.receive(LASTM));   
			case SERVER:   return doResponseAux(cases.receive(SERVER));  
			case STRICTTS: return doResponseAux(cases.receive(STRICTTS));
			case VARY:     return doResponseAux(cases.receive(VARY));    
			case VIA:      return doResponseAux(cases.receive(VIA));     
			case BODY: {
				Buf<Body> buf_body = new Buf<>();
				EndSocket end = cases.receive(BODY, buf_body);
				System.out.println(buf_body.val.getBody());
				return end;
			}
			default: throw new RuntimeException("[TODO]: " + cases.op);
		}
	}
}
