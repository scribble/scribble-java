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
import highlow.HighLow.HighLow.callbacks.C.HighLow_C;
import highlow.HighLow.HighLow.callbacks.C.HighLow_C_2_Branch;
import highlow.HighLow.HighLow.callbacks.C.states.HighLow_C_1;
import highlow.HighLow.HighLow.callbacks.C.states.HighLow_C_2;
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

import static highlow.HighLow.HighLow.HighLow.B;
import static highlow.HighLow.HighLow.HighLow.C;

public class MyC {
    public void run() throws Exception {
        HighLow proto = new HighLow();

        try (HighLow_C<MyStateC> c = new HighLow_C<>(proto, C, new ObjectStreamFormatter(), new MyStateC())) {
            c.icallback(HighLow_C_1.id, (data) -> {
                System.out.println("Guessing " + data.guess);
                return new HighLow_C_1.B.guess(data.guess);
            });
            c.icallback(HighLow_C_2.id, new MyHandlerC());

            c.request(B, SocketChannelEndpoint::new, "localhost", 9999);
            Future<Void> f = c.run();
            f.get();
        } catch (ScribRuntimeException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new MyC().run();
    }
}

class MyStateC {
    public int guess = 50;
}

class MyHandlerC extends HighLow_C_2_Branch<MyStateC> {
    @Override
    public void receive(MyStateC data, B peer, higher op) {
        data.guess++;
    }

    @Override
    public void receive(MyStateC data, B peer, lose op) {
        System.out.println("We lose...");
    }

    @Override
    public void receive(MyStateC data, B peer, lower op) {
        data.guess--;
    }

    @Override
    public void receive(MyStateC data, B peer, win op) {
        System.out.println("We win!");
    }
}
