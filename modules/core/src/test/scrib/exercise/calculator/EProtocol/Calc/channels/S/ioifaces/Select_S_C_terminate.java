package exercise.calculator.EProtocol.Calc.channels.S.ioifaces;

import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.channels.S.*;

public interface Select_S_C_terminate<__Succ1 extends Succ_Out_C_terminate> extends Out_C_terminate<__Succ1>, Succ_In_C_quit {
	public static final Select_S_C_terminate<?> cast = null;

	@Override
	default Select_S_C_terminate<?> to(Select_S_C_terminate<?> cast) {
		return (Select_S_C_terminate<?>) this;
	}

	default Calc_S_4 to(Calc_S_4 cast) {
		return (Calc_S_4) this;
	}
}