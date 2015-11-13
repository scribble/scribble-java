package exercise.voting.EProtocol.EVoting.channels.S;

import exercise.voting.EProtocol.EVoting.*;
import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.channels.S.ioifaces.*;

public final class EndSocket extends org.scribble.net.scribsock.EndSocket<EVoting, S> implements Succ_Out_V$Reject$String, Succ_Out_V$Result$Int {
	public static final EndSocket cast = null;

	protected EndSocket(org.scribble.net.session.SessionEndpoint<EVoting, S> se, boolean dummy) {
		super(se);
	}
}