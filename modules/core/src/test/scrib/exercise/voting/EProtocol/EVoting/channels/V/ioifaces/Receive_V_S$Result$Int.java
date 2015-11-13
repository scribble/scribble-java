package exercise.voting.EProtocol.EVoting.channels.V.ioifaces;

import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.ops.*;
import exercise.voting.EProtocol.EVoting.channels.V.*;

public interface Receive_V_S$Result$Int<__Succ1 extends Succ_In_S$Result$Int> extends In_S$Result$Int<__Succ1>, Succ_Out_S$Yes$String, Succ_Out_S$No$String {
	public static final Receive_V_S$Result$Int<?> cast = null;

	abstract public exercise.voting.EProtocol.EVoting.channels.V.EndSocket async(S role, Result op) throws org.scribble.main.ScribbleRuntimeException;

	@Override
	default Receive_V_S$Result$Int<?> to(Receive_V_S$Result$Int<?> cast) {
		return (Receive_V_S$Result$Int<?>) this; 
	}

	default EVoting_V_4 to(EVoting_V_4 cast) {
		return (EVoting_V_4) this;
	}
}