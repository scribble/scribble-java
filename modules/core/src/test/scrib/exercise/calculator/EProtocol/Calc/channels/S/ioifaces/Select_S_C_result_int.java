package exercise.calculator.EProtocol.Calc.channels.S.ioifaces;

import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.channels.S.*;

public interface Select_S_C_result_int<__Succ1 extends Succ_Out_C_result_int> extends Out_C_result_int<__Succ1>, Succ_In_C_sum_int_int, Succ_In_C_multiply_int_int {
	public static final Select_S_C_result_int<?> cast = null;

	@Override
	default Select_S_C_result_int<?> to(Select_S_C_result_int<?> cast) {
		return (Select_S_C_result_int<?>) this;
	}

	default Calc_S_2 to(Calc_S_2 cast) {
		return (Calc_S_2) this;
	}

	default Calc_S_3 to(Calc_S_3 cast) {
		return (Calc_S_3) this;
	}
}