package org.scribble.model.local;

import java.util.HashSet;
import java.util.Set;

import org.scribble.sesstype.name.RecVar;

@Deprecated
public class FsmBuilder
{
	private ProtocolState init = null;
	//private Map<ProtocolState, Map<Op, ProtocolState>> edges = new HashMap<>();
	private Set<ProtocolState> states = new HashSet<>();
	
	public FsmBuilder()
	{
		
	}

	public ProtocolState makeInit(Set<RecVar> labs)
	{
		//setInit(newState(labs));
		if (this.init != null)
		{
			throw new RuntimeException("Initial state already set.");
		}
		this.init = newState(labs);
		return this.init;
	}

	/*protected void setInit(ProtocolState s)
	{
		if (this.init != null)
		{
			throw new RuntimeException("Initial state already set.");
		}
		if (!this.states.contains(s))
		{
			this.states.add(s);
		}
		this.init = s;
	}*/
	
	public ProtocolState newState(Set<RecVar> labs)
	{
		ProtocolState s = null;//new ProtocolState(labs);
		this.states.add(s);
		return s;
	}

	/*public ProtocolState importState(ProtocolState s)
	{
		ProtocolState copy = newState(s.getLabels());
		importState(new HashSet<>(), s, copy);
		return copy;
	}

	private void importState(Set<ProtocolState> seen, ProtocolState curr, ProtocolState copy)
	{
		if (seen.contains(curr))
		{
			return;
		}
		seen.add(curr);
		//ProtocolState copy = newState(curr.getLabels());
		for (Entry<IOAction, ProtocolState> e : curr.getEdges().entrySet())
		{
			IOAction op = e.getKey();
			ProtocolState next = e.getValue();
			ProtocolState tmp = newState(next.getLabels());
			addEdge(copy, op, tmp);
			importState(seen, next, tmp);
		}
	}*/
	
	/*protected void addState(ProtocolState s)
	{
		this.states.add(s);
	}*/

	public void addEdge(ProtocolState s, IOAction act, ProtocolState succ)
	{
		/*Map<Op, ProtocolState> tmp = this.edges.get(s);
		if (tmp == null)	
		{
			tmp = new HashMap<>();
			this.edges.put(s, tmp);
		}
		tmp.put(op, succ);*/
		if (!this.states.contains(s) || !this.states.contains(succ))  // Guarantees s and succ are fresh states, so OK to addEdge mutate below
		{
			throw new RuntimeException("Unknown state: " + s);
			//this.states.add(s);
		}
		/*if (!this.states.contains(succ))
		{
			this.states.add(succ);
		}*/
		s.addEdge(act, succ);
	}
	
	public ScribFsm build()
	{
		ProtocolState term = validate();
		ScribFsm f = new ScribFsm(this.init, term);
		this.init = null;
		this.states.clear();
		return f;
	}
	
	private ProtocolState validate()  // Factor out as a ProtocolState method -- not quite: single init/term is an FSM property, and connected is wrt. recorded states
	{
		Set<ProtocolState> terms = this.init.findTerminals();
		if (terms.size() > 1)
		{
			throw new RuntimeException("Too many terminals: " + terms);
		}
		checkConnectedness();
		return (terms.size() == 0) ? null : terms.iterator().next();
	}
	
	private void checkConnectedness()
	{
		Set<ProtocolState> seen = new HashSet<>();
		checkConnectedness(seen, this.init);
		if (!seen.equals(this.states))
		{
			this.states.removeAll(seen);
			throw new RuntimeException("Graph not connected: " + this.states);
		}
	}

	private void checkConnectedness(Set<ProtocolState> seen, ProtocolState curr)
	{
		if (seen.contains(curr))
		{
			return;
		}
		seen.add(curr);
		for (ProtocolState succ : curr.getSuccessors())
		{
			checkConnectedness(seen, succ);
		}
	}
}
