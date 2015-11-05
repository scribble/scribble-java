package org.scribble.codegen.java.endpointapi.ioifaces;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.endpointapi.StateChannelTypeGenerator;
import org.scribble.codegen.java.util.ClassBuilder;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.model.local.Send;

public class IOStateInterfaceGenerator extends StateChannelTypeGenerator
{
	private final EndpointState curr;
	private final Map<IOAction, InterfaceBuilder> actions;
	private final Set<InterfaceBuilder> preds;  // can be null (instead of empty)
	private final InterfaceBuilder ib = new InterfaceBuilder();

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
		this.ib.setName(getIOStateInterfaceName(this.curr));
		this.ib.setPackage(IOInterfacesGenerator.getPackageName(this.apigen.getGProtocolName(), this.apigen.getSelf()));
		this.ib.addModifiers(JavaBuilder.PUBLIC);
		int i = 1;
		for (IOAction a : this.curr.getAcceptable())
		{
			String succ = this.actions.get(a).getName();
			this.ib.addParameters("__Succ" + i + " extends " + SuccessorInterfaceGenerator.getSuccessorInterfaceName(a));
			this.ib.addInterfaces(succ);
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
	public static String getIOStateInterfaceName(EndpointState s)
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
		name += "_";
		for (IOAction a : s.getAcceptable())
		{
			name += ActionInterfaceGenerator.getActionString(a);
		}
		return name;
	}
}
