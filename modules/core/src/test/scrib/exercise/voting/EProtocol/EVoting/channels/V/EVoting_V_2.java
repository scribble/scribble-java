package exercise.voting.EProtocol.EVoting.channels.V;

import java.io.IOException;
import exercise.voting.EProtocol.EVoting.*;
import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.channels.V.ioifaces.*;

public final class EVoting_V_2 extends org.scribble.net.scribsock.BranchSocket<EVoting, V> implements Branch_V_S$Ok$String$_S$Reject$String<EVoting_V_3, EndSocket> {
	public static final EVoting_V_2 cast = null;

	protected EVoting_V_2(org.scribble.net.session.SessionEndpoint<EVoting, V> se, boolean dummy) {
		super(se);
	}

	public EVoting_V_2_Cases branch(S role) throws org.scribble.main.ScribbleRuntimeException, IOException, ClassNotFoundException {
		org.scribble.net.ScribMessage m = super.readScribMessage(EVoting.S);
		Branch_V_S$Ok$String$_S$Reject$String_Enum openum;
		if (m.op.equals(EVoting.Ok)) {
			openum = Branch_V_S$Ok$String$_S$Reject$String_Enum.Ok;
		}
		else if (m.op.equals(EVoting.Reject)) {
			openum = Branch_V_S$Ok$String$_S$Reject$String_Enum.Reject;
		}
		else {
			throw new RuntimeException("Won't get here: " + m.op);
		}
		return new EVoting_V_2_Cases(this.se, true, openum, m);
	}

	public void branch(S role, EVoting_V_2_Handler branch) throws org.scribble.main.ScribbleRuntimeException, IOException, ClassNotFoundException {
		org.scribble.net.ScribMessage m = super.readScribMessage(EVoting.S);
		if (m.op.equals(EVoting.Ok)) {
			branch.receive(new EVoting_V_3(this.se, true), EVoting.Ok, new org.scribble.net.Buf<>((java.lang.String) m.payload[0]));
		}
		else
		if (m.op.equals(EVoting.Reject)) {
			this.se.setCompleted();
			branch.receive(new EndSocket(this.se, true), EVoting.Reject, new org.scribble.net.Buf<>((java.lang.String) m.payload[0]));
		}
		else {
			throw new RuntimeException("Won't get here: " + m.op);
		}
	}
}