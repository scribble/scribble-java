package exercise.calculator.EProtocol.Calc.channels.C;

import exercise.calculator.EProtocol.Calc.*;
import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.ops.*;
import exercise.calculator.EProtocol.Calc.channels.C.ioifaces.*;

public final class Calc_C_4 extends org.scribble.net.scribsock.ReceiveSocket<Calc, C> implements Receive_C_S_terminate<EndSocket> {
	public static final Calc_C_4 cast = null;

	protected Calc_C_4(org.scribble.net.session.SessionEndpoint<Calc, C> se, boolean dummy) {
		super(se);
	}

	public exercise.calculator.EProtocol.Calc.channels.C.EndSocket receive(S role, terminate op) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		super.readScribMessage(Calc.S);
		this.se.setCompleted();
		return new EndSocket(this.se, true);
	}

	public exercise.calculator.EProtocol.Calc.channels.C.EndSocket async(S role, terminate op, org.scribble.net.Buf<Calc_C_4_Future> arg) throws org.scribble.main.ScribbleRuntimeException {
		arg.val = new Calc_C_4_Future(super.getFuture(Calc.S));
		this.se.setCompleted();
		return new EndSocket(this.se, true);
	}

	public boolean isDone() {
		return super.isDone(Calc.S);
	}

	@SuppressWarnings("unchecked")
	public exercise.calculator.EProtocol.Calc.channels.C.EndSocket async(S role, terminate op) throws org.scribble.main.ScribbleRuntimeException {
		return async(Calc.S, op, (org.scribble.net.Buf<Calc_C_4_Future>) this.se.gc);
	}
}