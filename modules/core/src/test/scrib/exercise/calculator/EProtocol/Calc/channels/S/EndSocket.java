package exercise.calculator.EProtocol.Calc.channels.S;

import exercise.calculator.EProtocol.Calc.*;
import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.channels.S.ioifaces.*;

public final class EndSocket extends org.scribble.net.scribsock.EndSocket<Calc, S> implements Succ_Out_C_terminate {
	public static final EndSocket cast = null;

	protected EndSocket(org.scribble.net.session.SessionEndpoint<Calc, S> se, boolean dummy) {
		super(se);
	}
}