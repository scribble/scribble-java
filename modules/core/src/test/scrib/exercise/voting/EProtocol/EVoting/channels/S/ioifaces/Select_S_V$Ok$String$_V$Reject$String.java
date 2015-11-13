package exercise.voting.EProtocol.EVoting.channels.S.ioifaces;

import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.channels.S.*;

public interface Select_S_V$Ok$String$_V$Reject$String<__Succ1 extends Succ_Out_V$Ok$String, __Succ2 extends Succ_Out_V$Reject$String> extends Out_V$Ok$String<__Succ1>, Out_V$Reject$String<__Succ2>, Succ_In_V$Authenticate$String {
	public static final Select_S_V$Ok$String$_V$Reject$String<?, ?> cast = null;

	@Override
	default Select_S_V$Ok$String$_V$Reject$String<?, ?> to(Select_S_V$Ok$String$_V$Reject$String<?, ?> cast) {
		return (Select_S_V$Ok$String$_V$Reject$String<?, ?>) this; 
	}

	default EVoting_S_2 to(EVoting_S_2 cast) {
		return (EVoting_S_2) this;
	}
}