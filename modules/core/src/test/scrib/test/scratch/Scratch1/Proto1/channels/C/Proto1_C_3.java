package test.scratch.Scratch1.Proto1.channels.C;

import java.io.IOException;
import test.scratch.Scratch1.Proto1.*;
import test.scratch.Scratch1.Proto1.roles.*;
import test.scratch.Scratch1.Proto1.ops.*;

public final class Proto1_C_3 extends org.scribble.net.scribsock.ReceiveSocket<Proto1, C> {

	protected Proto1_C_3(org.scribble.net.session.SessionEndpoint<Proto1, C> se, boolean dummy) {
		super(se);
	}

	public Proto1_C_1 receive(S role, _3 op, org.scribble.net.Buf<? super java.lang.Integer> arg1) throws org.scribble.main.ScribbleRuntimeException, IOException, ClassNotFoundException {
		org.scribble.net.ScribMessage m = super.readScribMessage(Proto1.S);
		arg1.val = (java.lang.Integer) m.payload[0];
		return new Proto1_C_1(this.se, true);
	}

	public Proto1_C_1 async(S role, _3 op, org.scribble.net.Buf<Proto1_C_3_Future> arg) throws org.scribble.main.ScribbleRuntimeException {
		arg.val = new Proto1_C_3_Future(super.getFuture(Proto1.S));
		return new Proto1_C_1(this.se, true);
	}

	public boolean isDone() {
		return super.isDone(Proto1.S);
	}

	@SuppressWarnings("unchecked")
	public Proto1_C_1 async(S role, _3 op) throws org.scribble.main.ScribbleRuntimeException {
		return async(Proto1.S, op, (org.scribble.net.Buf<Proto1_C_3_Future>) this.se.gc);
	}
}