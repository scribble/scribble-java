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

import static betty16.lec2.adder.Adder.Adder.Adder.Add;
import static betty16.lec2.adder.Adder.Adder.Adder.Bye;
import static betty16.lec2.adder.Adder.Adder.Adder.C;
import static betty16.lec2.adder.Adder.Adder.Adder.Res;
import static betty16.lec2.adder.Adder.Adder.Adder.S;

import org.scribble.net.Buf;
import org.scribble.net.ObjectStreamFormatter;
import org.scribble.net.session.MPSTEndpoint;
import org.scribble.net.session.SocketChannelEndpoint;

import betty16.lec2.adder.Adder.Adder.Adder;
import betty16.lec2.adder.Adder.Adder.channels.C.Adder_C_1;
import betty16.lec2.adder.Adder.Adder.channels.C.EndSocket;
import betty16.lec2.adder.Adder.Adder.roles.C;

public class MyFibC {

	public static void main(String[] args) throws Exception {
		Adder adder = new Adder();
		try (MPSTEndpoint<Adder, C> client = new MPSTEndpoint<>(adder, C, new ObjectStreamFormatter())) {	
			client.connect(S, SocketChannelEndpoint::new, "localhost", 8888);

			Buf<Integer> i1 = new Buf<>(0);
			Buf<Integer> i2 = new Buf<>(1);
			int N = 10;
			new MyFibC().fibo(new Adder_C_1(client), i1, i2, N);
			System.out.println("C: " + i1.val);
		}
	}
	
	// Result: i1.val contains the N-th Fibonacci number
	private EndSocket fibo(Adder_C_1 c1, Buf<Integer> i1, Buf<Integer> i2, int i) throws Exception {

		//... c1.send(S, Add, i1.val, i1.val=i2.val) ...
		
		throw new RuntimeException("[TODO]");
	}
}
