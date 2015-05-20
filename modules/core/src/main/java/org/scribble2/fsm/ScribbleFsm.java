package org.scribble2.fsm;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

public class ScribbleFsm
{
	public final ProtocolState init;
	public final ProtocolState term;
	
	//private final Set<ProtocolState> preterms = new HashSet<>();
	
	public ScribbleFsm(ProtocolState init, ProtocolState term)
	{
		this.init = init;
		this.term = term;
		/*if (term != null)
		{
			findPreTerminals(new HashSet<>(), init);
		}*/
	}
	
	public ScribbleFsm stitch(ScribbleFsm f)
	{
		if (this.term == null || this.init.equals(this.term))
		{
			throw new RuntimeException("Cannot stitch onto FSM: " + this);
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
		ProtocolState init = b.makeInit(this.init.getLabels());
		ProtocolState swap = b.newState(f.init.getLabels());
		stitch(b, new HashSet<>(), this.init, init, this.term, swap);
		stitch(b, new HashSet<>(), f.init, swap, f.term, f.term);  // Essentially copy (could factor out as aux) -- unnecessary as PrototolStates are immutable, but would need to change FsmBuilder validation to record all newly reachable states
		return b.build();
	}
	
	private static void stitch(FsmBuilder b, Set<ProtocolState> seen, ProtocolState curr, ProtocolState copy, ProtocolState term, ProtocolState swap)
	{
		if (seen.contains(curr))
		{
			return;
		}
		seen.add(curr);
		for (Entry<IOAction, ProtocolState> e : curr.getEdges().entrySet())
		{
			IOAction op = e.getKey();
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
	
	@Override
	public String toString()
	{
		//return this.init.toString();
		return this.init.toDot();
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
