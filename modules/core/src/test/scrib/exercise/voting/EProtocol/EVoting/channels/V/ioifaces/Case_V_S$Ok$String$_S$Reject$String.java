package exercise.voting.EProtocol.EVoting.channels.V.ioifaces;

import exercise.voting.EProtocol.EVoting.ops.*;

public interface Case_V_S$Ok$String$_S$Reject$String<__Succ1 extends Succ_In_S$Ok$String, __Succ2 extends Succ_In_S$Reject$String> extends In_S$Ok$String<__Succ1>, In_S$Reject$String<__Succ2> {

	abstract Branch_V_S$Ok$String$_S$Reject$String.Branch_V_S$Ok$String$_S$Reject$String_Enum getOp();

	abstract public __Succ1 receive(Ok op) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException;

	abstract public __Succ2 receive(Reject op) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException;
}