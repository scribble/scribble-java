package exercise.calculator.EProtocol.Calc.channels.S;

import java.io.IOException;
import exercise.calculator.EProtocol.Calc.*;
import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.channels.S.ioifaces.*;

public final class Calc_S_1 extends org.scribble.net.scribsock.BranchSocket<Calc, S> implements Branch_S_C_multiply_int_int__C_quit__C_sum_int_int<Calc_S_3, Calc_S_4, Calc_S_2> {
	public static final Calc_S_1 cast = null;

	protected Calc_S_1(org.scribble.net.session.SessionEndpoint<Calc, S> se, boolean dummy) {
		super(se);
	}

	public Calc_S_1(org.scribble.net.session.SessionEndpoint<Calc, S> se) throws org.scribble.main.ScribbleRuntimeException {
		super(se);
		se.init();
	}

	public Calc_S_1_Cases branch(C role) throws org.scribble.main.ScribbleRuntimeException, IOException, ClassNotFoundException {
		org.scribble.net.ScribMessage m = super.readScribMessage(Calc.C);
		Branch_S_C_multiply_int_int__C_quit__C_sum_int_int_Enum openum;
		if (m.op.equals(Calc.sum)) {
			openum = Branch_S_C_multiply_int_int__C_quit__C_sum_int_int_Enum.sum;
		}
		else if (m.op.equals(Calc.multiply)) {
			openum = Branch_S_C_multiply_int_int__C_quit__C_sum_int_int_Enum.multiply;
		}
		else if (m.op.equals(Calc.quit)) {
			openum = Branch_S_C_multiply_int_int__C_quit__C_sum_int_int_Enum.quit;
		}
		else {
			throw new RuntimeException("Won't get here: " + m.op);
		}
		return new Calc_S_1_Cases(this.se, true, openum, m);
	}

	public void branch(C role, Calc_S_1_Handler branch) throws org.scribble.main.ScribbleRuntimeException, IOException, ClassNotFoundException {
		org.scribble.net.ScribMessage m = super.readScribMessage(Calc.C);
		if (m.op.equals(Calc.sum)) {
			branch.receive(new Calc_S_2(this.se, true), Calc.sum, new org.scribble.net.Buf<>((java.lang.Integer) m.payload[0]), new org.scribble.net.Buf<>((java.lang.Integer) m.payload[1]));
		}
		else
		if (m.op.equals(Calc.multiply)) {
			branch.receive(new Calc_S_3(this.se, true), Calc.multiply, new org.scribble.net.Buf<>((java.lang.Integer) m.payload[0]), new org.scribble.net.Buf<>((java.lang.Integer) m.payload[1]));
		}
		else
		if (m.op.equals(Calc.quit)) {
			branch.receive(new Calc_S_4(this.se, true), Calc.quit);
		}
		else {
			throw new RuntimeException("Won't get here: " + m.op);
		}
	}
}