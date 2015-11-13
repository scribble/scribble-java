package exercise.voting.EProtocol.EVoting.channels.S.ioifaces;

import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.channels.S.*;

public interface Select_S_V$Result$Int<__Succ1 extends Succ_Out_V$Result$Int> extends Out_V$Result$Int<__Succ1>, Succ_In_V$Yes$String, Succ_In_V$No$String {
	public static final Select_S_V$Result$Int<?> cast = null;

	@Override
	default Select_S_V$Result$Int<?> to(Select_S_V$Result$Int<?> cast) {
		return (Select_S_V$Result$Int<?>) this; 
	}

	default EVoting_S_4 to(EVoting_S_4 cast) {
		return (EVoting_S_4) this;
	}
}