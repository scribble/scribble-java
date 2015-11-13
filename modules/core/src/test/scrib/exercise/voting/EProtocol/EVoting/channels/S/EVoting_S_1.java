package exercise.voting.EProtocol.EVoting.channels.S;

import exercise.voting.EProtocol.EVoting.*;
import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.ops.*;
import exercise.voting.EProtocol.EVoting.channels.S.ioifaces.*;

public final class EVoting_S_1 extends org.scribble.net.scribsock.ReceiveSocket<EVoting, S> implements Receive_S_V$Authenticate$String<EVoting_S_2> {
	public static final EVoting_S_1 cast = null;

	protected EVoting_S_1(org.scribble.net.session.SessionEndpoint<EVoting, S> se, boolean dummy) {
		super(se);
	}

	public EVoting_S_1(org.scribble.net.session.SessionEndpoint<EVoting, S> se) throws org.scribble.main.ScribbleRuntimeException {
		super(se);
		se.init();
	}

	public EVoting_S_2 receive(V role, Authenticate op, org.scribble.net.Buf<? super java.lang.String> arg1) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		org.scribble.net.ScribMessage m = super.readScribMessage(EVoting.V);
		arg1.val = (java.lang.String) m.payload[0];
		return new EVoting_S_2(this.se, true);
	}

	public EVoting_S_2 async(V role, Authenticate op, org.scribble.net.Buf<EVoting_S_1_Future> arg) throws org.scribble.main.ScribbleRuntimeException {
		arg.val = new EVoting_S_1_Future(super.getFuture(EVoting.V));
		return new EVoting_S_2(this.se, true);
	}

	public boolean isDone() {
		return super.isDone(EVoting.V);
	}

	@SuppressWarnings("unchecked")
	public EVoting_S_2 async(V role, Authenticate op) throws org.scribble.main.ScribbleRuntimeException {
		return async(EVoting.V, op, (org.scribble.net.Buf<EVoting_S_1_Future>) this.se.gc);
	}
}