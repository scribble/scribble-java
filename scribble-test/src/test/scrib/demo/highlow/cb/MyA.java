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

package highlow.cb;

import highlow.HighLow.HighLow.HighLow;
import highlow.HighLow.HighLow.callbacks.A.HighLow_A;
import highlow.HighLow.HighLow.callbacks.A.HighLow_A_3_Branch;
import highlow.HighLow.HighLow.callbacks.A.states.HighLow_A_1;
import highlow.HighLow.HighLow.callbacks.A.states.HighLow_A_2;
import highlow.HighLow.HighLow.callbacks.A.states.HighLow_A_3;
import highlow.HighLow.HighLow.ops.higher;
import highlow.HighLow.HighLow.ops.lose;
import highlow.HighLow.HighLow.ops.lower;
import highlow.HighLow.HighLow.ops.win;
import highlow.HighLow.HighLow.roles.B;
import org.scribble.main.ScribRuntimeException;
import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.SocketChannelEndpoint;

import java.io.IOException;
import java.util.concurrent.Future;

import static highlow.HighLow.HighLow.HighLow.A;
import static highlow.HighLow.HighLow.HighLow.B;

public class MyA {
    public void run() throws Exception {
        HighLow proto = new HighLow();

        try (HighLow_A<Void> c = new HighLow_A<>(proto, A, new ObjectStreamFormatter(), null)) {
            c.icallback(HighLow_A_1.id, (data) -> new HighLow_A_1.B.start(42));
            c.icallback(HighLow_A_2.id, (data) -> new HighLow_A_2.B.limit(5));
            c.icallback(HighLow_A_3.id, new MyHandlerA());

            c.request(B, SocketChannelEndpoint::new, "localhost", 8888);
            Future<Void> f = c.run();
            f.get();
        } catch (ScribRuntimeException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new MyA().run();
    }
}

class MyHandlerA extends HighLow_A_3_Branch<Void> {
    @Override
    public void receive(Void data, highlow.HighLow.HighLow.roles.B peer, higher op) {
    }

    @Override
    public void receive(Void data, B peer, lose op) {
        System.out.println("We lose...");
    }

    @Override
    public void receive(Void data, B peer, lower op) {
    }

    @Override
    public void receive(Void data, B peer, win op) {
        System.out.println("We win!");
    }
}
