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
import highlow.HighLow.HighLow.callbacks.B.HighLow_B;
import highlow.HighLow.HighLow.callbacks.B.HighLow_B_1_Branch;
import highlow.HighLow.HighLow.callbacks.B.HighLow_B_2_Branch;
import highlow.HighLow.HighLow.callbacks.B.HighLow_B_3_Branch;
import highlow.HighLow.HighLow.callbacks.B.states.*;
import highlow.HighLow.HighLow.ops.guess;
import highlow.HighLow.HighLow.ops.secret;
import highlow.HighLow.HighLow.ops.tries;
import highlow.HighLow.HighLow.roles.A;
import highlow.HighLow.HighLow.roles.C;
import org.scribble.main.ScribRuntimeException;
import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.ScribServerSocket;
import org.scribble.runtime.net.SocketChannelServer;

import java.io.IOException;
import java.util.concurrent.Future;

import static highlow.HighLow.HighLow.HighLow.A;
import static highlow.HighLow.HighLow.HighLow.B;
import static highlow.HighLow.HighLow.HighLow.C;

public class MyB {
    public void run() throws Exception {

        try (ScribServerSocket ssA = new SocketChannelServer(8888);
             ScribServerSocket ssC = new SocketChannelServer(9999)
        ) {
            while (true) {
                HighLow proto = new HighLow();
                try (HighLow_B<MyStateB> b = new HighLow_B<>(proto, B, new ObjectStreamFormatter(), new MyStateB())) {
                    b.icallback(HighLow_B_1.id, new MyHandlerB1());
                    b.icallback(HighLow_B_2.id, new MyHandlerB2());
                    b.icallback(HighLow_B_3.id, new MyHandlerB3());
                    b.icallback(HighLow_B_4.id, (data) -> {
                        if (data.secret == data.guess) {
                            return new HighLow_B_4.C.win();
                        } else if (data.tries == 0) {
                            return new HighLow_B_4.C.lose();
                        } else {
                            data.tries--;
                            return (data.secret > data.guess)
                                    ? new HighLow_B_4.C.higher()
                                    : new HighLow_B_4.C.lower();
                        }
                    });
                    b.icallback(HighLow_B_5.id, (data) -> new HighLow_B_5.A.lose());
                    b.icallback(HighLow_B_6.id, (data) -> new HighLow_B_6.A.higher());
                    b.icallback(HighLow_B_7.id, (data) -> new HighLow_B_7.A.lower());
                    b.icallback(HighLow_B_8.id, (data) -> new HighLow_B_8.A.win());

                    b.accept(ssA, A);
                    b.accept(ssC, C);
                    Future<Void> f = b.run();
                    f.get();
                } catch (ScribRuntimeException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new MyB().run();
    }
}

class MyStateB {
    public int secret;
    public int tries;
    public int guess;
}

class MyHandlerB1 extends HighLow_B_1_Branch<MyStateB> {
    @Override
    public void receive(MyStateB data, A peer, secret op, Integer arg1) {
        data.secret = arg1;
    }
}

class MyHandlerB2 extends HighLow_B_2_Branch<MyStateB> {
    @Override
    public void receive(MyStateB data, A peer, tries op, Integer arg1) {
        data.tries = arg1;
    }
}

class MyHandlerB3 extends HighLow_B_3_Branch<MyStateB> {
    @Override
    public void receive(MyStateB data, C peer, guess op, Integer arg1) {
        data.guess = arg1;
    }
}
