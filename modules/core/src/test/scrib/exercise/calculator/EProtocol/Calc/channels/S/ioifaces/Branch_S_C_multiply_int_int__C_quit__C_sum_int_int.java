package exercise.calculator.EProtocol.Calc.channels.S.ioifaces;

import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.channels.S.*;

public interface Branch_S_C_multiply_int_int__C_quit__C_sum_int_int<__Succ1 extends Succ_In_C_multiply_int_int, __Succ2 extends Succ_In_C_quit, __Succ3 extends Succ_In_C_sum_int_int> extends Succ_Out_C_result_int {
	public static final Branch_S_C_multiply_int_int__C_quit__C_sum_int_int<?, ?, ?> cast = null;

	abstract Case_S_C_multiply_int_int__C_quit__C_sum_int_int<__Succ1, __Succ2, __Succ3> branch(C role) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException;

	@Override
	default Branch_S_C_multiply_int_int__C_quit__C_sum_int_int<?, ?, ?> to(Branch_S_C_multiply_int_int__C_quit__C_sum_int_int<?, ?, ?> cast) {
		return (Branch_S_C_multiply_int_int__C_quit__C_sum_int_int<?, ?, ?>) this;
	}

	default Calc_S_1 to(Calc_S_1 cast) {
		return (Calc_S_1) this;
	}

public enum Branch_S_C_multiply_int_int__C_quit__C_sum_int_int_Enum implements org.scribble.net.session.OpEnum {
	sum, multiply, quit
}
}