package org.scribble.codegen.java.endpointapi.ioifaces;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.java.endpointapi.ScribSocketGenerator;
import org.scribble.codegen.java.endpointapi.SessionApiGenerator;
import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.util.AbstractMethodBuilder;
import org.scribble.codegen.java.util.EnumBuilder;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.sesstype.name.Role;

public class BranchInterfaceGenerator extends IOStateInterfaceGenerator
{
	public BranchInterfaceGenerator(StateChannelApiGenerator apigen, Map<IOAction, InterfaceBuilder> actions, EndpointState curr)
	{
		super(apigen, actions, curr);
	}

	@Override
	protected void constructInterface()
	{
		super.constructInterface();
		addBranchEnum();
		addBranchMethods();
	}
				
	protected void addBranchMethods()
	{
		Role self = this.apigen.getSelf();
		Set<IOAction> as = this.curr.getAcceptable();

		// FIXME: factor out with BranchSocketGenerator
		AbstractMethodBuilder bra = this.ib.newAbstractMethod("branch");
		String ret = CaseInterfaceGenerator.getCasesInterfaceName(self, this.curr)
				+ "<" + IntStream.range(1, as.size()+1).mapToObj((i) -> "__Succ" + i).collect(Collectors.joining(", ")) + ">";  // FIXME: factor out
		bra.setReturn(ret);
		bra.addParameters(SessionApiGenerator.getRoleClassName(as.iterator().next().peer) + " role");
		bra.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "java.io.IOException", "ClassNotFoundException");
		
		AbstractMethodBuilder bra2 = this.ib.newAbstractMethod("branch");
		bra2.setReturn(JavaBuilder.VOID);
		bra2.addParameters(SessionApiGenerator.getRoleClassName(as.iterator().next().peer) + " role");
		String next = HandleInterfaceGenerator.getHandleInterfaceName(self, this.curr) + "<" + IntStream.range(1, as.size() + 1).mapToObj((i) -> "__Succ" + i).collect(Collectors.joining(", ")) + ">";
		bra2.addParameters(next + " handler");
		bra2.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "java.io.IOException", "ClassNotFoundException");
		
		AbstractMethodBuilder bra3 = this.ib.newAbstractMethod("handle");
		bra3.setReturn(JavaBuilder.VOID);
		bra3.addParameters(SessionApiGenerator.getRoleClassName(as.iterator().next().peer) + " role");
		String handle = HandleInterfaceGenerator.getHandleInterfaceName(self, this.curr) + "<" +
				as.stream().sorted(IOStateInterfaceGenerator.IOACTION_COMPARATOR)
					.map((a) -> SuccessorInterfaceGenerator.getSuccessorInterfaceName(a)).collect(Collectors.joining(", ")) + ">";
		bra3.addParameters(handle + " handler");
		bra3.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "java.io.IOException", "ClassNotFoundException");
	}

	protected void addBranchEnum()
	{
		Role self = this.apigen.getSelf();

		// Duplicated from BranchSocketGenerator
		EnumBuilder eb = this.ib.newMemberEnum(getBranchInterfaceEnumName(self, this.curr));
		eb.addModifiers(JavaBuilder.PUBLIC);
		eb.addInterfaces(ScribSocketGenerator.OPENUM_INTERFACE);
		this.curr.getAcceptable().stream().forEach((a) -> eb.addValues(SessionApiGenerator.getOpClassName(a.mid)));
	}

	// Don't add Action Interfaces (added to CaseInterface)
	@Override
	protected void addSuccessorParamsAndActionInterfaces()
	{
		int i = 1;
		for (IOAction a : this.curr.getAcceptable().stream().sorted(IOACTION_COMPARATOR).collect(Collectors.toList()))
		{
			this.ib.addParameters("__Succ" + i + " extends " + SuccessorInterfaceGenerator.getSuccessorInterfaceName(a));
			i++;
		}
	}
	
	public static String getBranchInterfaceEnumName(Role self, EndpointState curr)
	{
		return getIOStateInterfaceName(self, curr) + "_Enum";
	}
}
