package test.scratch.Scratch1.Proto1.channels.S;

import java.io.IOException;
import test.scratch.Scratch1.Proto1.ops.*;
import test.scratch.Scratch1.Proto1.*;
import test.scratch.Scratch1.Proto1.roles.*;

public interface Proto1_S_2_Handler {

	public void receive(Proto1_S_3 schan, _2 op, org.scribble.net.Buf<? super java.lang.Integer> arg1) throws org.scribble.main.ScribbleRuntimeException, IOException ;

	public void receive(org.scribble.net.scribsock.EndSocket<Proto1, S> schan, _4 op) throws org.scribble.main.ScribbleRuntimeException, IOException ;
}