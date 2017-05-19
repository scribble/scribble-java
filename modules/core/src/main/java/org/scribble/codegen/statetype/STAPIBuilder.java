package org.scribble.codegen.statetype;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.model.MState;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EState;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;

public abstract class STAPIBuilder
{
	public final GProtocolName gpn;
	public final Role role;

	public final EGraph graph;

	private final STOutputStateBuilder ob;
	private final STReceiveStateBuilder rb;
	private final STEndStateBuilder eb;

	private Map<Integer, String> names = new HashMap<>();
	
	protected STAPIBuilder(GProtocolName gpn, Role role, EGraph graph, STOutputStateBuilder ob, STReceiveStateBuilder rb, STEndStateBuilder eb)
	{
		this.gpn = gpn;
		this.role = role;
		this.graph = graph;
		this.ob = ob;
		this.rb = rb;
		this.eb = eb;
	}
	
	//public Map<EState, String> build()
	public Map<String, String> build()
	{
		Map<String, String> api = new HashMap<>();
		Set<EState> states = new HashSet<>();
		states.add(this.graph.init);
		states.addAll(MState.getReachableStates(this.graph.init));
		for (EState s : states)
		{
			switch (s.getStateKind())
			{
				case ACCEPT:      throw new RuntimeException("TODO");
				case OUTPUT:      api.put(getFilePath(s), this.ob.build(this, s)); break;
				case POLY_INPUT:  throw new RuntimeException("TODO");
				case TERMINAL:    api.put(getFilePath(s), this.eb.build(this, s)); break;
				case UNARY_INPUT: api.put(getFilePath(s), this.rb.build(this, s)); break;
				case WRAP_SERVER: throw new RuntimeException("TODO");
				default:          throw new RuntimeException("Shouldn't get in here: " + s);
			}
		}
		return api;
	}
	
	public abstract String getFilePath(EState s);
	
	public String getSTStateName(EState s)
	{
		String name = this.names.get(s.id);
		if (name == null)
		{
			name = makeSTStateName(s);
			this.names.put(s.id, name);
		}
		return name;
	}
	
	protected abstract String makeSTStateName(EState s);
}
