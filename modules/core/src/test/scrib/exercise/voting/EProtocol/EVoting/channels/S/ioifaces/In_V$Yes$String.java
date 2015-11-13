package exercise.voting.EProtocol.EVoting.channels.S.ioifaces;

import java.io.IOException;
import exercise.voting.EProtocol.EVoting.*;
import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.ops.*;

public interface In_V$Yes$String<__Succ extends Succ_In_V$Yes$String> {

	abstract public __Succ receive(V role, Yes op, org.scribble.net.Buf<? super java.lang.String> arg1) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException;
}