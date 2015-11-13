package exercise.calculator.EProtocol.Calc.channels.C.ioifaces;

import java.io.IOException;
import exercise.calculator.EProtocol.Calc.*;
import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.ops.*;

public interface In_S_result_int<__Succ extends Succ_In_S_result_int> {

	abstract public __Succ receive(S role, result op, org.scribble.net.Buf<? super java.lang.Integer> arg1) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException;
}