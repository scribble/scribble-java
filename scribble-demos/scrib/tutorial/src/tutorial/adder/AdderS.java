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
package tutorial.adder;

import static tutorial.adder.Adder.Adder.Adder.Add;
import static tutorial.adder.Adder.Adder.Adder.Bye;
import static tutorial.adder.Adder.Adder.Adder.C;
import static tutorial.adder.Adder.Adder.Adder.Res;
import static tutorial.adder.Adder.Adder.Adder.S;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.runtime.net.Buf;
import org.scribble.runtime.net.ObjectStreamFormatter;
import org.scribble.runtime.net.scribsock.ScribServerSocket;
import org.scribble.runtime.net.scribsock.SocketChannelServer;
import org.scribble.runtime.net.session.MPSTEndpoint;

import tutorial.adder.Adder.Adder.Adder;
import tutorial.adder.Adder.Adder.channels.S.Adder_S_1;
import tutorial.adder.Adder.Adder.channels.S.Adder_S_1_Cases;
import tutorial.adder.Adder.Adder.roles.S;

public class AdderS {

	public static void main(String[] args) throws Exception {
		try (ScribServerSocket ss = new SocketChannelServer(8888)) {
			while (true) {
				Adder adder = new Adder();
				try (MPSTEndpoint<Adder, S> server
							= new MPSTEndpoint<>(adder, S, new ObjectStreamFormatter())) {
					server.accept(ss, C);
					new AdderS().run(new Adder_S_1(server));
				} catch (ScribbleRuntimeException | IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void run(Adder_S_1 s1) throws Exception {
		Buf<Integer> x = new Buf<>();
		Buf<Integer> y = new Buf<>();
		while (true) {
			Adder_S_1_Cases cases = s1.branch(C);
			switch (cases.op) {
				case Add: s1 = cases.receive(Add, x, y)
				                    .send(C, Res, x.val+y.val); break;
				case Bye: cases.receive(Bye);                   return;
			}
		}
	}
}
