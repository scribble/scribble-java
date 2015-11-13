package exercise.voting.EProtocol.EVoting.channels.V.ioifaces;

import java.io.IOException;
import exercise.voting.EProtocol.EVoting.*;
import exercise.voting.EProtocol.EVoting.roles.*;
import exercise.voting.EProtocol.EVoting.ops.*;

public interface Out_S$Yes$String<__Succ extends Succ_Out_S$Yes$String> {

	abstract public __Succ send(S role, Yes op, java.lang.String arg0) throws org.scribble.main.ScribbleRuntimeException, IOException;
}