package org.scribble.model.local;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.scribble.main.ScribbleException;
import org.scribble.model.GraphBuilder;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.RecVar;

// Helper class for EndpointGraphBuilder -- can access the protected setters of EndpointState
public class LGraphBuilder extends GraphBuilder<IOAction, EndpointState, Local>
{
	/*private EndpointState root;
	
	private final Map<RecVar, Deque<EndpointState>> recvars = new HashMap<>();  // Should be a stack of EndpointState?
	//private final Map<SubprotocolSig, EndpointState> subprotos = new HashMap<>();  // Not scoped sigs
	private final Map<RecVar, Deque<IOAction>> enacting = new HashMap<>();

	private Deque<EndpointState> pred = new LinkedList<>();
	private Deque<IOAction> prev = new LinkedList<>();
	
	private EndpointState entry;
	private EndpointState exit;  // Good for merges (otherwise have to generate dummy merge nodes)*/
	
	public LGraphBuilder()
	{

	}

	public void removeEdgeFromPredecessor(EndpointState s, IOAction a) throws ScribbleException
	{
		super.removeEdgeFromPredecessor(s, a);
	}
	
	// Choice-guarded continues (can be done in one pass)
	public void addRecursionEdge(EndpointState s, IOAction a, EndpointState succ)
	{
		super.addRecursionEdge(s, a, succ);
	}
	
	// Choice-unguarded continues -- fixed in finalise pass
	public void addContinueEdge(EndpointState s, RecVar rv)
	{
		/*this.contStates.add(s);
		this.contRecVars.add(rv);*/
		EndpointState entry = getRecursionEntry(rv);
		addEdgeAux(s, new IntermediateContinueEdge(rv), entry);
	}
	
	public EndpointGraph finalise()
	{
		EndpointState res = new EndpointState(this.entry.getLabels());
		EndpointState resTerm = new EndpointState(this.exit.getLabels());
		Map<EndpointState, EndpointState> map = new HashMap<>();
		map.put(this.entry, res);
		map.put(this.exit, resTerm);
		Set<EndpointState> seen = new HashSet<>();
		fixContinueEdges(seen, map, this.entry, res);
		if (!seen.contains(this.exit))
		{
			resTerm = null;
		}
		
		/*Map<Integer, EndpointState> all = getAllStates(res);
		EndpointState dfa = determinise(all, res, resTerm);
		System.out.println("111: " + dfa.toDot());*/
		
		return new EndpointGraph(res, resTerm);
	}
	
	// FIXME: incomplete: won't fully correctly handle situations involving, e.g., transitive continue-edge fixing?
	private void fixContinueEdges(Set<EndpointState> seen, Map<EndpointState, EndpointState> map, EndpointState curr, EndpointState res)
	{
		if (seen.contains(curr))
		{
			return;
		}
		seen.add(curr);
		Iterator<IOAction> as = curr.getAllTakeable().iterator();
		Iterator<EndpointState> ss = curr.getSuccessors().iterator();
		while (as.hasNext())
		{
			IOAction a = as.next();
			EndpointState succ = ss.next();
			EndpointState next;
			next = getNext(map, succ);
			
			if (!(a instanceof IntermediateContinueEdge))
			{
				addEdgeAux(res, a, next);
			
				fixContinueEdges(seen, map, succ, next);
			}
			else
			{
				IntermediateContinueEdge ice = (IntermediateContinueEdge) a;
				//for (IOAction e : this.enactingMap.get(succ))
				RecVar rv = new RecVar(ice.mid.toString());
				for (IOAction e : this.enactingMap.get(succ).get(rv))
				{
					for (EndpointState n : succ.takeAll(e))
					{
						next = getNext(map, n);
						addEdgeAux(res, e, next);

						fixContinueEdges(seen, map, succ, next);
					}
				}
			}
		}
	}

	private EndpointState getNext(Map<EndpointState, EndpointState> map, EndpointState succ)
	{
		EndpointState next;
		if (map.containsKey(succ))
		{
			 next = map.get(succ);
		}
		else
		{
			next = new EndpointState(succ.getLabels());
			map.put(succ, next);
		}
		return next;
	}
	
	/*public void reset()
	{
		this.recvars.clear();
		this.entry = newState(Collections.emptySet());
		this.root = this.entry;
		this.exit = newState(Collections.emptySet());
	}*/
	
	public EndpointState newState(Set<RecVar> labs)
	{
		return new EndpointState(labs);
	}
	
	/*private Map<Integer, EndpointState> getAllStates(EndpointState init)
	{
		Map<Integer, EndpointState> all = new HashMap<>();
		List<EndpointState> todo = new LinkedList<>();
		todo.add(init);
		while (!todo.isEmpty())
		{
			EndpointState next = todo.remove(0);
			all.put(next.id, next);
			for (EndpointState s : next.getSuccessors())
			{
				if (!all.keySet().contains(s.id) && !todo.contains(s))
				{
					todo.add(s);
				}
			}
		}
		return all;
	}
	
	// Basic determinisation does not preserve bisimilarity, e.g. ( choice at A { 1() from A to B; 2() from A to B; } or { 1() from A to B; 2() from A to B; } ) becomes ( 1() from A to B; choice at A { 2() from A to B; } or { 2() from A to B; } )
	private EndpointState determinise(Map<Integer, EndpointState> all, EndpointState init, EndpointState term)
	{
		Map<Set<Integer>, EndpointState> done = new HashMap<>();
		Map<Set<Integer>, EndpointState> todo = new LinkedHashMap<>();
		Set<Integer> tmp0 = IntStream.of(init.id).mapToObj((i) -> i).collect(Collectors.toSet());
		EndpointState dinit = newState(tmp0.stream().map((i) -> new RecVar(Integer.toString(i))).collect(Collectors.toSet()));
		todo.put(tmp0, dinit);

		while (!todo.isEmpty())
		{
			Set<Integer> k = todo.keySet().iterator().next();
			EndpointState next = todo.remove(k);
			done.put(k, next);
			
			for (RecVar rv1 : next.getLabels())
			{
				EndpointState s1 = all.get(Integer.parseInt(rv1.toString()));
				for (IOAction a : s1.getAllTakeable())
				{
					Set<Integer> foo = new HashSet<>();
					for (RecVar rv2 : next.getLabels())
					{
						EndpointState s2 = all.get(Integer.parseInt(rv2.toString()));
						if (s2.isTakeable(a))
						{
							foo.addAll(s2.takeAll(a).stream().map((x) -> x.id).collect(Collectors.toSet()));
						}
					}
					EndpointState succ = null;
					if (done.containsKey(foo))
					{
						succ = done.get(foo);
					}
					else if (todo.containsKey(foo))
					{
						succ = todo.get(foo);
					}
					else
					{
						succ = newState(foo.stream().map((i) -> new RecVar(Integer.toString(i))).collect(Collectors.toSet()));
						todo.put(foo, succ);
					}
					addEdge(next, a, succ);
				}
			}
		}
		return dinit;
	}*/

	// OK for model checking to minimalise first before checking? determinise first -- no: need nfa minimisation up to bimisilarity (bisimilar quotients)
	// Individual duplicate edges already 
	// Post: initial state is the same instance, terminal state if any is the same instance (equality is instance id anyway)
	/*private static void minimalise(EndpointState init)
	{
		Map<EndpointState, Map<EndpointState, Map<GIOAction, EndpointState[]>>> candidates = getInitialCandidates(init);
	}
	
	private static Map<EndpointState, Map<EndpointState, Map<GIOAction, EndpointState[]>>> getInitialCandidates(EndpointState init)
	{
		Map<EndpointState, Map<EndpointState, Map<GIOAction, EndpointState[]>>> candidates = new HashMap<>();
		List<EndpointState> todo = new LinkedList<>();
		todo.add(init);
		while (!todo.isEmpty())
		{
			EndpointState curr = todo.remove(0);
			Map<EndpointState, Map<GIOAction, EndpointState[]>> tmp = new HashMap<>();
			for (EndpointState s : candidates.keySet())
			{
				tmp.put(s, new HashMap<>());
			}
			candidates.put(curr, tmp);
		}
		for (EndpointState s1 : candidates.keySet())
		{
			Map<EndpointState, Map<GIOAction, EndpointState[]>> tmp = candidates.get(s1);
			for (EndpointState s2 : tmp.keySet())
			{
				if (!s1.get...)
					...
			}
		}
		return candidates;
	}*/
	
	/*public void addEntryLabel(RecVar lab)
	{
		//this.entry.addLabel(lab);
		throw new RuntimeException("Deprecated: " + this);
	}

	// Records 's' as predecessor state, and 'a' as previous action and the "enacting action" for "fresh" recursion scopes
	public void addEdge(EndpointState s, IOAction a, EndpointState succ)
	{
		/*s.addEdge(a, succ);
		if (!this.pred.isEmpty())
		{
			this.pred.pop();
			this.prev.pop();
		}
		this.pred.push(s);
		this.prev.push(a);
		
		for (Deque<IOAction> ens : this.enacting.values())
		{
			if (!ens.isEmpty())
			{
				if (ens.peek() == null)
				{
					ens.pop();
					ens.push(a);
				}
			}
		}* /
		throw new RuntimeException("Deprecated: " + this);
	}
	
	public void pushChoiceBlock()
	{
		this.pred.push(null);  // Signifies following statement is "unguarded" in this choice block
		this.prev.push(null);
	}

	public void popChoiceBlock()
	{
		this.pred.pop();
		this.prev.pop();
	}
	
	public boolean isUnguardedInChoice()
	//public boolean isUnguardedInChoice(RecVar rv)
	{
		return
				!this.entry.equals(this.root) && // Hacky? for protocols that start with unguarded choice-rec, e.g. choice at A { rec X { ... at root
				!this.pred.isEmpty() && this.pred.peek() == null;
	}
	
	public void pushRecursionEntry(RecVar recvar, EndpointState entry)
	{
		/*if (!isUnguardedInChoice())  // Don't record rec entry if it is an unguarded choice-rec
		{
			this.entry.addLabel(recvar);
		}* /
		//this.recvars.put(recvar, this.entry);
		Deque<EndpointState> tmp = this.recvars.get(recvar);
		if (tmp == null)
		{
			tmp = new LinkedList<>();
			this.recvars.put(recvar, tmp);
		}
		/*if (isUnguardedInChoice())
		{
			tmp.push(tmp.peek());  // Works because unguarded recs unfolded (including nested recvar shadowing -- if unguarded choice-rec, it will be unfolded and rec entry recorded for guarded unfolding)
		}
		else
		{
			tmp.push(this.entry);
		}* /
		tmp.push(entry);
		
		Deque<IOAction> tmp2 = this.enacting.get(recvar);
		if (tmp2 == null)
		{
			tmp2 = new LinkedList<>();
			this.enacting.put(recvar, tmp2);
		}
		tmp2.push(null);
	}	

	public void popRecursionEntry(RecVar recvar)
	{
		this.recvars.get(recvar).pop();
		this.enacting.get(recvar).pop();
	}	
	
	public EndpointState getPredecessor()
	{
		return this.pred.peek();
	}
	
	public IOAction getPreviousAction()
	{
		return this.prev.peek();
	}

	public EndpointState getRecursionEntry(RecVar recvar)
	{
		return this.recvars.get(recvar).peek();
	}	
	
	public IOAction getEnacting(RecVar rv)
	{
		return this.enacting.get(rv).peek();
	}
	
	/*public EndpointState getSubprotocolEntry(SubprotocolSig subsig)
	{
		return this.subprotos.get(subsig);
	}

	public void setSubprotocolEntry(SubprotocolSig subsig)
	{
		this.subprotos.put(subsig, this.entry);
	}	

	public void removeSubprotocolEntry(SubprotocolSig subsig)
	{
		this.subprotos.remove(subsig);
	}* /
	
	public EndpointState getEntry()
	{
		return this.entry;
	}

	public void setEntry(EndpointState entry)
	{
		this.entry = entry;
	}

	public EndpointState getExit()
	{
		return this.exit;
	}

	public void setExit(EndpointState exit)
	{
		this.exit = exit;
	}*/
}
