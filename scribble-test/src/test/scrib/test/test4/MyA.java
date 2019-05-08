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
package test.test4;

import static test.test4.Test4.Proto1.Proto1.A;
import static test.test4.Test4.Proto1.Proto1.B;

import java.io.IOException;
import java.util.concurrent.Future;

import org.scribble.runtime.net.SocketChannelEndpoint;

import test.test4.Test4.Proto1.Proto1;
import test.test4.Test4.Proto1.callbacks.A.Proto1_A;
import test.test4.Test4.Proto1.callbacks.A.states.Proto1_A_1;
import test.test4.sig.Bar;
import test.test4.sig.Foo;
import test.test4.sig.Test4Formatter;;

public class MyA
{
	public static void main(String[] args) throws IOException
	{
		Proto1 P1 = new Proto1();
		try (Proto1_A<int[]> a = new Proto1_A<>(P1, A, new Test4Formatter(), new int[1]))
		{
			a.request(B, SocketChannelEndpoint::new, "localhost", 8888);

			a.icallback(Proto1_A_1.id,
					x -> (x[0]++ < 5) ? new Proto1_A_1.B.Foo(new Foo("abc" + x[0])) : new Proto1_A_1.B.Bar(new Bar(123))
			);
			// FIXME: "inline" Sig class constructor args directly as Op class constructor args -- maybe by reflection
			
			Future<Void> f = a.run();
			f.get();

			System.out.println("(A) end");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
