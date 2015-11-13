package exercise.voting.EProtocol.EVoting.channels.V.ioifaces;

public interface Succ_Out_S$Yes$String {

	default Receive_V_S$Result$Int<?> to(Receive_V_S$Result$Int<?> cast) {
		return (Receive_V_S$Result$Int<?>) this;
	}
}