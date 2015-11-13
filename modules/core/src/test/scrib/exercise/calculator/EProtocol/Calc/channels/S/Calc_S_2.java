package exercise.calculator.EProtocol.Calc.channels.S;

import java.io.IOException;
import exercise.calculator.EProtocol.Calc.*;
import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.ops.*;
import exercise.calculator.EProtocol.Calc.channels.S.ioifaces.*;

public final class Calc_S_2 extends org.scribble.net.scribsock.SendSocket<Calc, S> implements Select_S_C_result_int<Calc_S_1> {
	public static final Calc_S_2 cast = null;

	protected Calc_S_2(org.scribble.net.session.SessionEndpoint<Calc, S> se, boolean dummy) {
		super(se);
	}

	public Calc_S_1 send(C role, result op, java.lang.Integer arg0) throws org.scribble.main.ScribbleRuntimeException, IOException {
		super.writeScribMessage(role, Calc.result, arg0);

		return new Calc_S_1(this.se, true);
	}
}