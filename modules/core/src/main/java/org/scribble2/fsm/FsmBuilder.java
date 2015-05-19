package org.scribble2.fsm;

import java.util.Set;

import org.scribble2.sesstype.name.MessageId;
import org.scribble2.sesstype.name.RecVar;

public class FsmBuilder
{
	private ProtocolState init = null;
	//private Map<ProtocolState, Map<Op, ProtocolState>> edges = new HashMap<>();
	
	public FsmBuilder()
	{
		
	}

	public ProtocolState makeInit(Set<RecVar> labs)
	{
		if (this.init != null)
		{
			throw new RuntimeException("Initial state already set.");
		}
		this.init = new ProtocolState(labs);
		return this.init;
	}
	
	public ProtocolState newState(Set<RecVar> labs)
	{
		return new ProtocolState(labs);
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
		s.addEdge(op, succ);
	}
	
	public ScribbleFsm build()  // Connectedness not checked
	{
		Set<ProtocolState> terms = this.init.findTerminals();
		if (terms.size() > 1)
		{
			throw new RuntimeException("Too many terminals: " + terms);
		}
		ProtocolState term = (terms.size() == 0) ? null : terms.iterator().next();
		ScribbleFsm f = new ScribbleFsm(this.init, term);
		this.init = null;
		//this.edges.clear();
		return f;
	}
}
