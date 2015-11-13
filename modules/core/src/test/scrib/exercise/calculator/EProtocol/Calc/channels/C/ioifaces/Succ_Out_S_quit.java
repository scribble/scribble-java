package exercise.calculator.EProtocol.Calc.channels.C.ioifaces;

public interface Succ_Out_S_quit {

	default Receive_C_S_terminate<?> to(Receive_C_S_terminate<?> cast) {
		return (Receive_C_S_terminate<?>) this;
	}
}