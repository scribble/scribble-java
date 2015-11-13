package exercise.voting.EProtocol.EVoting.channels.V.ioifaces;

import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.channels.V.*;

public interface Branch_V_S$Ok$String$_S$Reject$String<__Succ1 extends Succ_In_S$Ok$String, __Succ2 extends Succ_In_S$Reject$String> extends Succ_Out_S$Authenticate$String {
	public static final Branch_V_S$Ok$String$_S$Reject$String<?, ?> cast = null;

	abstract Case_V_S$Ok$String$_S$Reject$String<__Succ1, __Succ2> branch(S role) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException;

	@Override
	default Branch_V_S$Ok$String$_S$Reject$String<?, ?> to(Branch_V_S$Ok$String$_S$Reject$String<?, ?> cast) {
		return (Branch_V_S$Ok$String$_S$Reject$String<?, ?>) this; 
	}

	default EVoting_V_2 to(EVoting_V_2 cast) {
		return (EVoting_V_2) this;
	}

public enum Branch_V_S$Ok$String$_S$Reject$String_Enum implements org.scribble.net.session.OpEnum {
	Ok, Reject
}
}