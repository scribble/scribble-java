package org.scribble.codegen.java.endpointapi.ioifaces;

import java.util.Map;
import java.util.Set;

import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.endpointapi.StateChannelTypeGenerator;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.model.local.Receive;
import org.scribble.model.local.Send;
import org.scribble.sesstype.name.Role;

public class IOStateInterfaceGenerator extends StateChannelTypeGenerator
{
	private final EndpointState curr;
	private final Map<IOAction, InterfaceBuilder> actions;
	private final Set<InterfaceBuilder> preds;  // can be null (instead of empty)
	private final InterfaceBuilder ib = new InterfaceBuilder();
	private InterfaceBuilder cases;  // HACK

	public IOStateInterfaceGenerator(StateChannelApiGenerator apigen, EndpointState curr, Map<IOAction, InterfaceBuilder> actions, Set<InterfaceBuilder> preds)
	{
		super(apigen);
		this.curr = curr;
		this.actions = actions;
		this.preds = preds;
	}

	@Override
	public InterfaceBuilder generateType()
	{
		String packname = IOInterfacesGenerator.getPackageName(this.apigen.getGProtocolName(), this.apigen.getSelf());
		this.ib.setName(getIOStateInterfaceName(this.apigen.getSelf(), this.curr));
		this.ib.setPackage(packname);
		this.ib.addModifiers(JavaBuilder.PUBLIC);
		IOAction first = this.curr.getAcceptable().iterator().next();
		if (first instanceof Receive && this.curr.getAcceptable().size() > 1)
		{
			String name = ib.getName();
			InterfaceBuilder cases = new InterfaceBuilder("Cases_" + name.substring("Branch_".length(), name.length()));
			cases.setPackage(packname);
			cases.addModifiers(JavaBuilder.PUBLIC);
			this.cases = cases;
		}
		int i = 1;
		for (IOAction a : this.curr.getAcceptable())
		{
			String succ = this.actions.get(a).getName();
			this.ib.addParameters("__Succ" + i + " extends " + SuccessorInterfaceGenerator.getSuccessorInterfaceName(a));
			if (first instanceof Receive && this.curr.getAcceptable().size() > 1)
			{
				this.cases.addParameters("__Succ" + i + " extends " + SuccessorInterfaceGenerator.getSuccessorInterfaceName(a));
				this.cases.addInterfaces(succ + "<__Succ" + i + ">");
			}
			else
			{
				this.ib.addInterfaces(succ + "<__Succ" + i + ">");
			}
			i++;
		}
		if (this.preds != null)
		{
			for (InterfaceBuilder pred : this.preds)
			{
				this.ib.addInterfaces(pred.getName());
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
		name += "_" + self + "$";
		for (IOAction a : s.getAcceptable())
		{
			name += ActionInterfaceGenerator.getActionString(a);
		}
		return name;
	}
	
	protected InterfaceBuilder getCasesInterface()
	{
		return this.cases;
	}
}
