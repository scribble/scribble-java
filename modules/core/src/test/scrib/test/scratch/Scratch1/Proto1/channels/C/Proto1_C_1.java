package test.scratch.Scratch1.Proto1.channels.C;

import java.io.IOException;
import test.scratch.Scratch1.Proto1.*;
import test.scratch.Scratch1.Proto1.roles.*;
import test.scratch.Scratch1.Proto1.ops.*;

public final class Proto1_C_1 extends org.scribble.net.scribsock.SendSocket<Proto1, C> {

	protected Proto1_C_1(org.scribble.net.session.SessionEndpoint<Proto1, C> se, boolean dummy) {
		super(se);
	}

	public Proto1_C_1(org.scribble.net.session.SessionEndpoint<Proto1, C> se) throws org.scribble.main.ScribbleRuntimeException {
		super(se);
		se.init();
	}

	public Proto1_C_2 send(S role, _1 op) throws org.scribble.main.ScribbleRuntimeException, IOException {
		super.writeScribMessage(role, Proto1._1);

		return new Proto1_C_2(this.se, true);
	}
}