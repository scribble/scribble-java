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
import highlow.HighLow.HighLow.roles.A;
import highlow.HighLow.HighLow.statechans.A.EndSocket;
import highlow.HighLow.HighLow.statechans.A.HighLow_A_1;
import highlow.HighLow.HighLow.statechans.A.HighLow_A_3;
import highlow.HighLow.HighLow.statechans.A.HighLow_A_3_Cases;
import org.scribble.main.ScribRuntimeException;
import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.SocketChannelEndpoint;
import org.scribble.runtime.session.MPSTEndpoint;

import java.io.IOException;

import static highlow.HighLow.HighLow.HighLow.*;

public class HighLowA {
    public void run() throws Exception {
        HighLow proto = new HighLow();
        try (MPSTEndpoint<HighLow, A> se
                     = new MPSTEndpoint<>(proto, A, new ObjectStreamFormatter())) {
            se.request(B, SocketChannelEndpoint::new, "localhost", 8888);
            highLow(new HighLow_A_1(se));
        } catch (ScribRuntimeException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private EndSocket highLow(HighLow_A_1 a1) throws Exception {
        return aux(a1.send(B, start, 42).send(B, limit, 5));
    }

    private EndSocket aux(HighLow_A_3 a3) throws Exception {
        HighLow_A_3_Cases a3Cases = a3.branch(B);
        switch (a3Cases.op) {
            case higher:
                return aux(a3Cases.receive(higher));
            case lose:
                return a3Cases.receive(lose);
            case lower:
                return aux(a3Cases.receive(lower));
            case win:
                return a3Cases.receive(win);
            default:
                throw new RuntimeException("Won't get in here: " + a3Cases.op);
        }
    }

    public static void main(String[] args) throws Exception {
        new HighLowA().run();
    }
}
