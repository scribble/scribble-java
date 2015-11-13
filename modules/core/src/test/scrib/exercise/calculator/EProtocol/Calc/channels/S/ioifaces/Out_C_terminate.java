package exercise.calculator.EProtocol.Calc.channels.S.ioifaces;

import java.io.IOException;
import exercise.calculator.EProtocol.Calc.*;
import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.ops.*;

public interface Out_C_terminate<__Succ extends Succ_Out_C_terminate> {

	abstract public exercise.calculator.EProtocol.Calc.channels.S.EndSocket send(C role, terminate op) throws org.scribble.main.ScribbleRuntimeException, IOException;
}