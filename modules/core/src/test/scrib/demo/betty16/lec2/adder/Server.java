package demo.betty16.lec2.adder;

import static demo.betty16.lec2.adder.Adder.Adder.Adder.Add;
import static demo.betty16.lec2.adder.Adder.Adder.Adder.Bye;
import static demo.betty16.lec2.adder.Adder.Adder.Adder.C;
import static demo.betty16.lec2.adder.Adder.Adder.Adder.Res;
import static demo.betty16.lec2.adder.Adder.Adder.Adder.S;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.scribsock.ScribServerSocket;
import org.scribble.net.scribsock.SocketChannelServer;
import org.scribble.net.session.SessionEndpoint;

import demo.betty16.lec2.adder.Adder.Adder.Adder;
import demo.betty16.lec2.adder.Adder.Adder.channels.S.Adder_S_1;
import demo.betty16.lec2.adder.Adder.Adder.channels.S.Adder_S_1_Cases;
import demo.betty16.lec2.adder.Adder.Adder.roles.S;

public class Server {

	public static void main(String[] args) throws Exception {
		try (ScribServerSocket ss = new SocketChannelServer(8888)) {
			while (true) {
				Adder adder = new Adder();
				try (SessionEndpoint<Adder, S> server = new SessionEndpoint<>(adder, S, new ObjectStreamFormatter())) {
					server.accept(ss, C);
					new Server().run(new Adder_S_1(server));
				} catch (ScribbleRuntimeException | IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void run(Adder_S_1 s1) throws Exception {
		Buf<Integer> i1 = new Buf<>();
		Buf<Integer> i2 = new Buf<>();
		while (true) {
			Adder_S_1_Cases cases = s1.branch(C);
			switch (cases.op) {
				case Add: s1 = cases.receive(Add, i1, i2).send(C, Res, i1.val+i2.val); break;
				case Bye: cases.receive(Bye).send(C, Bye);                             return;
			}
		}
	}

	/*
	private EndSocket run(Adder_S_1 s1, Buf<Integer> i1, Buf<Integer> i2) throws Exception {
		Adder_S_1_Cases cases = s1.branch(C);
		switch (cases.op) {
			case Add: return run(
			                   cases.receive(Add, i1, i2).send(C, Res, i1.val+i2.val),
			                   i1, i2);
			case Bye: return cases.receive(Bye).send(C, Bye);
			default:  throw new RuntimeException("Dummy.");  // Java
		}
	}
	//*/
}
