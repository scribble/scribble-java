package exercise.voting.EProtocol.EVoting.channels.S;

import exercise.voting.EProtocol.EVoting.*;
import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.ops.*;
import exercise.voting.EProtocol.EVoting.channels.S.ioifaces.*;

public final class EVoting_S_3_Cases extends org.scribble.net.scribsock.CaseSocket<EVoting, S> implements Case_S_V$Yes$String$_V$No$String<EVoting_S_4, EVoting_S_4> {
	public static final EVoting_S_3_Cases cast = null;
	public final EVoting_S_3.Branch_S_V$Yes$String$_V$No$String_Enum op;
	private final org.scribble.net.ScribMessage m;

	protected EVoting_S_3_Cases(org.scribble.net.session.SessionEndpoint<EVoting, S> se, boolean dummy, EVoting_S_3.Branch_S_V$Yes$String$_V$No$String_Enum op, org.scribble.net.ScribMessage m) {
		super(se);
		this.op = op;
		this.m = m;
	}

	public EVoting_S_4 receive(V role, Yes op, org.scribble.net.Buf<? super java.lang.String> arg1) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		super.use();
		if (!this.m.op.equals(EVoting.Yes)) {
			throw new org.scribble.main.ScribbleRuntimeException("Wrong branch, received: " + this.m.op);
		}
		arg1.val = (java.lang.String) m.payload[0];
		return new EVoting_S_4(this.se, true);
	}

	public EVoting_S_4 receive(Yes op, org.scribble.net.Buf<? super java.lang.String> arg1) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		return receive(EVoting.V, op, arg1);
	}

	@SuppressWarnings("unchecked")
	public EVoting_S_4 receive(Yes op) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		return receive(op, (org.scribble.net.Buf<java.lang.String>) this.se.gc);
	}

	public EVoting_S_4 receive(V role, No op, org.scribble.net.Buf<? super java.lang.String> arg1) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		super.use();
		if (!this.m.op.equals(EVoting.No)) {
			throw new org.scribble.main.ScribbleRuntimeException("Wrong branch, received: " + this.m.op);
		}
		arg1.val = (java.lang.String) m.payload[0];
		return new EVoting_S_4(this.se, true);
	}

	public EVoting_S_4 receive(No op, org.scribble.net.Buf<? super java.lang.String> arg1) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		return receive(EVoting.V, op, arg1);
	}

	@SuppressWarnings("unchecked")
	public EVoting_S_4 receive(No op) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		return receive(op, (org.scribble.net.Buf<java.lang.String>) this.se.gc);
	}

	@Override
	public Branch_S_V$Yes$String$_V$No$String.Branch_S_V$Yes$String$_V$No$String_Enum getOp() {
		return this.op;
	}
}