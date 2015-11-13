package exercise.calculator.EProtocol.Calc.channels.C;

import exercise.calculator.EProtocol.Calc.*;
import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.channels.C.ioifaces.*;

public final class EndSocket extends org.scribble.net.scribsock.EndSocket<Calc, C> implements Succ_In_S_terminate {
	public static final EndSocket cast = null;

	protected EndSocket(org.scribble.net.session.SessionEndpoint<Calc, C> se, boolean dummy) {
		super(se);
	}
}