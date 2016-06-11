package org.scribble.codegen.java.endpointapi.ioifaces;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.codegen.java.endpointapi.CaseSocketGenerator;
import org.scribble.codegen.java.endpointapi.ScribSocketGenerator;
import org.scribble.codegen.java.endpointapi.SessionApiGenerator;
import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.util.AbstractMethodBuilder;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;

public class CaseInterfaceGenerator extends IOStateInterfaceGenerator
{
	public CaseInterfaceGenerator(StateChannelApiGenerator apigen, Map<IOAction, InterfaceBuilder> actions, EndpointState curr)
	{
		super(apigen, actions, curr);
	}

	@Override
	protected void constructInterface() throws ScribbleException
	{
		super.constructInterface();
		addBranchEnumField();
		addCaseReceiveDiscardMethods();
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

	/*@Override
	protected void addSuccessorInterfaces()
	{

	}*/
	
	protected void addBranchEnumField()
	{
		Role self = this.apigen.getSelf();
		String name = super.getIOStateInterfaceName(self, this.curr);

		AbstractMethodBuilder op = this.ib.newAbstractMethod("getOp");
		op.setReturn(name + "." + BranchInterfaceGenerator.getBranchInterfaceEnumName(self, this.curr));
	}
				
	protected void addCaseReceiveDiscardMethods()
	{
		GProtocolName gpn = this.apigen.getGProtocolName();
		Set<IOAction> as = this.curr.getTakeable();

		int i = 1;
		this.ib.addImports(SessionApiGenerator.getOpsPackageName(gpn) + ".*");
		for (IOAction a : as.stream().sorted(IOACTION_COMPARATOR).collect(Collectors.toList()))
		{
			MethodBuilder mb = this.ib.newAbstractMethod();
			CaseSocketGenerator.setCaseReceiveDiscardHeaderWithoutReturnType(this.apigen, a, mb); 
			EndpointState succ = this.curr.take(a);
			if (succ.isTerminal())
			{
				ScribSocketGenerator.setNextSocketReturnType(this.apigen, mb, succ);
			}
			else
			{
				mb.setReturn("__Succ" + i);  // Hacky?  // FIXME: factor out Succ
			}
			i++;
		}
	}
	
	//protected static String getCasesInterfaceName(String braif)
	// Pre: s is a branch state
	// Cf. IOStateInterfaceGenerator.getIOStateInterfaceName
	protected static String getCasesInterfaceName(Role self, EndpointState s)
	{
		//return "Case_" + braif.substring("Branch_".length(), braif.length());
		return "Case_" + self + "_" + s.getTakeable().stream().sorted(IOACTION_COMPARATOR)
				.map((a) -> ActionInterfaceGenerator.getActionString(a)).collect(Collectors.joining("__"));
	}
}
