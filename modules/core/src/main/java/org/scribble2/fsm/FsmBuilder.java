package org.scribble2.fsm;

import java.util.Set;

import org.scribble2.sesstype.name.Op;
import org.scribble2.sesstype.name.RecVar;

public class FsmBuilder
{
	private ProtocolState init = null;
	//private Map<ProtocolState, Map<Op, ProtocolState>> edges = new HashMap<>();
	
	public FsmBuilder()
	{
		
	}

	public void addInit(ProtocolState init)
	{
		if (this.init != null)
		{
			throw new RuntimeException("Initial state already set.");
		}
		this.init = init;
	}
	
	public ProtocolState newState(Set<RecVar> labs)
	{
		return new ProtocolState(labs);
	}
	
	public void addEdge(ProtocolState s, Op op, ProtocolState succ)
	{
		/*Map<Op, ProtocolState> tmp = this.edges.get(s);
		if (tmp == null)	
		{
			tmp = new HashMap<>();
			this.edges.put(s, tmp);
		}
		tmp.put(op, succ);*/
		s.addEdge(op, succ);
	}
	
	public ScribbleFSM build()  // Connectedness not checked
	{
		Set<ProtocolState> terms = this.init.findTerminals();
		if (terms.size() > 1)
		{
			throw new RuntimeException("Too many terminals: " + terms);
		}
		ProtocolState term = (terms.size() == 0) ? null : terms.iterator().next();
		ScribbleFSM f = new ScribbleFSM(this.init, term);
		this.init = null;
		//this.edges.clear();
		return f;
	}
}
