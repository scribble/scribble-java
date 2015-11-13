package exercise.calculator.EProtocol.Calc.channels.S.ioifaces;

public interface Succ_In_C_quit {

	default Select_S_C_terminate<?> to(Select_S_C_terminate<?> cast) {
		return (Select_S_C_terminate<?>) this;
	}
}