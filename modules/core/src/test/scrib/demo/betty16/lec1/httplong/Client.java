package demo.betty16.lec1.httplong;

import static demo.betty16.lec1.httplong.HttpLong.Http.Http.ACCEPTR;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.BODY;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.C;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.CONTENTL;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.CONTENTT;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.DATE;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.ETAG;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.HTTPV;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.LASTM;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.S;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.SERVER;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.STRICTTS;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.VARY;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http.VIA;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http._200;
import static demo.betty16.lec1.httplong.HttpLong.Http.Http._404;

import org.scribble.net.Buf;
import org.scribble.net.session.SessionEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import demo.betty16.lec1.httplong.HttpLong.Http.Http;
import demo.betty16.lec1.httplong.HttpLong.Http.channels.C.Http_C_1;
import demo.betty16.lec1.httplong.HttpLong.Http.channels.C.Http_C_3;
import demo.betty16.lec1.httplong.HttpLong.Http.channels.C.Http_C_4_Cases;
import demo.betty16.lec1.httplong.HttpLong.Http.channels.C.Http_C_5;
import demo.betty16.lec1.httplong.HttpLong.Http.channels.C.Http_C_5_Cases;
import demo.betty16.lec1.httplong.HttpLong.Http.roles.C;
import demo.betty16.lec1.httplong.message.Body;
import demo.betty16.lec1.httplong.message.HttpLongMessageFormatter;
import demo.betty16.lec1.httplong.message.client.Host;
import demo.betty16.lec1.httplong.message.client.RequestLine;

public class Client {

	public static void main(String[] args) throws Exception {
		Http http = new Http();
		try (SessionEndpoint<Http, C> client = new SessionEndpoint<>(http, C, new HttpLongMessageFormatter())) {
			String host = "www.doc.ic.ac.uk"; int port = 80;
			//String host = "localhost"; int port = 8080;
		
			client.connect(S, SocketChannelEndpoint::new, host, port);
			new Client().run(new Http_C_1(client), host);
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
		switch (cases.op)
		{
			case _200: doResponseAux(cases.receive(_200)); break;
			case _404: doResponseAux(cases.receive(_404)); break;
			default: throw new RuntimeException("[TODO]: " + cases.op);
		}
	}

	private void doResponseAux(Http_C_5 c5) throws Exception {
		Http_C_5_Cases cases = c5.branch(S);
		switch (cases.op) {
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
			case BODY: {
				Buf<Body> buf_body = new Buf<>();
				cases.receive(BODY, buf_body);
				System.out.println(buf_body.val.getBody());
				return;
			}
			default: throw new RuntimeException("[TODO]: " + cases.op);
		}
	}
}
