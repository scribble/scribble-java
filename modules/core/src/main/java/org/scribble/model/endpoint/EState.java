package org.scribble.model.endpoint;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.scribble.main.ScribbleException;
import org.scribble.model.MState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.RecVar;

public class EState extends MState<EAction, EState, Local>
{
	public static enum Kind
	{
		OUTPUT,      // SEND, CONNECT and WRAP_CLIENT
		UNARY_INPUT,
		POLY_INPUT,
		TERMINAL,
		ACCEPT,      // Unary/multi accept?
		WRAP_SERVER,
	}
	
	/*private static int count = 0;
	
	public final int id;

	private final Set<RecVar> labs;  // Was RecVar and SubprotocolSigs, now using inlined protocol for FSM building so just RecVar
	private final LinkedHashMap<IOAction, EndpointState> edges;  // Want predictable ordering of entries for e.g. API generation (state enumeration)*/
	
	protected EState(Set<RecVar> labs)
	{
		/*this.id = EndpointState.count++;
		this.labs = new HashSet<>(labs);
		this.edges = new LinkedHashMap<>();*/
		super(labs);
	}

	public EGraph toGraph()
	{
		return new EGraph(this, getTerminal(this));  // Throws exception if >1 terminal; null if no terminal
	}

	/*.. move back to endpointstate
	.. use getallreachable to get subgraph, make a graph clone method
	.. for each poly output, clone a (non-det) edge to clone of the reachable subgraph with the clone of the current node pruned to this single choice
	..     be careful of original non-det edges, need to do each separately
	.. do recursively on the subgraphs, will end up with a normal form with subgraphs without output choices
	.. is it equiv to requiring all roles to see every choice path?  except initial accepting roles -- yes
	.. easier to implement as a direct check on the standard global model, rather than model hacking -- i.e. liveness is not just about terminal sets, but about "branching condition", c.f. julien?
	.. the issue is connect/accept -- makes direct check a bit more complicated, maybe value in doing it by model hacking to rely on standard liveness checking?
	..     should be fine, check set of roles on each path is equal, except for accept-guarded initial roles*/
	public EState unfairTransform()
	{
		EState init = this.clone();
		
		EState term = MState.getTerminal(init);
		Set<EState> seen = new HashSet<>();
		Set<EState> todo = new LinkedHashSet<>();
		todo.add(init);
		while (!todo.isEmpty())
		{
			Iterator<EState> i = todo.iterator();
			EState curr = i.next();
			i.remove();

			if (seen.contains(curr))
			{
				continue;
			}
			seen.add(curr);
			
			if (curr.getStateKind() == Kind.OUTPUT && curr.getAllActions().size() > 1)  // >1 is what makes this algorithm terminating
			{
				//if (curr.getAllTakeable().size() > 1)
				{
					Iterator<EAction> as = curr.getAllActions().iterator();
					Iterator<EState> ss = curr.getAllSuccessors().iterator();
					//Map<IOAction, EndpointState> clones = new HashMap<>();
					List<EAction> cloneas = new LinkedList<>();
					List<EState> cloness = new LinkedList<>();
					//LinkedHashMap<EndpointState, EndpointState> cloness = new LinkedHashMap<>();  // clone -> original
					Map<EState, List<EAction>> toRemove = new HashMap<>();  // List needed for multiple edges to remove to the same state: e.g. mu X . (A->B:1 + A->B:2).X
					while (as.hasNext())
					{
						EAction a = as.next();
						EState s = ss.next();
						if (!s.canReach(curr))
						{
							todo.add(s);
						}
						else
						{
							EState clone = curr.unfairClone(term, a, s);  // s is a succ of curr
							//try { s.removeEdge(a, tmps); } catch (ScribbleException e) { throw new RuntimeException(e); }
							//clones.put(a, clone);
							cloneas.add(a);
							cloness.add(clone);
							//cloness.put(clone, s);
							
							//toRemove.put(s, a);
							List<EAction> tmp = toRemove.get(s);
							if (tmp == null)
							{
								tmp = new LinkedList<>();
								toRemove.put(s, tmp);
							}
							tmp.add(a);
						}
					}
					//if (!clones.isEmpty())  // Redundant, but more clear
					if (!cloneas.isEmpty())  // Redundant, but more clear
					{
						/*as = new LinkedList<>(curr.getAllTakeable()).iterator();
						//Iterator<EndpointState>
						ss = new LinkedList<>(curr.getSuccessors()).iterator();
						while (as.hasNext())
						{
							IOAction a = as.next();
							EndpointState s = ss.next();
							//if (clones.containsKey(a))  // Still OK for non-det edges?
							//if (cloneas.contains(a))  // Still OK for non-det edges? -- no: removing *all* non-det a's for this a, so non-recursive cases are lost
							if (cloneas.contains(a) && ...succ == orig...)
							{
								try { curr.removeEdge(a, s); } catch (ScribbleException e) { throw new RuntimeException(e); }
							}
						}*/
						for (EState s : toRemove.keySet())
						{
							try
							{
								//curr.removeEdge(toRemove.get(s), s);
								for (EAction tmp : toRemove.get(s))
								{
									curr.removeEdge(tmp, s);
								}
							}
							catch (ScribbleException e) { throw new RuntimeException(e); }
						}
						//for (Entry<IOAction, EndpointState> e : clones.entrySet())
						Iterator<EAction> icloneas = cloneas.iterator();
						Iterator<EState> icloness = cloness.iterator();
						//Iterator<EndpointState> icloness = cloness.keySet().iterator();
						while (icloneas.hasNext())
						{
							EAction a = icloneas.next();
							EState s = icloness.next();
							/*curr.addEdge(e.getKey(), e.getValue());
							todo.add(e.getValue());
							seen.add(e.getValue());*/
							curr.addEdge(a, s);
							todo.add(s);  // Doesn't work if non-det preserved by unfairClone aux (recursively edges>1)
							/*seen.add(s);  // Idea is to bypass succ clone (for non-det, edges>1) but in general this will be cloned again before returning to it, so bypass doesn't work -- to solve this more generally probably need to keep a record of all clones to bypass future clones
							todo.addAll(s.getSuccessors());*/
						}
						//continue;
					}
				}
			}
			else
			{
				todo.addAll(curr.getAllSuccessors());
			}
		}

		return init;
	}
	
	// Pre: succ is the root of the subgraph, and succ is a successor of "this" (which is inside the subgraph)
	// i.e., this -a-> succ (maybe non-det)
	// Returns the clone of the subgraph rooted at succ, with all non- "this-a->succ" actions pruned from the clone of "this" state
	// i.e., we took "a" from "this" to get to succ (the subgraph root); if we enter "this" again (inside the subgraph), then always take "a" again
	protected EState unfairClone(EState term, EAction a, EState succ) // Need succ param for non-det
	{
		//EndpointState succ = take(a);
		Set<EState> all = new HashSet<>();
		all.add(succ);
		all.addAll(MState.getAllReachable(succ));
		Map<Integer, EState> map = new HashMap<>();  // original s.id -> clones
		for (EState s : all)
		{
			if (term != null && s.id == term.id)
			{
				map.put(term.id, term);
			}
			else
			{
				//map.put(s.id, newState(s.labs));
				map.put(s.id, newState(Collections.emptySet()));
			}
		}
		for (EState s : all)
		{
			Iterator<EAction> as = s.getAllActions().iterator();
			Iterator<EState> ss = s.getAllSuccessors().iterator();
			EState clone = map.get(s.id);
			while (as.hasNext())
			{
				EAction tmpa = as.next();
				EState tmps = ss.next();
				if (s.id != this.id
						|| (tmpa.equals(a) && tmps.equals(succ)))  // Non-det also pruned from clone of this -- but OK? non-det still preserved on original state, so any safety violations due to non-det will still come out?
					                                             // ^ Currently, this is like non-fairness is extended to even defeat non-determinism
				{
					clone.addEdge(tmpa, map.get(tmps.id));
				}
			}
		}
		return map.get(succ.id);
	}
	
	@Override
	protected EState newState(Set<RecVar> labs)
	{
		return new EState(labs);
	}
	
	// FIXME: refactor as "isSyncOnly" -- and make an isSync in IOAction
	public boolean isConnectOrWrapClientOnly()
	{
		return getStateKind() == Kind.OUTPUT && getAllActions().stream().allMatch((a) -> a.isConnect() || a.isWrapClient());
	}
	
	public Kind getStateKind()
	{
		List<EAction> as = this.getAllActions();
		if (as.size() == 0)
		{
			return Kind.TERMINAL;
		}
		else
		{
			EAction a = as.iterator().next();
			return (a.isSend() || a.isConnect() || a.isDisconnect() || a.isWrapClient() ) ? Kind.OUTPUT
						//: (a.isConnect() || a.isAccept()) ? Kind.CONNECTION  // FIXME: states can have mixed connects and sends
						//: (a.isConnect()) ? Kind.CONNECT
						: (a.isAccept()) ? Kind.ACCEPT  // Accept is always unary, guaranteed by treating as a unit message id (wrt. branching)  // No: not any more
						: (a.isWrapServer()) ? Kind.WRAP_SERVER   // WrapServer is always unary, guaranteed by treating as a unit message id (wrt. branching)
						: (as.size() > 1) ? Kind.POLY_INPUT : Kind.UNARY_INPUT;
		}
	}
}
