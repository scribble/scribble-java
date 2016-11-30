package demo.bettybook.http.longv;

import static demo.bettybook.http.longv.HttpLong.Http.Http.Accept;
import static demo.bettybook.http.longv.HttpLong.Http.Http.AcceptE;
import static demo.bettybook.http.longv.HttpLong.Http.Http.AcceptL;
import static demo.bettybook.http.longv.HttpLong.Http.Http.Body;
import static demo.bettybook.http.longv.HttpLong.Http.Http.C;
import static demo.bettybook.http.longv.HttpLong.Http.Http.Connection;
import static demo.bettybook.http.longv.HttpLong.Http.Http.DNT;
import static demo.bettybook.http.longv.HttpLong.Http.Http.Host;
import static demo.bettybook.http.longv.HttpLong.Http.Http.RequestL;
import static demo.bettybook.http.longv.HttpLong.Http.Http.S;
import static demo.bettybook.http.longv.HttpLong.Http.Http.UserA;
import static demo.bettybook.http.longv.HttpLong.Http.Http.UpgradeIR;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.MPSTEndpoint;

import demo.bettybook.http.longv.HttpLong.Http.Http;
import demo.bettybook.http.longv.HttpLong.Http.channels.S.Http_S_1;
import demo.bettybook.http.longv.HttpLong.Http.channels.S.Http_S_2;
import demo.bettybook.http.longv.HttpLong.Http.channels.S.Http_S_2_Cases;
import demo.bettybook.http.longv.HttpLong.Http.roles.S;
import demo.bettybook.http.longv.message.Body;
import demo.bettybook.http.longv.message.HttpLongMessageFormatter;
import demo.bettybook.http.longv.message.server.ContentLength;
import demo.bettybook.http.longv.message.server.HttpVersion;
import demo.bettybook.http.longv.message.server._200;

public class HttpLongS
{
	public static void main(String[] args) throws Exception
	{
		try (ScribServerSocket ss = new SocketChannelServer(8080)) {
			while (true)	{
				Http http = new Http();
				try (MPSTEndpoint<Http, S> server = new MPSTEndpoint<>(http, S, new HttpLongMessageFormatter())) {
					server.accept(ss, C);
				
					run(new Http_S_1(server));
				}
				catch (IOException | ClassNotFoundException | ScribbleRuntimeException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void run(Http_S_1 s1) throws ClassNotFoundException, ScribbleRuntimeException, IOException {
		Buf<Object> buf = new Buf<>();
		
		Http_S_2 s2 = s1.receive(C, RequestL, buf);
		System.out.println("Requested: " + buf.val);
		
		Y: while (true) {
			Http_S_2_Cases cases = s2.branch(C);
			switch (cases.op) {
				case Accept:  s2 = cases.receive(Accept, buf); break;
				case AcceptE: s2 = cases.receive(AcceptE, buf); break;
				case AcceptL: s2 = cases.receive(AcceptL, buf); break;
				case Body:
				{
					String body = "<html><body>Hello, World!</body></html>";
					cases.receive(Body, buf)
						.send(C, new HttpVersion("1.1"))
						.send(C, new _200("OK"))
						.send(C, new ContentLength(body.length()))
						.send(C, new Body(body));
					break Y;
				}
				case Connection: s2 = cases.receive(Connection, buf); break;
				case DNT:        s2 = cases.receive(DNT, buf); break;
				case UpgradeIR:  s2 = cases.receive(UpgradeIR, buf); break;
				case Host:       s2 = cases.receive(Host, buf); break;
				case UserA:      s2 = cases.receive(UserA, buf); break;
			}
		}
	}
}
