package exercise.voting.EProtocol.EVoting.channels.S;

import java.io.IOException;
import exercise.voting.EProtocol.EVoting.*;
import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.ops.*;
import exercise.voting.EProtocol.EVoting.channels.S.ioifaces.*;

public final class EVoting_S_4 extends org.scribble.net.scribsock.SendSocket<EVoting, S> implements Select_S_V$Result$Int<EndSocket> {
	public static final EVoting_S_4 cast = null;

	protected EVoting_S_4(org.scribble.net.session.SessionEndpoint<EVoting, S> se, boolean dummy) {
		super(se);
	}

	public exercise.voting.EProtocol.EVoting.channels.S.EndSocket send(V role, Result op, java.lang.Integer arg0) throws org.scribble.main.ScribbleRuntimeException, IOException {
		super.writeScribMessage(role, EVoting.Result, arg0);

		this.se.setCompleted();
		return new EndSocket(this.se, true);
	}
}