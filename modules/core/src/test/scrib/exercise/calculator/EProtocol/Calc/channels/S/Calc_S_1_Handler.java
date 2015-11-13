package exercise.calculator.EProtocol.Calc.channels.S;

import java.io.IOException;
import exercise.calculator.EProtocol.Calc.ops.*;

public interface Calc_S_1_Handler {

	abstract public void receive(Calc_S_2 schan, sum op, org.scribble.net.Buf<? super java.lang.Integer> arg1, org.scribble.net.Buf<? super java.lang.Integer> arg2) throws org.scribble.main.ScribbleRuntimeException, IOException;

	abstract public void receive(Calc_S_3 schan, multiply op, org.scribble.net.Buf<? super java.lang.Integer> arg1, org.scribble.net.Buf<? super java.lang.Integer> arg2) throws org.scribble.main.ScribbleRuntimeException, IOException;

	abstract public void receive(Calc_S_4 schan, quit op) throws org.scribble.main.ScribbleRuntimeException, IOException;
}