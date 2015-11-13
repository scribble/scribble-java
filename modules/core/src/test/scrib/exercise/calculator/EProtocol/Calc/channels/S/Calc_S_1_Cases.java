package exercise.calculator.EProtocol.Calc.channels.S;

import exercise.calculator.EProtocol.Calc.*;
import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.ops.*;
import exercise.calculator.EProtocol.Calc.channels.S.ioifaces.*;

public final class Calc_S_1_Cases extends org.scribble.net.scribsock.CaseSocket<Calc, S> implements Case_S_C_multiply_int_int__C_quit__C_sum_int_int<Calc_S_3, Calc_S_4, Calc_S_2> {
	public static final Calc_S_1_Cases cast = null;
	public final Calc_S_1.Branch_S_C_multiply_int_int__C_quit__C_sum_int_int_Enum op;
	private final org.scribble.net.ScribMessage m;

	protected Calc_S_1_Cases(org.scribble.net.session.SessionEndpoint<Calc, S> se, boolean dummy, Calc_S_1.Branch_S_C_multiply_int_int__C_quit__C_sum_int_int_Enum op, org.scribble.net.ScribMessage m) {
		super(se);
		this.op = op;
		this.m = m;
	}

	public Calc_S_2 receive(C role, sum op, org.scribble.net.Buf<? super java.lang.Integer> arg1, org.scribble.net.Buf<? super java.lang.Integer> arg2) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		super.use();
		if (!this.m.op.equals(Calc.sum)) {
			throw new org.scribble.main.ScribbleRuntimeException("Wrong branch, received: " + this.m.op);
		}
		arg1.val = (java.lang.Integer) m.payload[0];
		arg2.val = (java.lang.Integer) m.payload[1];
		return new Calc_S_2(this.se, true);
	}

	public Calc_S_2 receive(sum op, org.scribble.net.Buf<? super java.lang.Integer> arg1, org.scribble.net.Buf<? super java.lang.Integer> arg2) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		return receive(Calc.C, op, arg1, arg2);
	}

	@SuppressWarnings("unchecked")
	public Calc_S_2 receive(sum op) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		return receive(op, (org.scribble.net.Buf<java.lang.Integer>) this.se.gc, (org.scribble.net.Buf<java.lang.Integer>) this.se.gc);
	}

	public Calc_S_3 receive(C role, multiply op, org.scribble.net.Buf<? super java.lang.Integer> arg1, org.scribble.net.Buf<? super java.lang.Integer> arg2) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		super.use();
		if (!this.m.op.equals(Calc.multiply)) {
			throw new org.scribble.main.ScribbleRuntimeException("Wrong branch, received: " + this.m.op);
		}
		arg1.val = (java.lang.Integer) m.payload[0];
		arg2.val = (java.lang.Integer) m.payload[1];
		return new Calc_S_3(this.se, true);
	}

	public Calc_S_3 receive(multiply op, org.scribble.net.Buf<? super java.lang.Integer> arg1, org.scribble.net.Buf<? super java.lang.Integer> arg2) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		return receive(Calc.C, op, arg1, arg2);
	}

	@SuppressWarnings("unchecked")
	public Calc_S_3 receive(multiply op) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		return receive(op, (org.scribble.net.Buf<java.lang.Integer>) this.se.gc, (org.scribble.net.Buf<java.lang.Integer>) this.se.gc);
	}

	public Calc_S_4 receive(C role, quit op) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		super.use();
		if (!this.m.op.equals(Calc.quit)) {
			throw new org.scribble.main.ScribbleRuntimeException("Wrong branch, received: " + this.m.op);
		}
		return new Calc_S_4(this.se, true);
	}

	public Calc_S_4 receive(quit op) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException {
		return receive(Calc.C, op);
	}

	@Override
	public Branch_S_C_multiply_int_int__C_quit__C_sum_int_int.Branch_S_C_multiply_int_int__C_quit__C_sum_int_int_Enum getOp() {
		return this.op;
	}
}