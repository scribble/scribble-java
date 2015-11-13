package exercise.calculator.EProtocol.Calc.channels.S;

import java.io.IOException;
import exercise.calculator.EProtocol.Calc.*;
import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.ops.*;
import exercise.calculator.EProtocol.Calc.channels.S.ioifaces.*;

public final class Calc_S_4 extends org.scribble.net.scribsock.SendSocket<Calc, S> implements Select_S_C_terminate<EndSocket> {
	public static final Calc_S_4 cast = null;

	protected Calc_S_4(org.scribble.net.session.SessionEndpoint<Calc, S> se, boolean dummy) {
		super(se);
	}

	public exercise.calculator.EProtocol.Calc.channels.S.EndSocket send(C role, terminate op) throws org.scribble.main.ScribbleRuntimeException, IOException {
		super.writeScribMessage(role, Calc.terminate);

		this.se.setCompleted();
		return new EndSocket(this.se, true);
	}
}