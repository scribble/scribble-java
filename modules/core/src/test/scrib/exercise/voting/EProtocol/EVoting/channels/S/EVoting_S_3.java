package exercise.voting.EProtocol.EVoting.channels.S;

import java.io.IOException;
import exercise.voting.EProtocol.EVoting.*;
import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.channels.S.ioifaces.*;

public final class EVoting_S_3 extends org.scribble.net.scribsock.BranchSocket<EVoting, S> implements Branch_S_V$Yes$String$_V$No$String<EVoting_S_4, EVoting_S_4> {
	public static final EVoting_S_3 cast = null;

	protected EVoting_S_3(org.scribble.net.session.SessionEndpoint<EVoting, S> se, boolean dummy) {
		super(se);
	}

	public EVoting_S_3_Cases branch(V role) throws org.scribble.main.ScribbleRuntimeException, IOException, ClassNotFoundException {
		org.scribble.net.ScribMessage m = super.readScribMessage(EVoting.V);
		Branch_S_V$Yes$String$_V$No$String_Enum openum;
		if (m.op.equals(EVoting.Yes)) {
			openum = Branch_S_V$Yes$String$_V$No$String_Enum.Yes;
		}
		else if (m.op.equals(EVoting.No)) {
			openum = Branch_S_V$Yes$String$_V$No$String_Enum.No;
		}
		else {
			throw new RuntimeException("Won't get here: " + m.op);
		}
		return new EVoting_S_3_Cases(this.se, true, openum, m);
	}

	public void branch(V role, EVoting_S_3_Handler branch) throws org.scribble.main.ScribbleRuntimeException, IOException, ClassNotFoundException {
		org.scribble.net.ScribMessage m = super.readScribMessage(EVoting.V);
		if (m.op.equals(EVoting.Yes)) {
			branch.receive(new EVoting_S_4(this.se, true), EVoting.Yes, new org.scribble.net.Buf<>((java.lang.String) m.payload[0]));
		}
		else
		if (m.op.equals(EVoting.No)) {
			branch.receive(new EVoting_S_4(this.se, true), EVoting.No, new org.scribble.net.Buf<>((java.lang.String) m.payload[0]));
		}
		else {
			throw new RuntimeException("Won't get here: " + m.op);
		}
	}
}