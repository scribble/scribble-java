package exercise.calculator.EProtocol.Calc.channels.C.ioifaces;

import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.channels.C.*;

public interface Select_C_S_multiply_int_int__S_quit__S_sum_int_int<__Succ1 extends Succ_Out_S_multiply_int_int, __Succ2 extends Succ_Out_S_quit, __Succ3 extends Succ_Out_S_sum_int_int> extends Out_S_multiply_int_int<__Succ1>, Out_S_quit<__Succ2>, Out_S_sum_int_int<__Succ3>, Succ_In_S_result_int {
	public static final Select_C_S_multiply_int_int__S_quit__S_sum_int_int<?, ?, ?> cast = null;

	@Override
	default Select_C_S_multiply_int_int__S_quit__S_sum_int_int<?, ?, ?> to(Select_C_S_multiply_int_int__S_quit__S_sum_int_int<?, ?, ?> cast) {
		return (Select_C_S_multiply_int_int__S_quit__S_sum_int_int<?, ?, ?>) this;
	}

	default Calc_C_1 to(Calc_C_1 cast) {
		return (Calc_C_1) this;
	}
}