package org.scribble.codegen.java.endpointapi.ioifaces;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.java.endpointapi.CaseSocketGenerator;
import org.scribble.codegen.java.endpointapi.InputFutureGenerator;
import org.scribble.codegen.java.endpointapi.ReceiveSocketGenerator;
import org.scribble.codegen.java.endpointapi.ScribSocketGenerator;
import org.scribble.codegen.java.endpointapi.SessionApiGenerator;
import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.util.AbstractMethodBuilder;
import org.scribble.codegen.java.util.EnumBuilder;
import org.scribble.codegen.java.util.FieldBuilder;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.codegen.java.util.TypeBuilder;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.model.local.Receive;
import org.scribble.model.local.Send;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;

public class CaseInterfaceGenerator extends IOStateInterfaceGenerator
{
	private final Map<IOAction, InterfaceBuilder> actions;
	private final Set<InterfaceBuilder> preds;  // can be null (instead of empty)
	private final InterfaceBuilder ib = new InterfaceBuilder();
	private InterfaceBuilder cases;  // HACK

	public CaseInterfaceGenerator(StateChannelApiGenerator apigen, EndpointState curr, Map<IOAction, InterfaceBuilder> actions, Set<InterfaceBuilder> preds)
	{
		super(apigen, curr);
		this.actions = actions;
		this.preds = preds;
	}

	@Override
	public InterfaceBuilder generateType()
	{
		GProtocolName gpn = this.apigen.getGProtocolName();
		Role self = this.apigen.getSelf();

		String packname = IOInterfacesGenerator.getPackageName(gpn, self);
		String ifname = getIOStateInterfaceName(this.apigen.getSelf(), this.curr);
		Set<IOAction> as = this.curr.getAcceptable();

		this.ib.setName(ifname);
		this.ib.setPackage(packname);
		this.ib.addImports(SessionApiGenerator.getRolesPackageName(gpn) + ".*");
		this.ib.addModifiers(JavaBuilder.PUBLIC);
		
		FieldBuilder cast = this.ib.newField("cast");
		cast.addModifiers(TypeBuilder.PUBLIC, TypeBuilder.STATIC, TypeBuilder.FINAL);
		cast.setType(ifname + "<" + IntStream.range(1, as.size()+1).mapToObj((i) -> "?").collect(Collectors.joining(", ")) + ">");  // FIXME: factor out
		cast.setExpression("null");
		
		IOAction first = as.iterator().next();
		if (first instanceof Receive)
		{
			if (as.size() > 1)  // Branch and Case I/O interfaces
			{
				String name = this.ib.getName();

				InterfaceBuilder cases = new InterfaceBuilder(getCasesInterfaceName(name));
				cases.setPackage(packname);
				cases.addModifiers(JavaBuilder.PUBLIC);
				// Duplicated from BranchSocketGenerator
				EnumBuilder eb = this.ib.newMemberEnum(getBranchInterfaceEnumName(self, this.curr));
				eb.addModifiers(JavaBuilder.PUBLIC);
				eb.addInterfaces(ScribSocketGenerator.OPENUM_INTERFACE);
				this.curr.getAcceptable().stream().forEach((a) -> eb.addValues(SessionApiGenerator.getOpClassName(a.mid)));
				AbstractMethodBuilder op = cases.newAbstractMethod("getOp");
				op.setReturn(name + "." + getBranchInterfaceEnumName(self, this.curr));
				this.cases = cases;
				
				AbstractMethodBuilder bra = this.ib.newAbstractMethod("branch");
				String ret = cases.getName() + "<" + IntStream.range(1, as.size()+1).mapToObj((i) -> "__Succ" + i).collect(Collectors.joining(", ")) + ">";  // FIXME: factor out
				bra.setReturn(ret);
				bra.addParameters(SessionApiGenerator.getRoleClassName(first.peer) + " role");
				bra.addExceptions(StateChannelApiGenerator.SCRIBBLERUNTIMEEXCEPTION_CLASS, "java.io.IOException", "ClassNotFoundException");
				
				int i = 1;
				cases.addImports(SessionApiGenerator.getOpsPackageName(gpn) + ".*");
				for (IOAction a : as)
				{
					MethodBuilder mb2 = cases.newAbstractMethod();
					CaseSocketGenerator.setCaseReceiveDiscardHeaderWithoutReturnType(this.apigen, a, mb2); 
					EndpointState succ = this.curr.accept(first);
					if (succ.isTerminal())
					{
						ScribSocketGenerator.setNextSocketReturnType(this.apigen, mb2, succ);
					}
					else
					{
						mb2.setReturn("__Succ" + i++);  // Hacky
					}
				}
			}
			else
			{
				MethodBuilder mb2 = this.ib.newAbstractMethod();
				ReceiveSocketGenerator.setAsyncDiscardHeaderWithoutReturnType(this.apigen, first, mb2, 
						InputFutureGenerator.getInputFutureName(this.apigen.getSocketClassName(this.curr)));
				this.ib.addImports(SessionApiGenerator.getOpsPackageName(gpn) + ".*");
				EndpointState succ = this.curr.accept(first);
				if (succ.isTerminal())
				{
					ScribSocketGenerator.setNextSocketReturnType(this.apigen, mb2, succ);
				}
				else
				{
					mb2.setReturn("__Succ1");  // Hacky
				}
			}
		}

		int i = 1;
		for (IOAction a : this.curr.getAcceptable())  // FIXME: ordering (cf. IOInterfacesGenerator.getConcreteSuccessorParameters)
		{
			String actif = this.actions.get(a).getName();
			this.ib.addParameters("__Succ" + i + " extends " + SuccessorInterfaceGenerator.getSuccessorInterfaceName(this.curr, a));
			if (first instanceof Receive && this.curr.getAcceptable().size() > 1)
			{
				this.cases.addParameters("__Succ" + i + " extends " + SuccessorInterfaceGenerator.getSuccessorInterfaceName(this.curr, a));
				this.cases.addInterfaces(actif + "<__Succ" + i + ">");
			}
			else
			{
				this.ib.addInterfaces(actif + "<__Succ" + i + ">");
			}
			i++;
		}
		if (this.preds != null)
		{
			for (InterfaceBuilder pred : this.preds)
			{
				this.ib.addInterfaces(pred.getName());  // Adds Successor Interfaces to this I/O State Interface
			}
		}
		return ib;
	}
	
	// Pre: s non-terminal
	public static String getIOStateInterfaceName(Role self, EndpointState s)
	{
		String name;
		IOAction first = s.getAcceptable().iterator().next();
		if (first instanceof Send)
		{
			name = "Select";
		}
		else
		{
			if (s.getAcceptable().size() == 1)
			{
				name = "Receive";
			}
			else
			{
				name = "Branch";
			}
		}
		name += "_" + self + "_";
		/*for (IOAction a : s.getAcceptable())
		{
			name += ActionInterfaceGenerator.getActionString(a);
		}*/
		//name += s.getAcceptable().stream().map((a) -> ActionInterfaceGenerator.getActionString(a)).collect(Collectors.joining("$_"));
		name += s.getAcceptable().stream().map((a) -> ActionInterfaceGenerator.getActionString(a)).collect(Collectors.joining("__"));
		return name;
	}
	
	protected InterfaceBuilder getCasesInterface()
	{
		return this.cases;
	}
	
	protected static String getCasesInterfaceName(String braif)
	{
		return "Case_" + braif.substring("Branch_".length(), braif.length());
	}

	public static String getBranchInterfaceEnumName(Role self, EndpointState curr)
	{
		return getIOStateInterfaceName(self, curr) + "_Enum";
	}
}
