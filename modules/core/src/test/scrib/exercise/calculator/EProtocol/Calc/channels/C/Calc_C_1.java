package exercise.calculator.EProtocol.Calc.channels.C;

import java.io.IOException;
import exercise.calculator.EProtocol.Calc.*;
import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.ops.*;
import exercise.calculator.EProtocol.Calc.channels.C.ioifaces.*;

public final class Calc_C_1 extends org.scribble.net.scribsock.SendSocket<Calc, C> implements Select_C_S_multiply_int_int__S_quit__S_sum_int_int<Calc_C_3, Calc_C_4, Calc_C_2> {
	public static final Calc_C_1 cast = null;

	protected Calc_C_1(org.scribble.net.session.SessionEndpoint<Calc, C> se, boolean dummy) {
		super(se);
	}

	public Calc_C_1(org.scribble.net.session.SessionEndpoint<Calc, C> se) throws org.scribble.main.ScribbleRuntimeException {
		super(se);
		se.init();
	}

	public Calc_C_2 send(S role, sum op, java.lang.Integer arg0, java.lang.Integer arg1) throws org.scribble.main.ScribbleRuntimeException, IOException {
		super.writeScribMessage(role, Calc.sum, arg0, arg1);

		return new Calc_C_2(this.se, true);
	}

	public Calc_C_3 send(S role, multiply op, java.lang.Integer arg0, java.lang.Integer arg1) throws org.scribble.main.ScribbleRuntimeException, IOException {
		super.writeScribMessage(role, Calc.multiply, arg0, arg1);

		return new Calc_C_3(this.se, true);
	}

	public Calc_C_4 send(S role, quit op) throws org.scribble.main.ScribbleRuntimeException, IOException {
		super.writeScribMessage(role, Calc.quit);

		return new Calc_C_4(this.se, true);
	}
}