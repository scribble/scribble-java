package org.scribble.codegen.java.endpointapi.ioifaces;

import java.util.LinkedList;
import java.util.List;
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
		String ifname = getHandleInterfaceName(self, this.curr);

		this.ib.setName(ifname);
		this.ib.setPackage(packname);
		this.ib.addModifiers(JavaBuilder.PUBLIC);
	}

	@Override
	protected void addCastField()
	{

	}

	// Don't add Action Interfaces
	@Override
	protected void addSuccessorParamsAndActionInterfaces()
	{
		//Role self = this.apigen.getSelf();

		int i = 1;
		for (IOAction a : getHandleInterfaceIOActionParams(this.curr))
		{
			this.ib.addParameters("__Succ" + i + " extends " + SuccessorInterfaceGenerator.getSuccessorInterfaceName(a));
			i++;
		}
	}

	// Pre: curr is a branch state
	protected static List<IOAction> getHandleInterfaceIOActionParams(EndpointState curr)
	{
		List<IOAction> as = new LinkedList<>();
		for (IOAction a : curr.getAcceptable().stream().sorted(IOACTION_COMPARATOR).collect(Collectors.toList()))
		{
			EndpointState succ = curr.accept(a);
			/*InterfaceBuilder iostateif = this.iogen.getIOStateInterface(IOStateInterfaceGenerator.getIOStateInterfaceName(self, succ));
			for (String param : iostateif.getParameters())
			{
				this.ib.addParameters("__Succ" + i + " extends " + SuccessorInterfaceGenerator.getSuccessorInterfaceName(this.curr, a));
			}*/
			for (IOAction b : succ.getAcceptable().stream().sorted(IOACTION_COMPARATOR).collect(Collectors.toList()))
			{
				as.add(b);
			}
		}
		return as;
	}

	protected void addHandleMethods()
	{
		GProtocolName gpn = this.apigen.getGProtocolName();
		Role self = this.apigen.getSelf();
		Set<IOAction> as = this.curr.getAcceptable();

		this.ib.addImports(SessionApiGenerator.getOpsPackageName(gpn) + ".*");
		int i = 1; 
		for (IOAction a : as.stream().sorted(IOACTION_COMPARATOR).collect(Collectors.toList()))
		{
			EndpointState succ = this.curr.accept(a);

			MethodBuilder mb = this.ib.newAbstractMethod();
			HandlerInterfaceGenerator.setHandleMethodHeaderWithoutParamTypes(this.apigen, mb);
			setHandleMethodSuccessorParam(this.iogen, self, succ, mb, i);
			HandlerInterfaceGenerator.addHandleMethodOpAndPayloadParams(this.apigen, a, mb);
		}
	}

	private static void setHandleMethodSuccessorParam(IOInterfacesGenerator iogen, Role self, EndpointState succ, MethodBuilder mb, int i)
	{
			if (succ.isTerminal())
			{
				mb.addParameters(ScribSocketGenerator.ENDSOCKET_CLASS + "<?, ?> end");
			}
			else
			{
				InterfaceBuilder next = iogen.getIOStateInterface(IOStateInterfaceGenerator.getIOStateInterfaceName(self, succ));  // Select/Receive/Branch
				String ret = next.getName();
				ret += "<" + next.getParameters().stream().map((p) -> "__Succ" + i).collect(Collectors.joining(", ")) + ">";  // FIXME: fragile?
				mb.addParameters(ret + " schan");
				
				// FIXME: duplicates possible?
			}
	}

	// Pre: s is a branch state
	protected static String getHandleInterfaceName(Role self, EndpointState s)
	{
		// FIXME: factor out (CaseInterfaceGenerator, IOStateInterfaceGenerator.getIOStateInterfaceName)
		return "Handle_" + self + "_" + s.getAcceptable().stream().sorted(IOACTION_COMPARATOR)
				.map((a) -> ActionInterfaceGenerator.getActionString(a)).collect(Collectors.joining("__"));
	}
}
