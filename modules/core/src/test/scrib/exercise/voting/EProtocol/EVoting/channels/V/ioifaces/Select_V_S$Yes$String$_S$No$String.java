package exercise.voting.EProtocol.EVoting.channels.V.ioifaces;

import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.channels.V.*;

public interface Select_V_S$Yes$String$_S$No$String<__Succ1 extends Succ_Out_S$Yes$String, __Succ2 extends Succ_Out_S$No$String> extends Out_S$Yes$String<__Succ1>, Out_S$No$String<__Succ2>, Succ_In_S$Ok$String {
	public static final Select_V_S$Yes$String$_S$No$String<?, ?> cast = null;

	@Override
	default Select_V_S$Yes$String$_S$No$String<?, ?> to(Select_V_S$Yes$String$_S$No$String<?, ?> cast) {
		return (Select_V_S$Yes$String$_S$No$String<?, ?>) this; 
	}

	default EVoting_V_3 to(EVoting_V_3 cast) {
		return (EVoting_V_3) this;
	}
}