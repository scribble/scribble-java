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
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;

// Cf. ScribSocketGenerator
public abstract class IOStateInterfaceGenerator extends IOInterfaceGenerator
{
	protected static final Comparator<IOAction> IOACTION_COMPARATOR = new Comparator<IOAction>()
			{
				@Override
				public int compare(IOAction a1, IOAction a2)
				{
					return ActionInterfaceGenerator.getActionInterfaceName(a1).compareTo(ActionInterfaceGenerator.getActionInterfaceName(a2));
				}
			};

	protected final Map<IOAction, InterfaceBuilder> actions;
	protected final Set<InterfaceBuilder> preds;
	//private InterfaceBuilder cases;  // HACK
	
	protected final InterfaceBuilder ib = new InterfaceBuilder();

	// Preds can be null
	public IOStateInterfaceGenerator(StateChannelApiGenerator apigen, EndpointState curr, Map<IOAction, InterfaceBuilder> actions, Set<InterfaceBuilder> preds)
	{
		super(apigen, curr);
		this.actions = Collections.unmodifiableMap(actions);
		this.preds = (preds == null) ? Collections.unmodifiableSet(Collections.emptySet()) : Collections.unmodifiableSet(preds);
	}
	
	@Override
	public InterfaceBuilder generateType()
	{
		constructInterface();
		return this.ib;
	}

	protected void constructInterface()
	{
		addHeader();
		addSuccessorParamsAndActionInterfaces();
		addSuccessorInterfaces();
		addCastField();
	}

	protected void addHeader()
	{
		GProtocolName gpn = this.apigen.getGProtocolName();
		Role self = this.apigen.getSelf();
		String packname = IOInterfacesGenerator.getPackageName(gpn, self);
		String ifname = getIOStateInterfaceName(self, this.curr);

		this.ib.setName(ifname);
		this.ib.setPackage(packname);
		this.ib.addImports(SessionApiGenerator.getRolesPackageName(gpn) + ".*");
		this.ib.addModifiers(JavaBuilder.PUBLIC);
	}
	
	protected void addCastField()
	{
		String ifname = getIOStateInterfaceName(this.apigen.getSelf(), this.curr);
		Set<IOAction> as = this.curr.getAcceptable();

		FieldBuilder cast = this.ib.newField("cast");
		cast.addModifiers(TypeBuilder.PUBLIC, TypeBuilder.STATIC, TypeBuilder.FINAL);
		cast.setType(ifname + "<" + IntStream.range(1, as.size()+1).mapToObj((i) -> "?").collect(Collectors.joining(", ")) + ">");  // FIXME: factor out
		cast.setExpression("null");
	}

	protected void addSuccessorParamsAndActionInterfaces()
	{
		int i = 1;
		for (IOAction a : this.curr.getAcceptable().stream().sorted(IOACTION_COMPARATOR).collect(Collectors.toList()))
		{
			String actif = this.actions.get(a).getName();
			this.ib.addParameters("__Succ" + i + " extends " + SuccessorInterfaceGenerator.getSuccessorInterfaceName(this.curr, a));
			this.ib.addInterfaces(actif + "<__Succ" + i + ">");
			i++;
		}
	}

	protected void addSuccessorInterfaces()
	{
		if (this.preds != null)
		{
			for (InterfaceBuilder pred : this.preds)
			{
				this.ib.addInterfaces(pred.getName());  // Adds Successor Interfaces to this I/O State Interface
			}
		}
	}

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
		}
		return name + "_" + self + "_" + s.getAcceptable().stream().sorted(IOACTION_COMPARATOR)
				.map((a) -> ActionInterfaceGenerator.getActionString(a)).collect(Collectors.joining("__"));
	}
}
