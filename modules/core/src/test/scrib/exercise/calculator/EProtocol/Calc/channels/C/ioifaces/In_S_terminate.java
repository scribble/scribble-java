package exercise.calculator.EProtocol.Calc.channels.C.ioifaces;

import java.io.IOException;
import exercise.calculator.EProtocol.Calc.*;
import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.ops.*;

public interface In_S_terminate<__Succ extends Succ_In_S_terminate> {

	abstract public exercise.calculator.EProtocol.Calc.channels.C.EndSocket receive(S role, terminate op) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException;
}