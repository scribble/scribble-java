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

import org.scribble.runtime.net.Buf;
import org.scribble.runtime.net.ObjectStreamFormatter;
import org.scribble.runtime.net.session.MPSTEndpoint;
import org.scribble.runtime.net.session.SocketChannelEndpoint;

import tutorial.adder.Adder.Adder.Adder;
import tutorial.adder.Adder.Adder.channels.C.Adder_C_1;
import tutorial.adder.Adder.Adder.channels.C.Adder_C_2;
import tutorial.adder.Adder.Adder.roles.C;

public class AdderC {

	public static void main(String[] args) throws Exception {
		Adder adder = new Adder();
		try (MPSTEndpoint<Adder, C> client =
					new MPSTEndpoint<>(adder, C, new ObjectStreamFormatter())) {	
			client.connect(S, SocketChannelEndpoint::new, "localhost", 8888);
			int n = 10;
			System.out.println(n + "th Fibonacci number: "
					+ fibo(new Adder_C_1(client), n));
		}
	}
	
	private static int fibo(Adder_C_1 c1, int n) throws Exception {
		Buf<Integer> x = new Buf<>(0);
		Buf<Integer> y = new Buf<>(1);
		Buf<Integer> i = new Buf<>(1);
		while (i.val < n) {
			Adder_C_2 c2 = c1.send(S, Add, x.val, y.val);
			x.val = y.val;
			c1 = c2.receive(S, Res, y);
			c1 = add1(c1, i);
		}
		c1.send(S, Bye);  // EndSocket
		return x.val;
	}
	
	private static Adder_C_1 add1(Adder_C_1 c1, Buf<Integer> i) throws Exception {
		return c1.send(S, Add, i.val, 1).receive(S, Res, i);
	}
}
