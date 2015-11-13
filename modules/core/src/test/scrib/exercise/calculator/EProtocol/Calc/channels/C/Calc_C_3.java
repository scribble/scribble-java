package exercise.calculator.EProtocol.Calc.channels.C;

import exercise.calculator.EProtocol.Calc.*;
import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.ops.*;
import exercise.calculator.EProtocol.Calc.channels.C.ioifaces.*;

public final class Calc_C_3 extends org.scribble.net.scribsock.ReceiveSocket<Calc, C> implements Receive_C_S_result_int<Calc_C_1> {
	public static final Calc_C_3 cast = null;

	protected Calc_C_3(org.scribble.net.session.SessionEndpoint<Calc, C> se, boolean dummy) {
		super(se);
	}

	public Calc_C_1 receive(S role, result op, org.scribble.net.Buf<? super java.lang.Integer> arg1) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		org.scribble.net.ScribMessage m = super.readScribMessage(Calc.S);
		arg1.val = (java.lang.Integer) m.payload[0];
		return new Calc_C_1(this.se, true);
	}

	public Calc_C_1 async(S role, result op, org.scribble.net.Buf<Calc_C_3_Future> arg) throws org.scribble.main.ScribbleRuntimeException {
		arg.val = new Calc_C_3_Future(super.getFuture(Calc.S));
		return new Calc_C_1(this.se, true);
	}

	public boolean isDone() {
		return super.isDone(Calc.S);
	}

	@SuppressWarnings("unchecked")
	public Calc_C_1 async(S role, result op) throws org.scribble.main.ScribbleRuntimeException {
		return async(Calc.S, op, (org.scribble.net.Buf<Calc_C_3_Future>) this.se.gc);
	}
}