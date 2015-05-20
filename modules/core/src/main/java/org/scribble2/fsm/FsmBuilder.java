package org.scribble2.fsm;

import java.util.HashSet;
import java.util.Set;

import org.scribble2.sesstype.name.MessageId;
import org.scribble2.sesstype.name.RecVar;

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
		if (this.init != null)
		{
			throw new RuntimeException("Initial state already set.");
		}
		this.init = newState(labs);
		return this.init;
	}
	
	public ProtocolState newState(Set<RecVar> labs)
	{
		ProtocolState s = new ProtocolState(labs);
		this.states.add(s);
		return s;
	}
	
	public void addEdge(ProtocolState s, MessageId op, ProtocolState succ)
	{
		/*Map<Op, ProtocolState> tmp = this.edges.get(s);
		if (tmp == null)	
		{
			tmp = new HashMap<>();
			this.edges.put(s, tmp);
		}
		tmp.put(op, succ);*/
		if (!this.states.contains(s))
		{
			throw new RuntimeException("Unknown state: " + s);
		}
		if (!this.states.contains(succ))
		{
			this.states.add(succ);
		}
		s.addEdge(op, succ);
	}
	
	public ScribbleFsm build()
	{
		ProtocolState term = validate();
		ScribbleFsm f = new ScribbleFsm(this.init, term);
		this.init = null;
		this.states.clear();
		return f;
	}
	
	private ProtocolState validate()
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
			throw new RuntimeException("Graph not connected: " + this.states.removeAll(seen));
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
