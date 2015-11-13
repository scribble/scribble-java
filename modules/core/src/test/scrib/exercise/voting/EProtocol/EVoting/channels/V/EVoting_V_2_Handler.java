package exercise.voting.EProtocol.EVoting.channels.V;

import java.io.IOException;
import exercise.voting.EProtocol.EVoting.ops.*;
import exercise.voting.EProtocol.EVoting.*;
import exercise.voting.EProtocol.EVoting.roles.*;

public interface EVoting_V_2_Handler {

	abstract public void receive(EVoting_V_3 schan, Ok op, org.scribble.net.Buf<? super java.lang.String> arg1) throws org.scribble.main.ScribbleRuntimeException, IOException;

	abstract public void receive(EndSocket schan, Reject op, org.scribble.net.Buf<? super java.lang.String> arg1) throws org.scribble.main.ScribbleRuntimeException, IOException;
}