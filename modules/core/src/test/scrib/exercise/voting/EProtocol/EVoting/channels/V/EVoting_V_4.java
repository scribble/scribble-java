package exercise.voting.EProtocol.EVoting.channels.V;

import exercise.voting.EProtocol.EVoting.*;
import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.ops.*;
import exercise.voting.EProtocol.EVoting.channels.V.ioifaces.*;

public final class EVoting_V_4 extends org.scribble.net.scribsock.ReceiveSocket<EVoting, V> implements Receive_V_S$Result$Int<EndSocket> {
	public static final EVoting_V_4 cast = null;

	protected EVoting_V_4(org.scribble.net.session.SessionEndpoint<EVoting, V> se, boolean dummy) {
		super(se);
	}

	public exercise.voting.EProtocol.EVoting.channels.V.EndSocket receive(S role, Result op, org.scribble.net.Buf<? super java.lang.Integer> arg1) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		org.scribble.net.ScribMessage m = super.readScribMessage(EVoting.S);
		arg1.val = (java.lang.Integer) m.payload[0];
		this.se.setCompleted();
		return new EndSocket(this.se, true);
	}

	public exercise.voting.EProtocol.EVoting.channels.V.EndSocket async(S role, Result op, org.scribble.net.Buf<EVoting_V_4_Future> arg) throws org.scribble.main.ScribbleRuntimeException {
		arg.val = new EVoting_V_4_Future(super.getFuture(EVoting.S));
		this.se.setCompleted();
		return new EndSocket(this.se, true);
	}

	public boolean isDone() {
		return super.isDone(EVoting.S);
	}

	@SuppressWarnings("unchecked")
	public exercise.voting.EProtocol.EVoting.channels.V.EndSocket async(S role, Result op) throws org.scribble.main.ScribbleRuntimeException {
		return async(EVoting.S, op, (org.scribble.net.Buf<EVoting_V_4_Future>) this.se.gc);
	}
}