package org.scribble2.fsm;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

import org.scribble2.sesstype.name.Op;

public class ScribbleFSM
{
	public final ProtocolState init;
	public final ProtocolState term;
	
	//private final Set<ProtocolState> preterms = new HashSet<>();
	
	public ScribbleFSM(ProtocolState init, ProtocolState term)
	{
		this.init = init;
		this.term = term;
		/*if (term != null)
		{
			findPreTerminals(new HashSet<>(), init);
		}*/
	}
	
	public ScribbleFSM stitch(ScribbleFSM f)
	{
		if (this.term == null)
		{
			throw new RuntimeException("Cannot stitch onto FSM with no terminal: " + this);
		}
		/*ProtocolState init = this.init.copy();
		ProtocolState copy = f.init.copy();
		Set<ProtocolState> preterms = new HashSet<>();
		findPreTerminals(new HashSet<>(), init, preterms);
		for (ProtocolState s : preterms)
		{
			for (Entry<Op, ProtocolState> e : s.getEdges().entrySet())
			{
				if (e.getValue().isTerminal())	
				{
					s.edges.put(e.getKey(), copy);
				}
			}
		}
		return new ScribbleFSM(init, copy.findTerminals().iterator().next());  // FIXME: check single terminal?*/
		FsmBuilder b = new FsmBuilder(); 
		ProtocolState init = b.newState(this.init.getLabels());
		ProtocolState swap = b.newState(f.init.getLabels());
		b.addInit(init);
		stitch(b, new HashSet<>(), this.init, init, this.term, swap);
		stitch(b, new HashSet<>(), f.init, swap, f.term, f.term);  // Essentially copy
		return b.build();
	}
	
	private static void stitch(FsmBuilder b, Set<ProtocolState> seen, ProtocolState curr, ProtocolState copy, ProtocolState term, ProtocolState swap)
	{
		if (seen.contains(curr))
		{
			return;
		}
		seen.add(curr);
		for (Entry<Op, ProtocolState> e : curr.getEdges().entrySet())
		{
			Op op = e.getKey();
			ProtocolState next = e.getValue();
			if (next.equals(term))
			{
				b.addEdge(copy, op, swap);
			}
			else
			{
				ProtocolState tmp = b.newState(next.getLabels());
				b.addEdge(copy, op, tmp);
				stitch(b, seen, next, tmp, term, swap);
			}
		}
	}
	
	/*private static void findPreTerminals(Set<ProtocolState> seen, ProtocolState curr, Set<ProtocolState> preterms)
	{
		if (seen.contains(curr))
		{
			return;
		}
		seen.add(curr);
		for (ProtocolState succ : curr.getSuccessors())
		{
			//if (e.getValue().equals(term))
			if (succ.isTerminal())
			{
				preterms.add(curr);
			}
			else
			{
				findPreTerminals(seen, succ, preterms);
			}
		}
	}*/
}
