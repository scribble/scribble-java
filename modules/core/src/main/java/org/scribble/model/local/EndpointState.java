package org.scribble.model.local;

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
import org.scribble.model.ModelState;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.RecVar;

// http://sandbox.kidstrythisathome.com/erdos/
public class EndpointState extends ModelState<IOAction, EndpointState, Local>
{
	public static enum Kind { OUTPUT, UNARY_INPUT, POLY_INPUT, TERMINAL, ACCEPT, WRAP_SERVER, //CONNECT
		}  // CONNECTION should just be sync?
			// FIXME: distinguish connection and message transfer
	
	/*private static int count = 0;
	
	public final int id;

	private final Set<RecVar> labs;  // Was RecVar and SubprotocolSigs, now using inlined protocol for FSM building so just RecVar
	private final LinkedHashMap<IOAction, EndpointState> edges;  // Want predictable ordering of entries for e.g. API generation (state enumeration)*/
	
	protected EndpointState(Set<RecVar> labs)
	{
		/*this.id = EndpointState.count++;
		this.labs = new HashSet<>(labs);
		this.edges = new LinkedHashMap<>();*/
		super(labs);
	}

	public EndpointGraph toGraph()
	{
		return new EndpointGraph(this, getTerminal(this));  // Throws exception if >1 terminal; null if no terminal
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
	public EndpointState unfairTransform()
	{
		EndpointState init = this.clone();
		
		EndpointState term = ModelState.getTerminal(init);
		Set<EndpointState> seen = new HashSet<>();
		Set<EndpointState> todo = new LinkedHashSet<>();
		todo.add(init);
		while (!todo.isEmpty())
		{
			Iterator<EndpointState> i = todo.iterator();
			EndpointState curr = i.next();
			i.remove();

			if (seen.contains(curr))
			{
				continue;
			}
			seen.add(curr);
			
			if (curr.getStateKind() == Kind.OUTPUT && curr.getAllTakeable().size() > 1)
			{
				//if (curr.getAllTakeable().size() > 1)
				{
					Iterator<IOAction> as = curr.getAllTakeable().iterator();
					Iterator<EndpointState> ss = curr.getSuccessors().iterator();
					//Map<IOAction, EndpointState> clones = new HashMap<>();
					List<IOAction> cloneas = new LinkedList<>();
					List<EndpointState> cloness = new LinkedList<>();
					while (as.hasNext())
					{
						IOAction a = as.next();
						EndpointState s = ss.next();
						if (!s.canReach(curr))
						{
							todo.add(s);
						}
						else
						{
							EndpointState clone = curr.unfairClone(term, a, s);
							//try { s.removeEdge(a, tmps); } catch (ScribbleException e) { throw new RuntimeException(e); }
							//clones.put(a, clone);
							cloneas.add(a);
							cloness.add(clone);
						}
					}
					//if (!clones.isEmpty())  // Redundant, but more clear
					if (!cloneas.isEmpty())  // Redundant, but more clear
					{
						as = new LinkedList<>(curr.getAllTakeable()).iterator();
						//Iterator<EndpointState>
						ss = new LinkedList<>(curr.getSuccessors()).iterator();
						while (as.hasNext())
						{
							IOAction a = as.next();
							EndpointState s = ss.next();
							//if (clones.containsKey(a))  // Still OK for non-det edges?
							if (cloneas.contains(a))  // Still OK for non-det edges?
							{
								try { curr.removeEdge(a, s); } catch (ScribbleException e) { throw new RuntimeException(e); }
							}
						}
						//for (Entry<IOAction, EndpointState> e : clones.entrySet())
						Iterator<IOAction> icloneas = cloneas.iterator();
						Iterator<EndpointState> icloness = cloness.iterator();
						while (icloneas.hasNext())
						{
							IOAction a = icloneas.next();
							EndpointState s = icloness.next();
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
				todo.addAll(curr.getSuccessors());
			}
		}

		return init;
	}
	
	// Returns the clone of the subgraph rooted at succ, with all non-a pruned from the clone of this
	// Pre: this -a-> succ (maybe non-det)
	protected EndpointState unfairClone(EndpointState term, IOAction a, EndpointState succ) // Need succ param for non-det
	{
		//EndpointState succ = take(a);
		Set<EndpointState> all = new HashSet<>();
		all.add(succ);
		all.addAll(ModelState.getAllReachable(succ));
		Map<Integer, EndpointState> map = new HashMap<>();  // original s.id -> clones
		for (EndpointState s : all)
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
		for (EndpointState s : all)
		{
			Iterator<IOAction> as = s.getAllTakeable().iterator();
			Iterator<EndpointState> ss = s.getSuccessors().iterator();
			EndpointState clone = map.get(s.id);
			while (as.hasNext())
			{
				IOAction tmpa = as.next();
				EndpointState tmps = ss.next();
				if (s.id != this.id
						|| (tmpa.equals(a) && tmps.equals(succ)))  // Non-det also pruned from clone of this -- but OK? non-det still preserved on original state, so any safety violations due to non-det will still come out?
					                                             // ^ Currently this like non-fairness is extended to even defeat non-determinism
				{
					clone.addEdge(tmpa, map.get(tmps.id));
				}
			}
		}
		return map.get(succ.id);
	}
	
	@Override
	protected EndpointState newState(Set<RecVar> labs)
	{
		return new EndpointState(labs);
	}
	
	// FIXME: refactor as "isSyncOnly" -- and make an isSync in IOAction
	public boolean isConnectOrWrapClientOnly()
	{
		return getStateKind() == Kind.OUTPUT && getAllTakeable().stream().allMatch((a) -> a.isConnect() || a.isWrapClient());
	}
	
	public Kind getStateKind()
	{
		List<IOAction> as = this.getAllTakeable();
		if (as.size() == 0)
		{
			return Kind.TERMINAL;
		}
		else
		{
			IOAction a = as.iterator().next();
			return (a.isSend() || a.isConnect() || a.isDisconnect() || a.isWrapClient() ) ? Kind.OUTPUT
						//: (a.isConnect() || a.isAccept()) ? Kind.CONNECTION  // FIXME: states can have mixed connects and sends
						//: (a.isConnect()) ? Kind.CONNECT
						: (a.isAccept()) ? Kind.ACCEPT  // Accept is always unary, guaranteed by treating as a unit message id (wrt. branching)  // No: not any more
						: (a.isWrapServer()) ? Kind.WRAP_SERVER   // WrapServer is always unary, guaranteed by treating as a unit message id (wrt. branching)
						: (as.size() > 1) ? Kind.POLY_INPUT : Kind.UNARY_INPUT;
		}
	}
}
