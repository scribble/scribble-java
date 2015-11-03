package test.scratch.Scratch1.Proto1.channels.S;

import java.io.IOException;
import test.scratch.Scratch1.Proto1.*;
import test.scratch.Scratch1.Proto1.roles.*;

public final class Proto1_S_2 extends org.scribble.net.scribsock.BranchSocket<Proto1, S> {
public enum Proto1_S_2_Enum implements org.scribble.net.session.OpEnum {
	_2, _4
}

	protected Proto1_S_2(org.scribble.net.session.SessionEndpoint<Proto1, S> se, boolean dummy) {
		super(se);
	}

	public Proto1_S_2_Cases branch(C role) throws org.scribble.main.ScribbleRuntimeException, IOException, ClassNotFoundException {
		org.scribble.net.ScribMessage m = super.readScribMessage(Proto1.C);
		Proto1_S_2_Enum openum;
		if (m.op.equals(Proto1._2)) {
			openum = Proto1_S_2_Enum._2;
		}
		else if (m.op.equals(Proto1._4)) {
			openum = Proto1_S_2_Enum._4;
		}
		else {
			throw new RuntimeException("Won't get here: " + m.op);
		}
		return new Proto1_S_2_Cases(this.se, true, openum, m);
	}

	public void branch(C role, Proto1_S_2_Handler branch) throws org.scribble.main.ScribbleRuntimeException, IOException, ClassNotFoundException {
		org.scribble.net.ScribMessage m = super.readScribMessage(Proto1.C);
		if (m.op.equals(Proto1._2)) {
			branch.receive(new Proto1_S_3(this.se, true), Proto1._2, new org.scribble.net.Buf<>((java.lang.Integer) m.payload[0]));
		}
		else
		if (m.op.equals(Proto1._4)) {
			this.se.setCompleted();
			branch.receive(new org.scribble.net.scribsock.EndSocket<>(this.se, true), Proto1._4);
		}
		else {
			throw new RuntimeException("Won't get here: " + m.op);
		}
	}
}