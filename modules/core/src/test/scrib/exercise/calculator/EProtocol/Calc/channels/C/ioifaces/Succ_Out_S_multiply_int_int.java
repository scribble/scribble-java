package exercise.calculator.EProtocol.Calc.channels.C.ioifaces;

public interface Succ_Out_S_multiply_int_int {

	default Receive_C_S_result_int<?> to(Receive_C_S_result_int<?> cast) {
		return (Receive_C_S_result_int<?>) this;
	}
}