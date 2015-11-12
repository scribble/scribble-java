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
	private final StateChannelApiGenerator apigen;

	private final Map<IOAction, InterfaceBuilder> actions = new HashMap<>();
	private final Map<IOAction, InterfaceBuilder> succs = new HashMap<>();
	private final Map<String, InterfaceBuilder> iostates = new HashMap<>();  // Key is interface simple name
	
	private final Map<EndpointState, Set<IOAction>> pre = new HashMap<>();  // Pre set: the actions that lead to each state
	private final Map<EndpointState, Set<InterfaceBuilder>> preds = new HashMap<>();

	public IOInterfacesGenerator(StateChannelApiGenerator apigen)
	{
		super(apigen.getJob(), apigen.getGProtocolName());
		this.apigen = apigen;

		GProtocolName fullname = apigen.getGProtocolName();
		Role self = getSelf();
		EndpointState init = this.job.getContext().getEndpointGraph(fullname, self).init;

		generateActionAndSuccessorInterfacesAndCollectPredecessors(new HashSet<>(), init);
		//Map<EndpointState, Set<InterfaceBuilder>> preds = getPreds();
		setPreds();
		generateIOStateInterfacesFirstPass(new HashSet<>(), init);
		generateIOStateInterfacesSecondPass(new HashSet<>(), init);
		
		traverse(new HashSet<>(), init);

		EndpointState term = EndpointState.findTerminalState(new HashSet<>(), init);
		if (term != null)
		{
			TypeBuilder tb = this.apigen.getType(ScribSocketGenerator.GENERATED_ENDSOCKET_NAME);
			tb.addImports(getIOInterfacePackageName(this.gpn, self) + ".*");
			//for (InterfaceBuilder ib : getPreds().get(term))
			for (InterfaceBuilder ib : this.preds.get(term))
			{
				String succ = ib.getName();
				tb.addInterfaces(succ);
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
	private void generateActionAndSuccessorInterfacesAndCollectPredecessors(Set<EndpointState> visited, EndpointState s)
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
			putPre(succ, a);

			generateActionAndSuccessorInterfacesAndCollectPredecessors(visited, succ);
			/*Set<EndpointState> tmp = new HashSet<>(visited);
			tmp.add(s);  // FIXME? merge paths visited multiple times
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
		if (!this.iostates.containsKey(key))
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
			/*Set<EndpointState> next = new HashSet<>(visited);
			next.add(s);
			secondPass(next, s.accept(a));*/
		}
	}

	// Adds Sucessor Interfaces and to-cast methods to IO State Interfaces
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
					addToCastMethod(iostate, cast.getReturn());
					// TODO: add @Override
				}
			}
		}
		
		visited.add(s);
		for (IOAction a : s.getAcceptable())
		{
			generateIOStateInterfacesSecondPass(visited, s.accept(a));
			/*Set<EndpointState> next = new HashSet<>(visited);
			next.add(s);
			thirdPass(next, s.accept(a));*/
		}
	}
	
	private void traverse(Set<EndpointState> visited, EndpointState s)
	{
		Role self = getSelf();
		
		if (visited.contains(s) || s.isTerminal())
		{
			return;
		}
		String scname = this.apigen.getSocketClassName(s);
		String ioname = IOStateInterfaceGenerator.getIOStateInterfaceName(self, s);
		TypeBuilder tb = this.apigen.getType(scname);
		
		String tmp = ioname + getConcreteSuccessorParameters(s);

		tb.addImports(getIOInterfacePackageName(this.gpn, self) + ".*");
		tb.addInterfaces(tmp);
		
		InterfaceBuilder iostate = this.iostates.get(ioname);
		if (iostate.getDefaultMethods().stream().filter((def) -> def.getReturn().equals(scname)).count() == 0) 
			// Merge states entered from multiple paths, don't want to add cast multiple times
		{
			iostate.addImports(SessionApiGenerator.getStateChannelPackageName(this.gpn, self) + ".*");
			MethodBuilder to = iostate.newDefaultMethod("to");
			to.addParameters(scname + " cast");
			to.setReturn(scname);
			to.addBodyLine(JavaBuilder.RETURN + " (" + scname + ") this;");
		}

		if (s.getStateKind() == Kind.POLY_INPUT)
		{
			TypeBuilder cases = this.apigen.getType(this.apigen.getSocketClassName(s) + "_Cases");  // FIXME: factor out
			cases.addInterfaces(CaseInterfaceGenerator.getCasesInterfaceName(self, s) + getConcreteSuccessorParameters(s));
			cases.addImports(getIOInterfacePackageName(this.gpn, self) + ".*");
		}
		
		visited.add(s);
		for (IOAction a : s.getAcceptable())
		{
			traverse(visited, s.accept(a));
			/*Set<EndpointState> next = new HashSet<>(visited);
			next.add(s);
			traverse(next, s.accept(a));*/
		}
	}
	
	// Pre: ib is a Successor I/f for the cast IO State I/f
	private static void addToCastMethod(InterfaceBuilder succif, String ret)
	{
		if (succif.getDefaultMethods().stream().filter((def) -> def.getReturn().equals(ret)).count() > 0)
		{
			return;
		}
		MethodBuilder mb = succif.newDefaultMethod("to");
		mb.setReturn(ret);
		mb.addParameters(ret + " cast");
		mb.addBodyLine(JavaBuilder.RETURN + " (" + ret + ") this;");
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
	
	private void putPre(EndpointState s, IOAction a)
	{
		Set<IOAction> tmp = this.pre.get(s);
		if (tmp == null)
		{
			tmp = new LinkedHashSet<>();
			this.pre.put(s, tmp);
		}
		tmp.add(a);
	}
	
	// Successor interfaces
	//private Map<EndpointState, Set<InterfaceBuilder>> getPreds()
	private void setPreds()
	{
		//Map<EndpointState, Set<InterfaceBuilder>> preds = new HashMap<>();
		for (EndpointState s : this.pre.keySet())
		{
			Set<InterfaceBuilder> tmp = new HashSet<>();
			for (IOAction a : this.pre.get(s))
			{
				tmp.add(this.succs.get(a));
			}
			this.preds.put(s, tmp);
		}
		//return preds;
	}
	
	protected Role getSelf()
	{
		return this.apigen.getSelf();
	}

	protected static String getIOInterfacePackageName(GProtocolName gpn, Role self)
	{
		return SessionApiGenerator.getStateChannelPackageName(gpn, self) + ".ioifaces";
	}
}
