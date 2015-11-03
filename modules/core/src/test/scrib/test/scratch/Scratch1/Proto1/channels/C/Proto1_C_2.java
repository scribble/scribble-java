package test.scratch.Scratch1.Proto1.channels.C;

import java.io.IOException;
import test.scratch.Scratch1.Proto1.*;
import test.scratch.Scratch1.Proto1.roles.*;
import test.scratch.Scratch1.Proto1.ops.*;

public final class Proto1_C_2 extends org.scribble.net.scribsock.SendSocket<Proto1, C> {

	protected Proto1_C_2(org.scribble.net.session.SessionEndpoint<Proto1, C> se, boolean dummy) {
		super(se);
	}

	public Proto1_C_3 send(S role, _2 op, java.lang.Integer arg0) throws org.scribble.main.ScribbleRuntimeException, IOException {
		super.writeScribMessage(role, Proto1._2, arg0);

		return new Proto1_C_3(this.se, true);
	}

	public org.scribble.net.scribsock.EndSocket<Proto1, C> send(S role, _4 op) throws org.scribble.main.ScribbleRuntimeException, IOException {
		super.writeScribMessage(role, Proto1._4);

		this.se.setCompleted();
		return new org.scribble.net.scribsock.EndSocket<>(this.se, true);
	}
}