package exercise.calculator.EProtocol.Calc.channels.C.ioifaces;

import java.io.IOException;
import exercise.calculator.EProtocol.Calc.*;
import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.ops.*;

public interface Out_S_quit<__Succ extends Succ_Out_S_quit> {

	abstract public __Succ send(S role, quit op) throws org.scribble.main.ScribbleRuntimeException, IOException;
}