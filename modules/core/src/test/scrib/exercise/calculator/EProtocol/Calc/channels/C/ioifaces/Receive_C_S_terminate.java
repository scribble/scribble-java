package exercise.calculator.EProtocol.Calc.channels.C.ioifaces;

import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.ops.*;
import exercise.calculator.EProtocol.Calc.channels.C.*;

public interface Receive_C_S_terminate<__Succ1 extends Succ_In_S_terminate> extends In_S_terminate<__Succ1>, Succ_Out_S_quit {
	public static final Receive_C_S_terminate<?> cast = null;

	abstract public exercise.calculator.EProtocol.Calc.channels.C.EndSocket async(S role, terminate op) throws org.scribble.main.ScribbleRuntimeException;

	@Override
	default Receive_C_S_terminate<?> to(Receive_C_S_terminate<?> cast) {
		return (Receive_C_S_terminate<?>) this;
	}

	default Calc_C_4 to(Calc_C_4 cast) {
		return (Calc_C_4) this;
	}
}