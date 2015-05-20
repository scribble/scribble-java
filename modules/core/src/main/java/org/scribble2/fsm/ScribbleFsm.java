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
	
	public ScribbleFsm fstitch(ScribbleFsm f)
	{
		if (this.term == null || this.init.equals(this.term))
		{
			throw new RuntimeException("Cannot stitch onto FSM: " + this);
		}
		FsmBuilder b = new FsmBuilder(); 
		ProtocolState init = b.makeInit(this.init.getLabels());
		ProtocolState swap = b.newState(f.init.getLabels());
		fstitch(b, new HashSet<>(), this.init, init, this.term, swap);
		ProtocolState tmp = b.newState(f.term.getLabels());
		fstitch(b, new HashSet<>(), f.init, swap, f.term, tmp);  // Essentially copy (could factor out as aux) -- unnecessary as PrototolStates are immutable, but would need to change FsmBuilder validation to record all newly reachable states
		return b.build();
	}
	
	private static void fstitch(FsmBuilder b, Set<ProtocolState> seen, ProtocolState curr, ProtocolState copy, ProtocolState term, ProtocolState swap)
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
				fstitch(b, seen, next, tmp, term, swap);
			}
		}
	}
	
	public ScribbleFsm embed(ProtocolState init, ScribbleFsm f, ProtocolState term)
	{
		if (f.term == null)
		{
			throw new RuntimeException("Cannot embed FSM: " + f);
		}
		/*FsmBuilder b = new FsmBuilder();
		b.setInit(init);
		b.addState(term);
		for (Entry<IOAction, ProtocolState> e : f.init.getEdges().entrySet())
		{
			//ProtocolState tmp = b.importState(e.getValue());
			ProtocolState succ = e.getValue();
			ProtocolState tmp = b.newState(succ.getLabels());
			b.addEdge(init, e.getKey(), tmp);
			fstitch(b, new HashSet<>(), succ, tmp, f.term, term);
		}
		return b.build();*/
		for (Entry<IOAction, ProtocolState> e : f.init.getEdges().entrySet())
		{
			init.addEdge(e.getKey(), e.getValue());
		}
		Set<ProtocolState> preterms = new HashSet<ProtocolState>();
		findPreTerminals(new HashSet<>(), init, preterms);
		for (ProtocolState s : preterms)
		{
			for (IOAction a : s.getEdges().keySet())
			{
				if (s.accept(a).isTerminal())
				{
					s.addEdge(a, term);
				}
			}
		}
		return new ScribbleFsm(init, term);
	}

	/*public ScribbleFsm bstitch(ScribbleFsm f)
	{
		if (this.term == null || this.init.equals(this.term))
		{
			throw new RuntimeException("Cannot stitch onto FSM: " + this);
		}
		FsmBuilder b = new FsmBuilder(); 
		ProtocolState init = b.makeInit(this.init.getLabels());
		ProtocolState tmp = b.newState(this.term.getLabels());
		fstitch(b, new HashSet<>(), this.init, init, this.term, tmp);  // Essentially copy (could factor out as aux) -- unnecessary as PrototolStates are immutable, but would need to change FsmBuilder validation to record all newly reachable states
		bstitch(b, this.term, f);
		return b.build();
	}
	
	private static void bstitch(FsmBuilder b, ProtocolState term, ScribbleFsm f)
	{
		for (Entry<IOAction, ProtocolState> e : f.init.getEdges().entrySet())
		{
			ProtocolState tmp = b.newState(e.getValue().getLabels());
			fstitch(b, new HashSet<>(), f.init, tmp, f.term, f.term);  // Essentially copy (could factor out as aux) -- unnecessary as PrototolStates are immutable, but would need to change FsmBuilder validation to record all newly reachable states
			b.addEdge(term, e.getKey(), tmp);
		}
	}*/
	
	@Override
	public String toString()
	{
		//return this.init.toString();
		return this.init.toDot();
	}
	
	private static void findPreTerminals(Set<ProtocolState> seen, ProtocolState curr, Set<ProtocolState> preterms)
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
	}
}
