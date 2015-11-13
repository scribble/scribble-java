package exercise.voting.EProtocol.EVoting.channels.S;

import java.io.IOException;
import exercise.voting.EProtocol.EVoting.ops.*;

public interface EVoting_S_3_Handler {

	abstract public void receive(EVoting_S_4 schan, Yes op, org.scribble.net.Buf<? super java.lang.String> arg1) throws org.scribble.main.ScribbleRuntimeException, IOException;

	abstract public void receive(EVoting_S_4 schan, No op, org.scribble.net.Buf<? super java.lang.String> arg1) throws org.scribble.main.ScribbleRuntimeException, IOException;
}