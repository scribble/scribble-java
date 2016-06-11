package org.scribble.codegen.java.endpointapi.ioifaces;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.java.endpointapi.SessionApiGenerator;
import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.util.FieldBuilder;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.TypeBuilder;
import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleException;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;

// Cf. ScribSocketGenerator
// Partial I/O State I/f generator -- Successor Interfaces and cast methods added later
public abstract class IOStateInterfaceGenerator extends IOInterfaceGenerator
{
	public static final Comparator<IOAction> IOACTION_COMPARATOR = new Comparator<IOAction>()
			{
				@Override
				public int compare(IOAction a1, IOAction a2)
				{
					return ActionInterfaceGenerator.getActionInterfaceName(a1).compareTo(ActionInterfaceGenerator.getActionInterfaceName(a2));
				}
			};

	protected final Map<IOAction, InterfaceBuilder> actions;
	
	protected final InterfaceBuilder ib = new InterfaceBuilder();

	// Preds can be null
	public IOStateInterfaceGenerator(StateChannelApiGenerator apigen, Map<IOAction, InterfaceBuilder> actions, EndpointState curr)
	{
		super(apigen, curr);
		this.actions = Collections.unmodifiableMap(actions);
	}
	
	@Override
	public InterfaceBuilder generateType() throws ScribbleException
	{
		constructInterface();
		return this.ib;
	}

	protected void constructInterface() throws ScribbleException
	{
		addHeader();
		addSuccessorParamsAndActionInterfaces();
		//addSuccessorInterfaces();  // Do later (different states may share the same IO State I/f, don't know all successors for this I/f yet (only this state)
		addCastField();
	}

	protected void addHeader()
	{
		GProtocolName gpn = this.apigen.getGProtocolName();
		Role self = this.apigen.getSelf();
		String packname = IOInterfacesGenerator.getIOInterfacePackageName(gpn, self);
		String ifname = getIOStateInterfaceName(self, this.curr);

		this.ib.setName(ifname);
		this.ib.setPackage(packname);
		this.ib.addImports(SessionApiGenerator.getRolesPackageName(gpn) + ".*");
		this.ib.addModifiers(JavaBuilder.PUBLIC);
	}
	
	protected void addCastField()
	{
		String ifname = getIOStateInterfaceName(this.apigen.getSelf(), this.curr);
		Set<IOAction> as = this.curr.getTakeable();

		FieldBuilder cast = this.ib.newField("cast");
		cast.addModifiers(TypeBuilder.PUBLIC, TypeBuilder.STATIC, TypeBuilder.FINAL);
		cast.setType(ifname + "<" + IntStream.range(1, as.size()+1).mapToObj((i) -> "?").collect(Collectors.joining(", ")) + ">");  // FIXME: factor out
		cast.setExpression("null");
	}

	protected void addSuccessorParamsAndActionInterfaces()
	{
		int i = 1;
		for (IOAction a : this.curr.getTakeable().stream().sorted(IOACTION_COMPARATOR).collect(Collectors.toList()))
		{
			if (a.isSend() || a.isReceive())  // HACK FIXME
			{
				String actif = this.actions.get(a).getName();
				this.ib.addParameters("__Succ" + i + " extends " + SuccessorInterfaceGenerator.getSuccessorInterfaceName(a));
				this.ib.addInterfaces(actif + "<__Succ" + i + ">");
				i++;
			}
		}
	}

	/*protected void addSuccessorInterfaces()
	{
		if (this.preds != null)
		{
			System.out.println("AA: " + this.ib.getName() + ", " + this.preds.stream().map((p) -> p.getName()).collect(Collectors.toList()));
			
			for (InterfaceBuilder pred : this.preds)
			{
				this.ib.addInterfaces(pred.getName());  // Adds Successor Interfaces to this I/O State Interface
			}
		}
	}*/

	// Pre: s non-terminal
	public static String getIOStateInterfaceName(Role self, EndpointState s)
	{
		String name = null;
		switch (s.getStateKind())
		{
			case OUTPUT:      name = "Select";  break;
			case UNARY_INPUT: name = "Receive"; break;
			case POLY_INPUT:  name = "Branch";  break;
			case TERMINAL:    throw new RuntimeScribbleException("Shouldn't get in here: " + s);
			default:          throw new RuntimeException("(TODO) I/O interface generation: " + s.getStateKind());
		}
		return name + "_" + self + "_" + s.getTakeable().stream().sorted(IOACTION_COMPARATOR)
				.map((a) -> ActionInterfaceGenerator.getActionString(a)).collect(Collectors.joining("__"));
	}
}
