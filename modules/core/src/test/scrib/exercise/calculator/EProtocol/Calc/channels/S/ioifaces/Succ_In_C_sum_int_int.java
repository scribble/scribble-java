package exercise.calculator.EProtocol.Calc.channels.S.ioifaces;

public interface Succ_In_C_sum_int_int {

	default Select_S_C_result_int<?> to(Select_S_C_result_int<?> cast) {
		return (Select_S_C_result_int<?>) this;
	}
}