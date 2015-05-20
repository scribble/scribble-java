package org.scribble2.fsm;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScribbleFsm
{
	public final ProtocolState init;
	public final ProtocolState term;
	
	public ScribbleFsm(ProtocolState init, ProtocolState term)
	{
		this.init = init;
		this.term = term;
	}
	
	public ScribbleFsm stitch(ScribbleFsm f)
	{
		if (this.term == null)
		{
			throw new RuntimeException("Cannot stitch onto FSM: " + this);
		}
		if (this.init.equals(this.term))
		{
			// Return f but updated with labels from this
			throw new RuntimeException("TODO: ");
		}
		else
		{
			FsmBuilder b = new FsmBuilder(); 
			ProtocolState init = b.makeInit(this.init.getLabels());
			ProtocolState swap = b.newState(f.init.getLabels());
			stitch(b, new HashSet<>(), this.init, init, this.term, swap);
			ProtocolState term = b.newState(f.term.getLabels());
			stitch(b, new HashSet<>(), f.init, swap, f.term, term);  // Essentially copy (could factor out as aux) -- unnecessary as PrototolStates are immutable, but would need to change FsmBuilder validation to record all newly reachable states
			return b.build();
		}
	}
	
	private static void stitch(FsmBuilder b, Set<ProtocolState> seen, ProtocolState curr, ProtocolState cswap, ProtocolState term, ProtocolState tswap)
	{
		if (seen.contains(curr))
		{
			return;
		}
		seen.add(curr);
		for (IOAction a : curr.getAcceptable())
		{
			ProtocolState next = curr.accept(a);
			if (next.equals(term))
			{
				b.addEdge(cswap, a, tswap);
			}
			else
			{
				ProtocolState tmp = b.newState(next.getLabels());
				b.addEdge(cswap, a, tmp);
				stitch(b, seen, next, tmp, term, tswap);
			}
		}
	}
	
	//public ScribbleFsm embed(ProtocolState init, ProtocolState term, ScribbleFsm... fs)
	public static ScribbleFsm merge(List<ScribbleFsm> fs)
	{
		FsmBuilder b = new FsmBuilder();
		//b.setInit(init);
		//b.addState(term);
		ProtocolState init = b.makeInit(Collections.emptySet());
		ProtocolState term = b.newState(Collections.emptySet());
		for (ScribbleFsm f : fs)
		{
			if (f.term == null)
			{
				throw new RuntimeException("Cannot embed FSM: " + f);
			}
			else if (!f.init.getLabels().isEmpty())  // FIXME: recursive subprotocols won't have a label
			{
				throw new RuntimeException("TODO: ");  // For potential recursion, a kind of unfolding could work?
			}
			for (IOAction a : f.init.getAcceptable())
			{
				ProtocolState succ = f.init.accept(a);
				if (succ.isTerminal())
				{
					b.addEdge(init, a, term);
				}
				else
				{
					ProtocolState tmp = b.newState(succ.getLabels());
					b.addEdge(init, a, tmp);
					stitch(b, new HashSet<>(), succ, tmp, f.term, term);
				}
			}
		}
		return b.build();
		/*for (Entry<IOAction, ProtocolState> e : f.init.getEdges().entrySet())
		{
			//ProtocolState tmp = b.importState(e.getValue());
			ProtocolState succ = e.getValue();
			ProtocolState tmp = b.newState(succ.getLabels());
			b.addEdge(init, e.getKey(), tmp);
			fstitch(b, new HashSet<>(), succ, tmp, f.term, term);
		}
		return b.build();
		/*for (Entry<IOAction, ProtocolState> e : f.init.getEdges().entrySet())
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
		return new ScribbleFsm(init, term);*/
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
