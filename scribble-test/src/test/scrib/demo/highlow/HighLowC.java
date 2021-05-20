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
import highlow.HighLow.HighLow.roles.C;
import highlow.HighLow.HighLow.statechans.C.EndSocket;
import highlow.HighLow.HighLow.statechans.C.HighLow_C_1;
import highlow.HighLow.HighLow.statechans.C.HighLow_C_2_Cases;
import org.scribble.main.ScribRuntimeException;
import org.scribble.runtime.message.ObjectStreamFormatter;
import org.scribble.runtime.net.SocketChannelEndpoint;
import org.scribble.runtime.session.MPSTEndpoint;

import java.io.IOException;

import static highlow.HighLow.HighLow.HighLow.*;

public class HighLowC {
    public void run() throws Exception {
        HighLow proto = new HighLow();

        try (MPSTEndpoint<HighLow, C> se
                     = new MPSTEndpoint<>(proto, C, new ObjectStreamFormatter())) {
            se.request(B, SocketChannelEndpoint::new, "localhost", 9999);


            highLow(new HighLow_C_1(se));
        } catch (ScribRuntimeException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private EndSocket highLow(HighLow_C_1 c1) throws Exception {
        return aux(c1, 50);
    }

    private EndSocket aux(HighLow_C_1 c1, int g) throws Exception {
        System.out.println("Guessing " + g);
        HighLow_C_2_Cases c2Cases = c1.send(B, guess, g).branch(B);
        switch (c2Cases.op) {
            case higher:
                return aux(c2Cases.receive(B, higher), g + 1);
            case win:
                System.out.println("We win!");
                return c2Cases.receive(B, win);
            case lower:
                return aux(c2Cases.receive(B, lower), g - 1);
            case lose:
                System.out.println("We lose...");
                return c2Cases.receive(B, lose);
            default:
                throw new RuntimeException("Won't get in here: " + c2Cases.op);
        }
    }

    public static void main(String[] args) throws Exception {
        new HighLowC().run();
    }
}
