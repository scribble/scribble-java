package test.scratch.Scratch1.Proto1.channels.S;

import java.io.IOException;
import test.scratch.Scratch1.Proto1.*;
import test.scratch.Scratch1.Proto1.roles.*;
import test.scratch.Scratch1.Proto1.ops.*;

public final class Proto1_S_3 extends org.scribble.net.scribsock.SendSocket<Proto1, S> {

	protected Proto1_S_3(org.scribble.net.session.SessionEndpoint<Proto1, S> se, boolean dummy) {
		super(se);
	}

	public Proto1_S_1 send(C role, _3 op, java.lang.Integer arg0) throws org.scribble.main.ScribbleRuntimeException, IOException {
		super.writeScribMessage(role, Proto1._3, arg0);

		return new Proto1_S_1(this.se, true);
	}
}