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

	public IOInterfacesGenerator(StateChannelApiGenerator apigen)
	{
		super(apigen.getJob(), apigen.getGProtocolName());
		this.apigen = apigen;

		GProtocolName fullname = apigen.getGProtocolName();
		Role self = getSelf();
		EndpointState init = this.job.getContext().getEndpointGraph(fullname, self).init;

		firstPass(new HashSet<>(), init);
		Map<EndpointState, Set<InterfaceBuilder>> preds = getPreds();
		secondPass(preds, new HashSet<>(), init);
		
		traverse(new HashSet<>(), init);

		EndpointState term = EndpointState.findTerminalState(new HashSet<>(), init);
		if (term != null)
		{
			TypeBuilder tb = this.apigen.getType(ScribSocketGenerator.GENERATED_ENDSOCKET_NAME);
			tb.addImports(getPackageName(this.gpn, self) + ".*");
			for (InterfaceBuilder ib : getPreds().get(term))
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
		String prefix = getPackageName(this.gpn, getSelf()).replace('.', '/') + "/";
		this.actions.values().stream().forEach((ib) -> output.put(prefix + ib.getName() + ".java", ib.build()));
		this.succs.values().stream().forEach((ib) -> output.put(prefix + ib.getName() + ".java", ib.build()));
		this.iostates.values().stream().forEach((tb) -> output.put(prefix + tb.getName() + ".java", tb.build()));
		return output;
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

		tb.addImports(getPackageName(this.gpn, self) + ".*");
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
			cases.addImports(getPackageName(this.gpn, self) + ".*");
		}
		
		//visited.add(s);
		for (IOAction a : s.getAcceptable())
		{
			Set<EndpointState> next = new HashSet<>(visited);  // FIXME: merged paths visited multiple times
			next.add(s);
			traverse(next, s.accept(a));
		}
	}
	
	// Factor out FSM visitor?
	private void firstPass(Set<EndpointState> visited, EndpointState s)
	{
		if (visited.contains(s) || s.isTerminal())
		{
			return;
		}
		for (IOAction a : s.getAcceptable())
		{
			if (!this.actions.containsKey(a))
			{
				InterfaceBuilder actionif = new ActionInterfaceGenerator(this.apigen, s, a).generateType();
				this.actions.put(a, actionif);
				
				InterfaceBuilder succif = new SuccessorInterfaceGenerator(this.apigen, s, a).generateType();
				this.succs.put(a, succif);
			}
			
			EndpointState succ = s.accept(a);
			putPre(succ, a);

			Set<EndpointState> tmp = new HashSet<>(visited);
			tmp.add(s);
			firstPass(tmp, succ);
		}
	}

	private void secondPass(Map<EndpointState, Set<InterfaceBuilder>> preds, Set<EndpointState> visited, EndpointState s)
	{
		if (visited.contains(s) || s.isTerminal())
		{
			return;
		}
		
		Set<InterfaceBuilder> tmp = preds.get(s);  // Successor interfaces
		String key = IOStateInterfaceGenerator.getIOStateInterfaceName(getSelf(), s);
		IOStateInterfaceGenerator iogen = null;
		InterfaceBuilder iostate;
		if (this.iostates.containsKey(key))
		{
			iostate = this.iostates.get(key);
			for (InterfaceBuilder pred : tmp)  // FIXME: factor out with IOStateInterfaceGenerator
			{
				iostate.addInterfaces(pred.getName());  // Adds Successor Interfaces to this I/O State Interface
			}
		}
		else
		{
			switch (s.getStateKind())
			{
				case OUTPUT:
					iogen = new SelectInterfaceGenerator(this.apigen, s, this.actions, tmp);
					break;
				case UNARY_INPUT:
					iogen = new ReceiveInterfaceGenerator(this.apigen, s, this.actions, tmp);
					break;
				case POLY_INPUT:
					InterfaceBuilder cases = new CaseInterfaceGenerator(this.apigen, s, this.actions, tmp).generateType();
					addIOStateInterface(cases);
					
					iogen = new BranchInterfaceGenerator(this.apigen, s, this.actions, tmp);
					break;
				case TERMINAL:
				default:
					throw new RuntimeException("TODO:");
			}
			iostate = iogen.generateType();
		}
		if (tmp != null)
		{
			for (InterfaceBuilder pred : tmp)
			{
				addSuccessorInterfaceToMethod(pred, iostate);

				X: for (MethodBuilder cast : pred.getDefaultMethods())
				{
					// Overriding every Successor I/f to methods in the I/O State I/f, even if unnecessary
					// FIXME: should be done in third pass: not all to methods in the cast may be built yet due to merge flows (e.g. Select_C_S$StartTls missing Quit cast)
					for (MethodBuilder mb2 : iostate.getDefaultMethods())  // FIXME: factor out with addSuccessorInterfaceToMethod
					{
						if (mb2.getReturn().equals(cast.getReturn()))
						{
							continue X;
						}
					}
					MethodBuilder copy = iostate.newDefaultMethod(cast.getName());
					copy.setReturn(cast.getReturn());
					cast.getParameters().stream().forEach((p) -> copy.addParameters(p));
					copy.addBodyLine(JavaBuilder.RETURN + " (" + cast.getReturn() + ") this; ");  // FIXME: factor out
					copy.addAnnotations("@Override");
				}
			}
		}
		this.iostates.put(key, iostate);
		
		for (IOAction a : s.getAcceptable())
		{
			Set<EndpointState> next = new HashSet<>(visited);
			next.add(s);
			secondPass(preds, next, s.accept(a));
		}
	}
	
	// Pre: ib is a Successor I/f for the cast IO State I/f class
	private static void addSuccessorInterfaceToMethod(InterfaceBuilder ib, InterfaceBuilder cast)
	{
		String tmp = cast.getName() + "<" + IntStream.range(0, cast.getParameters().size()).mapToObj((i) -> "?").collect(Collectors.joining(", ")) + ">";
		for (MethodBuilder def : ib.getDefaultMethods())
		{
			if (def.getReturn().equals(tmp))
			{
				return;
			}
		}
		MethodBuilder mb = ib.newDefaultMethod("to");
		mb.setReturn(tmp);
		mb.addParameters(tmp + " cast");
		mb.addBodyLine(JavaBuilder.RETURN + " (" + tmp + ") this;");
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
	private Map<EndpointState, Set<InterfaceBuilder>> getPreds()
	{
		Map<EndpointState, Set<InterfaceBuilder>> preds = new HashMap<>();
		for (EndpointState s : this.pre.keySet())
		{
			Set<InterfaceBuilder> tmp = new HashSet<>();
			for (IOAction a : this.pre.get(s))
			{
				tmp.add(this.succs.get(a));
			}
			preds.put(s, tmp);
		}
		return preds;
	}
	
	protected void addIOStateInterface(InterfaceBuilder ib)
	{
		//String prefix = getPackageName(this.gpn, this.self).replace('.', '/') + "/";
		String name = ib.getName() + ".java";
		this.iostates.put(name, ib);
	}
	
	protected Role getSelf()
	{
		return this.apigen.getSelf();
	}

	protected static String getPackageName(GProtocolName gpn, Role self)
	{
		//return SessionApiGenerator.getEndpointApiRootPackageName(gpn).replace('.', '/') + "/channels/" + self + "/ioifaces/" ;  // FIXME: factor out (e.g. StateChannelApiGenerator)
		return SessionApiGenerator.getEndpointApiRootPackageName(gpn) + ".channels." + self + ".ioifaces" ;  // FIXME: factor out (e.g. StateChannelApiGenerator)
	}
}
