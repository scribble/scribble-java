package demo.betty16.lec2.adder;

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
import demo.betty16.lec2.adder.Adder.Adder.channels.S.Adder_S_1_Handler;
import demo.betty16.lec2.adder.Adder.Adder.channels.S.Adder_S_2;
import demo.betty16.lec2.adder.Adder.Adder.channels.S.Adder_S_3;
import demo.betty16.lec2.adder.Adder.Adder.ops.Add;
import demo.betty16.lec2.adder.Adder.Adder.ops.Bye;
import demo.betty16.lec2.adder.Adder.Adder.roles.S;

public class Server2 implements Adder_S_1_Handler {

	public static void main(String[] args) throws Exception {
		try (ScribServerSocket ss = new SocketChannelServer(8888)) {
			while (true) {
				Adder adder = new Adder();
				try (SessionEndpoint<Adder, S> server = new SessionEndpoint<>(adder, S, new ObjectStreamFormatter())) {
					server.accept(ss, C);

					new Adder_S_1(server).branch(C, new Server2());
				} catch (ScribbleRuntimeException | IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void receive(Adder_S_2 s2, Add op, Buf<Integer> arg1, Buf<Integer> arg2) throws ScribbleRuntimeException, IOException, ClassNotFoundException {
		s2.send(C, Res, arg1.val + arg2.val).branch(C, this);
	}

	@Override
	public void receive(Adder_S_3 s3, Bye op) throws ScribbleRuntimeException, IOException {
		s3.send(C, Bye);
	}
}
