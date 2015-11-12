package org.scribble.codegen.java.endpointapi.ioifaces;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.codegen.java.endpointapi.ApiGenerator;
import org.scribble.codegen.java.endpointapi.CaseSocketGenerator;
import org.scribble.codegen.java.endpointapi.ScribSocketGenerator;
import org.scribble.codegen.java.endpointapi.SessionApiGenerator;
import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.JavaBuilder;
import org.scribble.codegen.java.util.MethodBuilder;
import org.scribble.codegen.java.util.TypeBuilder;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.EndpointState.Kind;
import org.scribble.model.local.IOAction;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;

// Cf. StateChannelApiGenerator
public class IOInterfacesGenerator extends ApiGenerator
{
	protected final StateChannelApiGenerator apigen;

	private final Map<IOAction, InterfaceBuilder> actions = new HashMap<>();
	private final Map<IOAction, InterfaceBuilder> succs = new HashMap<>();
	private final Map<String, InterfaceBuilder> iostates = new HashMap<>();  // Key is interface simple name
	
	private final Map<EndpointState, Set<IOAction>> preActions = new HashMap<>();  // Pre set: the actions that lead to each state
	private final Map<EndpointState, Set<InterfaceBuilder>> preds = new HashMap<>();

	public IOInterfacesGenerator(StateChannelApiGenerator apigen)
	{
		super(apigen.getJob(), apigen.getGProtocolName());
		this.apigen = apigen;

		GProtocolName fullname = apigen.getGProtocolName();
		Role self = getSelf();
		EndpointState init = this.job.getContext().getEndpointGraph(fullname, self).init;

		generateActionAndSuccessorInterfacesAndCollectPreActions(new HashSet<>(), init);
		collectPreds();
		generateIOStateInterfacesFirstPass(new HashSet<>(), init);
		generateIOStateInterfacesSecondPass(new HashSet<>(), init);
		generateHandleInterfaces(new HashSet<>(), init);
		addIOStateInterfacesToStateChannels(new HashSet<>(), init);  // Except EndSocket

		// Successor I/f's for EndSocket
		EndpointState term = EndpointState.findTerminalState(new HashSet<>(), init);
		if (term != null)
		{
			TypeBuilder tb = this.apigen.getType(ScribSocketGenerator.GENERATED_ENDSOCKET_NAME);
			tb.addImports(getIOInterfacePackageName(this.gpn, self) + ".*");
			for (InterfaceBuilder ib : this.preds.get(term))
			{
				tb.addInterfaces(ib.getName());
			}
		}
	}

	@Override
	public Map<String, String> generateApi()
	{
		Map<String, String> output = new HashMap<>();
		String prefix = getIOInterfacePackageName(this.gpn, getSelf()).replace('.', '/') + "/";
		this.actions.values().stream().forEach((ib) -> output.put(prefix + ib.getName() + ".java", ib.build()));
		this.succs.values().stream().forEach((ib) -> output.put(prefix + ib.getName() + ".java", ib.build()));
		this.iostates.values().stream().forEach((tb) -> output.put(prefix + tb.getName() + ".java", tb.build()));
		return output;
	}
	
	// Factor out FSM visitor?
	private void generateActionAndSuccessorInterfacesAndCollectPreActions(Set<EndpointState> visited, EndpointState s)
	{
		if (visited.contains(s) || s.isTerminal())
		{
			return;
		}
		visited.add(s);
		for (IOAction a : s.getAcceptable())
		{
			if (!this.actions.containsKey(a))
			{
				this.actions.put(a, new ActionInterfaceGenerator(this.apigen, s, a).generateType());
				this.succs.put(a, new SuccessorInterfaceGenerator(this.apigen, s, a).generateType());
			}
			
			EndpointState succ = s.accept(a);
			putPreAction(succ, a);

			generateActionAndSuccessorInterfacesAndCollectPreActions(visited, succ);
			/*Set<EndpointState> tmp = new HashSet<>(visited);
			tmp.add(s);  // merge paths visited multiple times
			generateActionAndSuccessorInterfacesAndCollectPredecessors(tmp, succ);*/
		}
	}

	// Generates partial IO State Interfaces
	private void generateIOStateInterfacesFirstPass(Set<EndpointState> visited, EndpointState s)
	{
		if (visited.contains(s) || s.isTerminal())
		{
			return;
		}
		
		String key = IOStateInterfaceGenerator.getIOStateInterfaceName(getSelf(), s);
		if (!this.iostates.containsKey(key))  // Don't generate if one already exists (up to this pass, repeats will all be the same, i.e. name, Action Interfaces, and action-succ parameters)
		{
			// Make the partial I/O State Interface (Successor Interfaces and cast methods added later -- different states may share same State I/f)
			IOStateInterfaceGenerator iogen = null;
			InterfaceBuilder iostate;
			switch (s.getStateKind())
			{
				case OUTPUT:
					iogen = new SelectInterfaceGenerator(this.apigen, this.actions, s);
					break;
				case UNARY_INPUT:
					iogen = new ReceiveInterfaceGenerator(this.apigen, this.actions, s);
					break;
				case POLY_INPUT:
					InterfaceBuilder cases = new CaseInterfaceGenerator(this.apigen, this.actions, s).generateType();
					this.iostates.put(cases.getName(), cases);
					iogen = new BranchInterfaceGenerator(this.apigen, this.actions, s);
					break;
				case TERMINAL:
				default:
					throw new RuntimeException("TODO:");
			}
			iostate = iogen.generateType();
			this.iostates.put(key, iostate);
		}
		
		visited.add(s);
		for (IOAction a : s.getAcceptable())
		{
			generateIOStateInterfacesFirstPass(visited, s.accept(a));
		}
	}

	// Adds Successor Interfaces and to-cast methods to IO State Interfaces
	private void generateIOStateInterfacesSecondPass(Set<EndpointState> visited, EndpointState s)
	{
		if (visited.contains(s) || s.isTerminal())
		{
			return;
		}

		Set<InterfaceBuilder> succifs = this.preds.get(s);  // Successor interfaces to be implemented by IO State Interface of s
		if (succifs != null)
		{
			InterfaceBuilder iostate = this.iostates.get(IOStateInterfaceGenerator.getIOStateInterfaceName(getSelf(), s));
			for (InterfaceBuilder pred : succifs)  // pred is a Successor Interface for the state s 
			{
				// May already have "visited" this State I/f for a different state -- Interfaces recorded as a Set, to support repeated adds
				iostate.addInterfaces(pred.getName());  // Adds Successor Interfaces to this I/O State Interface
				String ret = iostate.getName() + "<"
						+ IntStream.range(0, iostate.getParameters().size()).mapToObj((i) -> "?").collect(Collectors.joining(", ")) + ">";
				addToCastMethod(pred, ret);
				
				for (MethodBuilder cast : pred.getDefaultMethods())  // cast is a default method (a to cast -- hacky) in pred
				{
					// Overriding every Successor I/f to methods in the I/O State I/f, even if unnecessary
					MethodBuilder mb = addToCastMethod(iostate, cast.getReturn());
					if (mb != null)
					{
						mb.addAnnotations("@Override");
					}
				}
			}
		}
		
		visited.add(s);
		for (IOAction a : s.getAcceptable())
		{
			generateIOStateInterfacesSecondPass(visited, s.accept(a));
		}
	}

	private void generateHandleInterfaces(Set<EndpointState> visited, EndpointState s)
	{
		if (visited.contains(s) || s.isTerminal())
		{
			return;
		}
		
		// TODO
		
		visited.add(s);
		for (IOAction a : s.getAcceptable())
		{
			addIOStateInterfacesToStateChannels(visited, s.accept(a));
		}
	}
	
	// Except EndSocket
	private void addIOStateInterfacesToStateChannels(Set<EndpointState> visited, EndpointState s)
	{
		if (visited.contains(s) || s.isTerminal())
		{
			return;
		}

		Role self = getSelf();
		String scname = this.apigen.getSocketClassName(s);
		String ioname = IOStateInterfaceGenerator.getIOStateInterfaceName(self, s);
		TypeBuilder tb = this.apigen.getType(scname);
		
		// Add I/O State Interface to each ScribSocket (except CaseSocket)
		// Do here (not inside I/O State Interface generators) because multiple states can share the same I/O State Interface
		tb.addImports(getIOInterfacePackageName(this.gpn, self) + ".*");
		tb.addInterfaces(ioname + getConcreteSuccessorParameters(s));
		
		InterfaceBuilder iostate = this.iostates.get(ioname);
		MethodBuilder mb = addToCastMethod(iostate, scname);
		if (mb != null)
		{
			iostate.addImports(SessionApiGenerator.getStateChannelPackageName(this.gpn, self) + ".*");
		}

		if (s.getStateKind() == Kind.POLY_INPUT)
		{
			// Add CaseInterface to each CaseSocket
			TypeBuilder cases = this.apigen.getType(CaseSocketGenerator.getCaseSocketName(this.apigen.getSocketClassName(s)));
			cases.addImports(getIOInterfacePackageName(this.gpn, self) + ".*");
			cases.addInterfaces(CaseInterfaceGenerator.getCasesInterfaceName(self, s) + getConcreteSuccessorParameters(s));
		}
		
		visited.add(s);
		for (IOAction a : s.getAcceptable())
		{
			addIOStateInterfacesToStateChannels(visited, s.accept(a));
		}
	}
	
	// Pre: ib is a Successor I/f for the cast IO State I/f
	// Returns MethodBuilder, or null if none built
	private static MethodBuilder addToCastMethod(InterfaceBuilder ib, String ret)
	{
		if (ib.getDefaultMethods().stream().filter((def) -> def.getReturn().equals(ret)).count() > 0)
		{
			// Merge states entered from multiple paths, don't want to add cast multiple times -- still true for this case?
			// But duplicate cast check cast still needed anyway?
			return null;
		}
		MethodBuilder mb = ib.newDefaultMethod("to");
		mb.setReturn(ret);
		mb.addParameters(ret + " cast");
		mb.addBodyLine(JavaBuilder.RETURN + " (" + ret + ") this;");
		return mb;
	}

	private String getConcreteSuccessorParameters(EndpointState s)
	{
		Function<EndpointState, String> getSuccName = (succ) ->
				(succ.isTerminal())
						? ScribSocketGenerator.GENERATED_ENDSOCKET_NAME
						: this.apigen.getSocketClassName(succ);

		return "<" +
				s.getAcceptable().stream().sorted(IOStateInterfaceGenerator.IOACTION_COMPARATOR)
						.map((a) -> getSuccName.apply(s.accept(a))).collect(Collectors.joining(", "))
				+ ">";
	}
	
	private void putPreAction(EndpointState s, IOAction a)
	{
		Set<IOAction> tmp = this.preActions.get(s);
		if (tmp == null)
		{
			tmp = new LinkedHashSet<>();
			this.preActions.put(s, tmp);
		}
		tmp.add(a);
	}
	
	// Successor I/f's to be implemented by each I/O State I/f
	private void collectPreds()
	{
		for (EndpointState s : this.preActions.keySet())
		{
			Set<InterfaceBuilder> tmp = new HashSet<>();
			for (IOAction a : this.preActions.get(s))
			{
				tmp.add(this.succs.get(a));
			}
			this.preds.put(s, tmp);
		}
	}
	
	protected Role getSelf()
	{
		return this.apigen.getSelf();
	}

	protected static String getIOInterfacePackageName(GProtocolName gpn, Role self)
	{
		return SessionApiGenerator.getStateChannelPackageName(gpn, self) + ".ioifaces";
	}
	
	protected InterfaceBuilder getIOStateInterface(String name)
	{
		return this.iostates.get(name);
	}
}
