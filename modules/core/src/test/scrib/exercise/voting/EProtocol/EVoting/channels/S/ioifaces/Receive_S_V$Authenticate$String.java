package exercise.voting.EProtocol.EVoting.channels.S.ioifaces;

import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.ops.*;
import exercise.voting.EProtocol.EVoting.channels.S.*;

public interface Receive_S_V$Authenticate$String<__Succ1 extends Succ_In_V$Authenticate$String> extends In_V$Authenticate$String<__Succ1> {
	public static final Receive_S_V$Authenticate$String<?> cast = null;

	abstract public __Succ1 async(V role, Authenticate op) throws org.scribble.main.ScribbleRuntimeException;

	default EVoting_S_1 to(EVoting_S_1 cast) {
		return (EVoting_S_1) this;
	}
}