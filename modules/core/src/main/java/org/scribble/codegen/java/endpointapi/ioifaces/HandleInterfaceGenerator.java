package org.scribble.codegen.java.endpointapi.ioifaces;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.codegen.java.endpointapi.HandlerInterfaceGenerator;
import org.scribble.codegen.java.endpointapi.ScribSocketGenerator;
import org.scribble.codegen.java.endpointapi.SessionApiGenerator;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;

// Cf. HandlerInterfaceGenerator
public class HandleInterfaceGenerator extends IOStateInterfaceGenerator
{
	private final IOInterfacesGenerator iogen;

	public HandleInterfaceGenerator(IOInterfacesGenerator iogen, Map<IOAction, InterfaceBuilder> actions, EndpointState curr)
	{
		super(iogen.apigen, actions, curr);
		this.iogen = iogen;
	}

	@Override
	protected void constructInterface()
	{
		super.constructInterface();
		addHandleMethods();
	}

	@Override
	protected void addHeader()
	{
		GProtocolName gpn = this.apigen.getGProtocolName();
		Role self = this.apigen.getSelf();
		String packname = IOInterfacesGenerator.getIOInterfacePackageName(gpn, self);
		String ifname = getCasesInterfaceName(self, this.curr);

		this.ib.setName(ifname);
		this.ib.setPackage(packname);
		this.ib.addModifiers(JavaBuilder.PUBLIC);
	}
				
	protected void addHandleMethods()
	{
		GProtocolName gpn = this.apigen.getGProtocolName();
		Role self = this.apigen.getSelf();
		Set<IOAction> as = this.curr.getAcceptable();

		this.ib.addImports(SessionApiGenerator.getOpsPackageName(gpn) + ".*");
		for (IOAction a : as.stream().sorted(IOACTION_COMPARATOR).collect(Collectors.toList()))
		{
			EndpointState succ = this.curr.accept(a);
			//String nextClass = this.apigen.getSocketClassName(succ);

			MethodBuilder mb = this.ib.newAbstractMethod();
			HandlerInterfaceGenerator.setHandleMethodHeaderWithoutParamTypes(this.apigen, mb);
			if (succ.isTerminal())
			{
				mb.addParameters(ScribSocketGenerator.GENERATED_ENDSOCKET_NAME + " schan");  // FIXME: factor out
			}
			else
			{
				InterfaceBuilder next = this.iogen.getIOStateInterface(IOStateInterfaceGenerator.getIOStateInterfaceName(self, succ));  // Select/Receive/Branch
				mb.addParameters(next + " schan");
			}
			HandlerInterfaceGenerator.addHandleMethodOpAndPayloadParams(this.apigen, a, mb);
		}
	}

	// Don't add Action Interfaces
	@Override
	protected void addSuccessorParamsAndActionInterfaces()
	{
		int i = 1;
		for (IOAction a : this.curr.getAcceptable().stream().sorted(IOACTION_COMPARATOR).collect(Collectors.toList()))
		{
			this.ib.addParameters("__Succ" + i + " extends " + SuccessorInterfaceGenerator.getSuccessorInterfaceName(this.curr, a));
			i++;
		}
	}

	// Pre: s is a branch state
	protected static String getCasesInterfaceName(Role self, EndpointState s)
	{
		// FIXME: factor out (CaseInterfaceGenerator, IOStateInterfaceGenerator.getIOStateInterfaceName)
		return "Handle_" + self + "_" + s.getAcceptable().stream().sorted(IOACTION_COMPARATOR)
				.map((a) -> ActionInterfaceGenerator.getActionString(a)).collect(Collectors.joining("__"));
	}
}
