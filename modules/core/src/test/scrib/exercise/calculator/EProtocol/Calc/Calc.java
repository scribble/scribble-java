package exercise.calculator.EProtocol.Calc;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.scribble.sesstype.name.Role;
import exercise.calculator.EProtocol.Calc.roles.*;
import exercise.calculator.EProtocol.Calc.ops.*;

public final class Calc extends org.scribble.net.session.Session {
	public static final List<String> IMPATH = new LinkedList<>();
	public static final String SOURCE = "getSource";
	public static final org.scribble.sesstype.name.GProtocolName PROTO = org.scribble.sesstype.SessionTypeFactory.parseGlobalProtocolName("exercise.calculator.EProtocol.Calc");
	public static final S S = exercise.calculator.EProtocol.Calc.roles.S.S;
	public static final C C = exercise.calculator.EProtocol.Calc.roles.C.C;
	public static final result result = exercise.calculator.EProtocol.Calc.ops.result.result;
	public static final quit quit = exercise.calculator.EProtocol.Calc.ops.quit.quit;
	public static final terminate terminate = exercise.calculator.EProtocol.Calc.ops.terminate.terminate;
	public static final sum sum = exercise.calculator.EProtocol.Calc.ops.sum.sum;
	public static final multiply multiply = exercise.calculator.EProtocol.Calc.ops.multiply.multiply;
	public static final List<Role> ROLES = Collections.unmodifiableList(Arrays.asList(new Role[] {S, C}));

	public Calc() {
		super(Calc.IMPATH, Calc.SOURCE, Calc.PROTO);
	}

	@Override
	public List<Role> getRoles() {
		return Calc.ROLES;
	}
}