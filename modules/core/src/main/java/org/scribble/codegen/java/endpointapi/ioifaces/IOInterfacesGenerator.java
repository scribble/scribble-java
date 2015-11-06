package org.scribble.codegen.java.endpointapi.ioifaces;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
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
import org.scribble.model.local.IOAction;
import org.scribble.model.local.Receive;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.visit.Job;

public class IOInterfacesGenerator extends ApiGenerator
{
	private final Role self;
	private final EndpointState init;

	private final Map<IOAction, InterfaceBuilder> actions = new HashMap<>();
	private final Map<IOAction, InterfaceBuilder> succs = new HashMap<>();
	private final Map<EndpointState, TypeBuilder> iostates = new HashMap<>();
	
	private final Map<EndpointState, InterfaceBuilder> cases = new HashMap<>();  // HACK
	
	private final Map<EndpointState, Set<IOAction>> pre = new HashMap<>();  // Pre set
	
	private final StateChannelApiGenerator apigen;  // FIXME: refactor
	
	public IOInterfacesGenerator(Job job, GProtocolName fullname, Role self, StateChannelApiGenerator apigen)
	{
		super(job, fullname);
		this.self = self;
		this.init = job.getContext().getEndpointGraph(fullname, self).init;
		this.apigen = apigen;
	}

	@Override
	public Map<String, String> generateApi()
	{
		firstPass(new HashSet<>(), this.init);
		Map<EndpointState, Set<InterfaceBuilder>> preds = getPreds();
		secondPass(preds, new HashSet<>(), this.init);
		
		String prefix = getPackageName(this.gpn, this.self).replace('.', '/') + "/";
		Map<String, String> ifaces = new HashMap<>();
		for (InterfaceBuilder ib : this.actions.values())
		{
			String path = prefix + ib.getName() + ".java";
			ifaces.put(path, ib.build());
			//System.out.println("1: " + path + ":\n" + ib.build());
		}
		for (InterfaceBuilder ib : this.succs.values())
		{
			String path = prefix + ib.getName() + ".java";
			ifaces.put(path, ib.build());
			//System.out.println("2: " + ib.getName() + ":\n" + ib.build());
		}
		for (TypeBuilder tb : this.iostates.values())
		{
			String path = prefix + tb.getName() + ".java";
			ifaces.put(path, tb.build());
		}
		for (InterfaceBuilder ib : this.cases.values())
		{
			String path = prefix + ib.getName() + ".java";
			ifaces.put(path, ib.build());
		}
		
		traverse(new HashSet<>(), this.init);  // FIXME: move into constructor

		EndpointState term = EndpointState.findTerminalState(new HashSet<>(), this.init);
		if (term != null)
		{
			//System.out.println("ZZZ: " + this.pre.get(EndpointState.findTerminalState(new HashSet<>(), this.init)));
			TypeBuilder tb = this.apigen.getType(ScribSocketGenerator.GEN_ENDSOCKET_CLASS);
			//for (InterfaceBuilder ib : this.pre.get(EndpointState.findTerminalState(new HashSet<>(), this.init)))
			tb.addImports(getPackageName(this.gpn, this.self) + ".*");
			for (InterfaceBuilder ib : getPreds().get(term))
			{
				String succ = ib.getName();
				tb.addInterfaces(succ);
			}
		}
		
		return ifaces;
	}
	
	private void traverse(Set<EndpointState> visited, EndpointState s)
	{
		if (visited.contains(s) || s.isTerminal())
		{
			return;
		}
		String scname = this.apigen.getSocketClassName(s);
		String ioname = IOStateInterfaceGenerator.getIOStateInterfaceName(this.self, s);
		TypeBuilder tb = this.apigen.getType(scname);
		
		// TODO: branch/select name scheme needs ordering (cf. IOStateInterfaceGenerator get name)
		String tmp = ioname + getConcreteSuccessorParameters(s);

		tb.addImports(getPackageName(this.gpn, this.self) + ".*");
		tb.addInterfaces(tmp);

		System.out.println("a: " + tb.build());
		
		Set<IOAction> as = s.getAcceptable();
		if (as.iterator().next() instanceof Receive && as.size() > 1)
		{
			TypeBuilder ib = this.apigen.getType(this.apigen.getSocketClassName(s) + "_Cases");  // FIXME: factor
			ib.addInterfaces(IOStateInterfaceGenerator.getCasesInterfaceName(tmp));
			ib.addImports(getPackageName(this.gpn, this.self) + ".*");
		}
		
		visited.add(s);
		for (IOAction a : s.getAcceptable())
		{
			traverse(visited, s.accept(a));
		}
	}

	private String getConcreteSuccessorParameters(EndpointState s)
	{
		String tmp = "<";
		
		boolean first = true;
		for (IOAction a : s.getAcceptable())
		{
			if (first)
			{
				first = false;
			}
			else
			{
				tmp += ", ";
			}
			// FIXME: ordering
			EndpointState succ = s.accept(a);
			if (succ.isTerminal())
			{
				tmp += ScribSocketGenerator.GEN_ENDSOCKET_CLASS;
			}
			else
			{
				tmp += this.apigen.getSocketClassName(succ);
			}
		}
		tmp += ">";
		return tmp;
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
				visited.add(s);

				InterfaceBuilder actionif = new ActionInterfaceGenerator(this.apigen, s, a).generateType();
				this.actions.put(a, actionif);
				
				//System.out.println("\nz:" + actionif.build());

				InterfaceBuilder succif = new SuccessorInterfaceGenerator(this.apigen, s, a).generateType();
				this.succs.put(a, succif);
				
				EndpointState succ = s.accept(a);
				putPre(succ, a);
				firstPass(visited, succ);
			}
		}
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

	private void secondPass(Map<EndpointState, Set<InterfaceBuilder>> preds, Set<EndpointState> visited, EndpointState s)
	{
		if (visited.contains(s) || s.isTerminal())
		{
			return;
		}
		
		Set<InterfaceBuilder> tmp = preds.get(s);  // Successor interfaces
		IOStateInterfaceGenerator iogen = new IOStateInterfaceGenerator(this.apigen, s, this.actions, tmp);
		InterfaceBuilder iostate = iogen.generateType();
		if (tmp != null)
		{
			for (InterfaceBuilder ib : tmp)
			{
				addSuccessorInterfaceToMethod(ib, iostate);
				//System.out.println("\na:" + ib.build());
			}
		}
		this.iostates.put(s, iostate);

		//System.out.println("\nb:" + iostate.build());
		IOAction first = s.getAcceptable().iterator().next();
		if (first instanceof Receive && s.getAcceptable().size() > 1)
		{
			//System.out.println("\nc:" + iogen.getCasesInterface().build());
			this.cases.put(s, iogen.getCasesInterface());
		}
		
		visited.add(s);
		for (IOAction a : s.getAcceptable())
		{
			secondPass(preds, visited, s.accept(a));
		}
	}
	
	// Pre: ib is a successor interface for the cast class
	private static void addSuccessorInterfaceToMethod(InterfaceBuilder ib, InterfaceBuilder cast)
	{
		String tmp = cast.getName() + "<" + IntStream.range(0, cast.getParameters().size()).mapToObj((i) -> "?").collect(Collectors.joining(", ")) + ">";
		MethodBuilder mb = ib.newDefaultMethod("to");
		mb.setReturn(tmp);
		mb.addParameters(tmp + " cast");
		mb.addBodyLine(JavaBuilder.RETURN + " (" + tmp + ") this;");
	}

	protected static String getPackageName(GProtocolName gpn, Role self)
	{
		//return SessionApiGenerator.getEndpointApiRootPackageName(gpn).replace('.', '/') + "/channels/" + self + "/ioifaces/" ;  // FIXME: factor out (e.g. StateChannelApiGenerator)
		return SessionApiGenerator.getEndpointApiRootPackageName(gpn) + ".channels." + self + ".ioifaces" ;  // FIXME: factor out (e.g. StateChannelApiGenerator)
	}
}
