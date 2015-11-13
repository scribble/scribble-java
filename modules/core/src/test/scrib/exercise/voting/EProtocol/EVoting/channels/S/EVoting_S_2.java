package exercise.voting.EProtocol.EVoting.channels.S;

import java.io.IOException;
import exercise.voting.EProtocol.EVoting.*;
import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.ops.*;
import exercise.voting.EProtocol.EVoting.channels.S.ioifaces.*;

public final class EVoting_S_2 extends org.scribble.net.scribsock.SendSocket<EVoting, S> implements Select_S_V$Ok$String$_V$Reject$String<EVoting_S_3, EndSocket> {
	public static final EVoting_S_2 cast = null;

	protected EVoting_S_2(org.scribble.net.session.SessionEndpoint<EVoting, S> se, boolean dummy) {
		super(se);
	}

	public EVoting_S_3 send(V role, Ok op, java.lang.String arg0) throws org.scribble.main.ScribbleRuntimeException, IOException {
		super.writeScribMessage(role, EVoting.Ok, arg0);

		return new EVoting_S_3(this.se, true);
	}

	public exercise.voting.EProtocol.EVoting.channels.S.EndSocket send(V role, Reject op, java.lang.String arg0) throws org.scribble.main.ScribbleRuntimeException, IOException {
		super.writeScribMessage(role, EVoting.Reject, arg0);

		this.se.setCompleted();
		return new EndSocket(this.se, true);
	}
}