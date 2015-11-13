package exercise.calculator.EProtocol.Calc.channels.C.ioifaces;

import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.ops.*;
import exercise.calculator.EProtocol.Calc.channels.C.*;

public interface Receive_C_S_result_int<__Succ1 extends Succ_In_S_result_int> extends In_S_result_int<__Succ1>, Succ_Out_S_sum_int_int, Succ_Out_S_multiply_int_int {
	public static final Receive_C_S_result_int<?> cast = null;

	abstract public __Succ1 async(S role, result op) throws org.scribble.main.ScribbleRuntimeException;

	@Override
	default Receive_C_S_result_int<?> to(Receive_C_S_result_int<?> cast) {
		return (Receive_C_S_result_int<?>) this;
	}

	default Calc_C_2 to(Calc_C_2 cast) {
		return (Calc_C_2) this;
	}

	default Calc_C_3 to(Calc_C_3 cast) {
		return (Calc_C_3) this;
	}
}