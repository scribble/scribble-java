package exercise.voting.EProtocol.EVoting.channels.V;

import exercise.voting.EProtocol.EVoting.*;
import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.channels.V.ioifaces.*;

public final class EndSocket extends org.scribble.net.scribsock.EndSocket<EVoting, V> implements Succ_In_S$Reject$String, Succ_In_S$Result$Int {
	public static final EndSocket cast = null;

	protected EndSocket(org.scribble.net.session.SessionEndpoint<EVoting, V> se, boolean dummy) {
		super(se);
	}
}