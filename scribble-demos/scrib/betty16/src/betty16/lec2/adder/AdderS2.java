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
package betty16.lec2.adder;

import static betty16.lec2.adder.Adder.Adder.Adder.Bye;
import static betty16.lec2.adder.Adder.Adder.Adder.C;
import static betty16.lec2.adder.Adder.Adder.Adder.Res;
import static betty16.lec2.adder.Adder.Adder.Adder.S;

import java.io.IOException;

import org.scribble.main.ScribbleRuntimeException;
import org.scribble.runtime.net.Buf;
import org.scribble.runtime.net.ObjectStreamFormatter;
import org.scribble.runtime.net.scribsock.ScribServerSocket;
import org.scribble.runtime.net.scribsock.SocketChannelServer;
import org.scribble.runtime.net.session.MPSTEndpoint;

import betty16.lec2.adder.Adder.Adder.Adder;
import betty16.lec2.adder.Adder.Adder.channels.S.Adder_S_1;
import betty16.lec2.adder.Adder.Adder.channels.S.Adder_S_1_Handler;
import betty16.lec2.adder.Adder.Adder.channels.S.Adder_S_2;
import betty16.lec2.adder.Adder.Adder.channels.S.Adder_S_3;
import betty16.lec2.adder.Adder.Adder.ops.Add;
import betty16.lec2.adder.Adder.Adder.ops.Bye;
import betty16.lec2.adder.Adder.Adder.roles.S;

public class AdderS2 implements Adder_S_1_Handler {

	public static void main(String[] args) throws Exception {
		try (ScribServerSocket ss = new SocketChannelServer(8888)) {
			while (true) {
				Adder adder = new Adder();
				try (MPSTEndpoint<Adder, S> server = new MPSTEndpoint<>(adder, S, new ObjectStreamFormatter())) {
					server.accept(ss, C);

					new Adder_S_1(server).branch(C, new AdderS2());
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
