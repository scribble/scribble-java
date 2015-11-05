package org.scribble.codegen.java.endpointapi.ioifaces;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.codegen.java.endpointapi.ApiGenerator;
import org.scribble.codegen.java.endpointapi.SessionApiGenerator;
import org.scribble.codegen.java.endpointapi.StateChannelApiGenerator;
import org.scribble.codegen.java.util.InterfaceBuilder;
import org.scribble.codegen.java.util.TypeBuilder;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
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
		return ifaces;
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

				InterfaceBuilder actionif = new ActionInterfaceGenerator(this.apigen, a).generateType();
				this.actions.put(a, actionif);
				
				InterfaceBuilder succif = new SuccessorInterfaceGenerator(this.apigen, a).generateType();
				this.succs.put(a,  succif);
				
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
		
		InterfaceBuilder ib = new IOStateInterfaceGenerator(apigen, s, this.actions, preds.get(s)).generateType();
		
		System.out.println("\n" + ib.build());
		
		visited.add(s);
		for (IOAction a : s.getAcceptable())
		{
			secondPass(preds, visited, s.accept(a));
		}
	}

	protected static String getPackageName(GProtocolName gpn, Role self)
	{
		//return SessionApiGenerator.getEndpointApiRootPackageName(gpn).replace('.', '/') + "/channels/" + self + "/ioifaces/" ;  // FIXME: factor out (e.g. StateChannelApiGenerator)
		return SessionApiGenerator.getEndpointApiRootPackageName(gpn) + ".channels." + self + ".ioifaces" ;  // FIXME: factor out (e.g. StateChannelApiGenerator)
	}
}
