package test.scratch.Scratch1.Proto1.channels.S;

import java.io.IOException;
import test.scratch.Scratch1.Proto1.*;
import test.scratch.Scratch1.Proto1.roles.*;
import test.scratch.Scratch1.Proto1.ops.*;

public final class Proto1_S_2_Cases extends org.scribble.net.scribsock.CaseSocket<Proto1, S> {
	public final Proto1_S_2.Proto1_S_2_Enum op;
	private final org.scribble.net.ScribMessage m;

	protected Proto1_S_2_Cases(org.scribble.net.session.SessionEndpoint<Proto1, S> se, boolean dummy, Proto1_S_2.Proto1_S_2_Enum op, org.scribble.net.ScribMessage m) {
		super(se);
		this.op = op;
		this.m = m;
	}

	public Proto1_S_3 receive(_2 op, org.scribble.net.Buf<? super java.lang.Integer> arg1) throws org.scribble.main.ScribbleRuntimeException, IOException, ClassNotFoundException {
		super.use();
		if (!this.m.op.equals(Proto1._2)) {
			throw new org.scribble.main.ScribbleRuntimeException("Wrong branch, received: " + this.m.op);
		}
		arg1.val = (java.lang.Integer) m.payload[0];
		return new Proto1_S_3(this.se, true);
	}

	@SuppressWarnings("unchecked")
	public Proto1_S_3 receive(_2 op) throws org.scribble.main.ScribbleRuntimeException, IOException, ClassNotFoundException {
		return receive(op, (org.scribble.net.Buf<java.lang.Integer>) this.se.gc);
	}

	public org.scribble.net.scribsock.EndSocket<Proto1, S> receive(_4 op) throws org.scribble.main.ScribbleRuntimeException, IOException, ClassNotFoundException {
		super.use();
		if (!this.m.op.equals(Proto1._4)) {
			throw new org.scribble.main.ScribbleRuntimeException("Wrong branch, received: " + this.m.op);
		}
		this.se.setCompleted();
		return new org.scribble.net.scribsock.EndSocket<>(this.se, true);
	}
}