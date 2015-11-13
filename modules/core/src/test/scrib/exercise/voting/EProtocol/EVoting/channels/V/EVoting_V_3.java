package exercise.voting.EProtocol.EVoting.channels.V;

import java.io.IOException;
import exercise.voting.EProtocol.EVoting.*;
import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.ops.*;
import exercise.voting.EProtocol.EVoting.channels.V.ioifaces.*;

public final class EVoting_V_3 extends org.scribble.net.scribsock.SendSocket<EVoting, V> implements Select_V_S$Yes$String$_S$No$String<EVoting_V_4, EVoting_V_4> {
	public static final EVoting_V_3 cast = null;

	protected EVoting_V_3(org.scribble.net.session.SessionEndpoint<EVoting, V> se, boolean dummy) {
		super(se);
	}

	public EVoting_V_4 send(S role, Yes op, java.lang.String arg0) throws org.scribble.main.ScribbleRuntimeException, IOException {
		super.writeScribMessage(role, EVoting.Yes, arg0);

		return new EVoting_V_4(this.se, true);
	}

	public EVoting_V_4 send(S role, No op, java.lang.String arg0) throws org.scribble.main.ScribbleRuntimeException, IOException {
		super.writeScribMessage(role, EVoting.No, arg0);

		return new EVoting_V_4(this.se, true);
	}
}