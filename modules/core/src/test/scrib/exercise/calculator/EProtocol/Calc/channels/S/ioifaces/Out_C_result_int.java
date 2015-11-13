package exercise.calculator.EProtocol.Calc.channels.S.ioifaces;

import java.io.IOException;
import exercise.calculator.EProtocol.Calc.*;
import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.ops.*;

public interface Out_C_result_int<__Succ extends Succ_Out_C_result_int> {

	abstract public __Succ send(C role, result op, java.lang.Integer arg0) throws org.scribble.main.ScribbleRuntimeException, IOException;
}