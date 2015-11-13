package exercise.calculator.EProtocol.Calc.channels.S.ioifaces;

import java.io.IOException;
import exercise.calculator.EProtocol.Calc.*;
import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.ops.*;

public interface In_C_multiply_int_int<__Succ extends Succ_In_C_multiply_int_int> {

	abstract public __Succ receive(C role, multiply op, org.scribble.net.Buf<? super java.lang.Integer> arg1, org.scribble.net.Buf<? super java.lang.Integer> arg2) throws org.scribble.main.ScribbleRuntimeException, java.io.IOException, ClassNotFoundException;
}