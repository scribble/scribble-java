package exercise.calculator.EProtocol.Calc.channels.C.ioifaces;

import java.io.IOException;
import exercise.calculator.EProtocol.Calc.*;
import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.ops.*;

public interface Out_S_sum_int_int<__Succ extends Succ_Out_S_sum_int_int> {

	abstract public __Succ send(S role, sum op, java.lang.Integer arg0, java.lang.Integer arg1) throws org.scribble.main.ScribbleRuntimeException, IOException;
}