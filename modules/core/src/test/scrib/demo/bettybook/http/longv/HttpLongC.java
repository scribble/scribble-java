package demo.bettybook.http.longv;

import static demo.bettybook.http.longv.HttpLong.Http.Http.ACCEPTR;
import static demo.bettybook.http.longv.HttpLong.Http.Http.BODY;
import static demo.bettybook.http.longv.HttpLong.Http.Http.C;
import static demo.bettybook.http.longv.HttpLong.Http.Http.CONTENTL;
import static demo.bettybook.http.longv.HttpLong.Http.Http.CONTENTT;
import static demo.bettybook.http.longv.HttpLong.Http.Http.DATE;
import static demo.bettybook.http.longv.HttpLong.Http.Http.ETAG;
import static demo.bettybook.http.longv.HttpLong.Http.Http.HTTPV;
import static demo.bettybook.http.longv.HttpLong.Http.Http.LASTM;
import static demo.bettybook.http.longv.HttpLong.Http.Http.S;
import static demo.bettybook.http.longv.HttpLong.Http.Http.SERVER;
import static demo.bettybook.http.longv.HttpLong.Http.Http.STRICTTS;
import static demo.bettybook.http.longv.HttpLong.Http.Http.VARY;
import static demo.bettybook.http.longv.HttpLong.Http.Http.VIA;
import static demo.bettybook.http.longv.HttpLong.Http.Http._200;
import static demo.bettybook.http.longv.HttpLong.Http.Http._404;

import org.scribble.net.Buf;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.bettybook.http.longv.HttpLong.Http.Http;
import demo.bettybook.http.longv.HttpLong.Http.channels.C.EndSocket;
import demo.bettybook.http.longv.HttpLong.Http.channels.C.Http_C_1;
import demo.bettybook.http.longv.HttpLong.Http.channels.C.Http_C_3;
import demo.bettybook.http.longv.HttpLong.Http.channels.C.Http_C_4_Cases;
import demo.bettybook.http.longv.HttpLong.Http.channels.C.Http_C_5;
import demo.bettybook.http.longv.HttpLong.Http.channels.C.Http_C_5_Cases;
import demo.bettybook.http.longv.HttpLong.Http.roles.C;
import demo.bettybook.http.longv.message.Body;
import demo.bettybook.http.longv.message.HttpLongMessageFormatter;
import demo.bettybook.http.longv.message.client.Host;
import demo.bettybook.http.longv.message.client.RequestLine;

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
