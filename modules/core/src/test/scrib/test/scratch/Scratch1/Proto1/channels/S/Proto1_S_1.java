package test.scratch.Scratch1.Proto1.channels.S;

import java.io.IOException;
import test.scratch.Scratch1.Proto1.*;
import test.scratch.Scratch1.Proto1.roles.*;
import test.scratch.Scratch1.Proto1.ops.*;

public final class Proto1_S_1 extends org.scribble.net.scribsock.ReceiveSocket<Proto1, S> {

	protected Proto1_S_1(org.scribble.net.session.SessionEndpoint<Proto1, S> se, boolean dummy) {
		super(se);
	}

	public Proto1_S_1(org.scribble.net.session.SessionEndpoint<Proto1, S> se) throws org.scribble.main.ScribbleRuntimeException {
		super(se);
		se.init();
	}

	public Proto1_S_2 receive(C role, _1 op) throws org.scribble.main.ScribbleRuntimeException, IOException, ClassNotFoundException {
		super.readScribMessage(Proto1.C);
		return new Proto1_S_2(this.se, true);
	}

	public Proto1_S_2 async(C role, _1 op, org.scribble.net.Buf<Proto1_S_1_Future> arg) throws org.scribble.main.ScribbleRuntimeException {
		arg.val = new Proto1_S_1_Future(super.getFuture(Proto1.C));
		return new Proto1_S_2(this.se, true);
	}

	public boolean isDone() {
		return super.isDone(Proto1.C);
	}

	@SuppressWarnings("unchecked")
	public Proto1_S_2 async(C role, _1 op) throws org.scribble.main.ScribbleRuntimeException {
		return async(Proto1.C, op, (org.scribble.net.Buf<Proto1_S_1_Future>) this.se.gc);
	}
}