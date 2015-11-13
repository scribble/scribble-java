package exercise.voting.EProtocol.EVoting.channels.S.ioifaces;

import java.io.IOException;
import exercise.voting.EProtocol.EVoting.*;
import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.ops.*;

public interface Out_V$Ok$String<__Succ extends Succ_Out_V$Ok$String> {

	abstract public __Succ send(V role, Ok op, java.lang.String arg0) throws org.scribble.main.ScribbleRuntimeException, IOException;
}