package exercise.voting.EProtocol.EVoting.channels.V;

import exercise.voting.EProtocol.EVoting.*;
import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.ops.*;
import exercise.voting.EProtocol.EVoting.channels.V.ioifaces.*;

public final class EVoting_V_2_Cases extends org.scribble.net.scribsock.CaseSocket<EVoting, V> implements Case_V_S$Ok$String$_S$Reject$String<EVoting_V_3, EndSocket> {
	public static final EVoting_V_2_Cases cast = null;
	public final EVoting_V_2.Branch_V_S$Ok$String$_S$Reject$String_Enum op;
	private final org.scribble.net.ScribMessage m;

	protected EVoting_V_2_Cases(org.scribble.net.session.SessionEndpoint<EVoting, V> se, boolean dummy, EVoting_V_2.Branch_V_S$Ok$String$_S$Reject$String_Enum op, org.scribble.net.ScribMessage m) {
		super(se);
		this.op = op;
		this.m = m;
	}

	public EVoting_V_3 receive(S role, Ok op, org.scribble.net.Buf<? super java.lang.String> arg1) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		super.use();
		if (!this.m.op.equals(EVoting.Ok)) {
			throw new org.scribble.main.ScribbleRuntimeException("Wrong branch, received: " + this.m.op);
		}
		arg1.val = (java.lang.String) m.payload[0];
		return new EVoting_V_3(this.se, true);
	}

	public EVoting_V_3 receive(Ok op, org.scribble.net.Buf<? super java.lang.String> arg1) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		return receive(EVoting.S, op, arg1);
	}

	@SuppressWarnings("unchecked")
	public EVoting_V_3 receive(Ok op) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		return receive(op, (org.scribble.net.Buf<java.lang.String>) this.se.gc);
	}

	public exercise.voting.EProtocol.EVoting.channels.V.EndSocket receive(S role, Reject op, org.scribble.net.Buf<? super java.lang.String> arg1) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		super.use();
		if (!this.m.op.equals(EVoting.Reject)) {
			throw new org.scribble.main.ScribbleRuntimeException("Wrong branch, received: " + this.m.op);
		}
		arg1.val = (java.lang.String) m.payload[0];
		this.se.setCompleted();
		return new EndSocket(this.se, true);
	}

	public exercise.voting.EProtocol.EVoting.channels.V.EndSocket receive(Reject op, org.scribble.net.Buf<? super java.lang.String> arg1) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		return receive(EVoting.S, op, arg1);
	}

	@SuppressWarnings("unchecked")
	public exercise.voting.EProtocol.EVoting.channels.V.EndSocket receive(Reject op) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		return receive(op, (org.scribble.net.Buf<java.lang.String>) this.se.gc);
	}

	@Override
	public Branch_V_S$Ok$String$_S$Reject$String.Branch_V_S$Ok$String$_S$Reject$String_Enum getOp() {
		return this.op;
	}
}