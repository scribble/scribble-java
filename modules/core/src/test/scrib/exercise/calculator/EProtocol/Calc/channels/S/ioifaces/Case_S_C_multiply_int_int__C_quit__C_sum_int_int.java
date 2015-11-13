package exercise.calculator.EProtocol.Calc.channels.S.ioifaces;

import exercise.calculator.EProtocol.Calc.ops.*;

public interface Case_S_C_multiply_int_int__C_quit__C_sum_int_int<__Succ1 extends Succ_In_C_multiply_int_int, __Succ2 extends Succ_In_C_quit, __Succ3 extends Succ_In_C_sum_int_int> extends In_C_multiply_int_int<__Succ1>, In_C_quit<__Succ2>, In_C_sum_int_int<__Succ3> {
	public static final Branch_S_C_multiply_int_int__C_quit__C_sum_int_int<?, ?, ?> cast = null;

	abstract Branch_S_C_multiply_int_int__C_quit__C_sum_int_int.Branch_S_C_multiply_int_int__C_quit__C_sum_int_int_Enum getOp();

	abstract public __Succ1 receive(multiply op) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException;

	abstract public __Succ2 receive(quit op) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException;

	abstract public __Succ3 receive(sum op) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException;
}