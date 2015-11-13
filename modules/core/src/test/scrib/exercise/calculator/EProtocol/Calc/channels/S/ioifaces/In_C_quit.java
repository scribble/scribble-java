package exercise.calculator.EProtocol.Calc.channels.S.ioifaces;

import java.io.IOException;
import exercise.calculator.EProtocol.Calc.*;
import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.ops.*;

public interface In_C_quit<__Succ extends Succ_In_C_quit> {

	abstract public __Succ receive(C role, quit op) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException;
}