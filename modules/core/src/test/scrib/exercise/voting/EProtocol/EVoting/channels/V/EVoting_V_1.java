package exercise.voting.EProtocol.EVoting.channels.V;

import java.io.IOException;
import exercise.voting.EProtocol.EVoting.*;
import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.ops.*;
import exercise.voting.EProtocol.EVoting.channels.V.ioifaces.*;

public final class EVoting_V_1 extends org.scribble.net.scribsock.SendSocket<EVoting, V> implements Select_V_S$Authenticate$String<EVoting_V_2> {
	public static final EVoting_V_1 cast = null;

	protected EVoting_V_1(org.scribble.net.session.SessionEndpoint<EVoting, V> se, boolean dummy) {
		super(se);
	}

	public EVoting_V_1(org.scribble.net.session.SessionEndpoint<EVoting, V> se) throws org.scribble.main.ScribbleRuntimeException {
		super(se);
		se.init();
	}

	public EVoting_V_2 send(S role, Authenticate op, java.lang.String arg0) throws org.scribble.main.ScribbleRuntimeException, IOException {
		super.writeScribMessage(role, EVoting.Authenticate, arg0);

		return new EVoting_V_2(this.se, true);
	}
}