package exercise.voting.EProtocol.EVoting.channels.S.ioifaces;

import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.channels.S.*;

public interface Branch_S_V$Yes$String$_V$No$String<__Succ1 extends Succ_In_V$Yes$String, __Succ2 extends Succ_In_V$No$String> extends Succ_Out_V$Ok$String {
	public static final Branch_S_V$Yes$String$_V$No$String<?, ?> cast = null;

	abstract Case_S_V$Yes$String$_V$No$String<__Succ1, __Succ2> branch(V role) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException;

	@Override
	default Branch_S_V$Yes$String$_V$No$String<?, ?> to(Branch_S_V$Yes$String$_V$No$String<?, ?> cast) {
		return (Branch_S_V$Yes$String$_V$No$String<?, ?>) this; 
	}

	default EVoting_S_3 to(EVoting_S_3 cast) {
		return (EVoting_S_3) this;
	}

public enum Branch_S_V$Yes$String$_V$No$String_Enum implements org.scribble.net.session.OpEnum {
	Yes, No
}
}