package exercise.voting.EProtocol.EVoting.channels.S.ioifaces;

import exercise.voting.EProtocol.EVoting.ops.*;

public interface Case_S_V$Yes$String$_V$No$String<__Succ1 extends Succ_In_V$Yes$String, __Succ2 extends Succ_In_V$No$String> extends In_V$Yes$String<__Succ1>, In_V$No$String<__Succ2> {

	abstract Branch_S_V$Yes$String$_V$No$String.Branch_S_V$Yes$String$_V$No$String_Enum getOp();

	abstract public __Succ1 receive(Yes op) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException;

	abstract public __Succ2 receive(No op) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException;
}