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

package highlow;

import highlow.HighLow.HighLow.HighLow;
import highlow.HighLow.HighLow.roles.B;
import highlow.HighLow.HighLow.statechans.B.EndSocket;
import highlow.HighLow.HighLow.statechans.B.HighLow_B_1;
import highlow.HighLow.HighLow.statechans.B.HighLow_B_3;
import highlow.HighLow.HighLow.statechans.B.HighLow_B_4;
import org.scribble.main.ScribRuntimeException;
import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.ScribServerSocket;
import org.scribble.runtime.net.SocketChannelServer;
import org.scribble.runtime.session.MPSTEndpoint;
import org.scribble.runtime.util.Buf;

import java.io.IOException;

import static highlow.HighLow.HighLow.HighLow.*;

public class HighLowB {
    public void run() throws Exception {
        try (ScribServerSocket ssA = new SocketChannelServer(8888);
             ScribServerSocket ssC = new SocketChannelServer(9999);
        ) {
            while (true) {
                HighLow proto = new HighLow();
                try (MPSTEndpoint<HighLow, B> se
                             = new MPSTEndpoint<>(proto, B, new ObjectStreamFormatter())) {
                    se.accept(ssA, A);
                    se.accept(ssC, C);
                    highLow(new HighLow_B_1(se));
                } catch (ScribRuntimeException | IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private EndSocket highLow(HighLow_B_1 b1) throws Exception {
        Buf<Integer> s = new Buf<>();
        Buf<Integer> l = new Buf<>();
        return aux(b1.receive(A, secret, s).receive(A, tries, l), s.val, l.val);
    }

    private EndSocket aux(HighLow_B_3 b3, int secret, int tries) throws Exception {
        Buf<Integer> g = new Buf<>();
        HighLow_B_4 b4 = b3.receive(C, guess, g);
        if (g.val == secret) {
            return b4.send(C, win).send(A, lose);
        } else if (tries <= 1) {
            return b4.send(C, lose).send(A, win);
        } else if (g.val < secret) {
            return aux(b4.send(C, higher).send(A, higher), secret, tries - 1);
        } else {
            return aux(b4.send(C, lower).send(A, lower), secret, tries - 1);
        }
    }

    public static void main(String[] args) throws Exception {
        new HighLowB().run();
    }
}
