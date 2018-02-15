/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
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
import java.util.stream.Collectors;

import org.scribble.main.ScribbleException;
import org.scribble.model.MPrettyState;
import org.scribble.model.MState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.type.kind.Local;
import org.scribble.type.name.RecVar;
import org.scribble.type.name.Role;

// Label types used to be both RecVar and SubprotocolSigs; now using inlined protocol for FSM building so just RecVar
public class EState extends MPrettyState<RecVar, EAction, EState, Local>
{
	protected EState(Set<RecVar> labs)
	{
		super(labs);
	}
	
	public String toPml(Role r)
	{
		Map<Integer, String> seen = new HashMap<>();
		return 
				  "active proctype " + r + "() {\n"
				+ toPml(seen, r) + "\n"
				+ "}\n";
	}
	
	protected String toPml(Map<Integer, String> seen, Role r)
	{
		if (seen.containsKey(this.id))
		{
			//return "goto " + getLabel(seen, this.id, r) + "\n";
			return "";
		}

		String lab = getLabel(seen, this, r);
		Map<Integer, String> tmp = new HashMap<>(seen);
		tmp.put(this.id, lab);

		String res = lab + ":\n";
		EStateKind kind = getStateKind();
		List<EAction> as = getActions();
		if (kind == EStateKind.OUTPUT)
		{
			if (as.stream().anyMatch(a -> !a.isSend()))
			{
				throw new RuntimeException("TODO: " + as);
			}
			
			res += //"lab" + this.id + ":\n"
					  "if\n"
					+ as.stream().map(a ->
							  "::\n"
							+ "skip ->\n"
							+ "s_" + r + "_" + a.peer + "!" + a.mid + ";\n"
							+ "goto " + getLabel(seen, getSuccessor(a), r) + "\n"
						)
						.collect(Collectors.joining(""))
					+ "fi\n";
		}
		else if (kind == EStateKind.UNARY_INPUT || kind == EStateKind.POLY_INPUT)
		{
			res +=
					  "if\n"
					+ as.stream().map(a ->
							  "::\n"
							+ "r_" + a.peer + "_" + r + "?[" + a.mid + "] ->\n"
							+ "r_" + a.peer + "_" + r + "?" + a.mid + ";\n"
							+ "goto " + getLabel(seen, getSuccessor(a), r) + "\n"
						)
						.collect(Collectors.joining("")) 
					+ "fi\n";
		}
		else if (kind == EStateKind.TERMINAL)
		{
			res += "skip\n";
		}
		else
		{
			throw new RuntimeException("TODO: " + kind);
		}

		res += as.stream().map(a -> "\n" + getSuccessor(a).toPml(tmp, r)).collect(Collectors.joining(""));

		return res;
	}
	
	private static String getLabel(Map<Integer, String> seen, EState s, Role r)
	{
		if (seen.containsKey(s.id))
		{
			return seen.get(s.id);
		}
		String lab = (s.isTerminal() ? "end" : "label") + r + s.id;
		seen.put(s.id, lab);
		return lab;
	}
	
	// To be overridden by subclasses, to obtain the subclass nodes
  // FIXME: remove labs arg, and modify the underlying Set if needed?
	protected EState cloneNode(EModelFactory ef, Set<RecVar> labs)
	{
		//return ef.newEState(this.labs);
		return ef.newEState(labs);
	}
	
	// Helper factory method for deriving an EGraph from an arbitary EState (but not the primary way to construct EGraphs; cf., EGraphBuilderUtil)
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
	public EState unfairTransform(EModelFactory ef)
	{
		EState init = clone(ef);
		
		EState term = MPrettyState.getTerminal(init);
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
			
			if (curr.getStateKind() == EStateKind.OUTPUT && curr.getAllActions().size() > 1)  // >1 is what makes this algorithm terminating
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
							EState clone = curr.unfairClone(ef, term, a, s);  // s is a succ of curr
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

	// Fully clones the reachable graph (i.e. the "general" graph -- cf., EGraph, the specific Scribble concept of an endpoint protocol graph)
	protected EState clone(EModelFactory ef)
	{
		Set<EState> all = new HashSet<>();
		all.add(this);
		all.addAll(MPrettyState.getReachableStates(this));
		Map<Integer, EState> map = new HashMap<>();  // original s.id -> clones
		for (EState s : all)
		{
			map.put(s.id, s.cloneNode(ef, s.labs));
		}
		for (EState s : all)
		{
			Iterator<EAction> as = s.getAllActions().iterator();
			Iterator<EState> ss = s.getAllSuccessors().iterator();
			EState clone = map.get(s.id);
			while (as.hasNext())
			{
				EAction a = as.next();
				EState succ = ss.next();
				clone.addEdge(a, map.get(succ.id));
			}
		}
		return map.get(this.id);
	}
	
	// Pre: succ is the root of the subgraph, and succ is a successor of "this" (which is inside the subgraph)
	// i.e., this -a-> succ (maybe non-det)
	// Returns the clone of the subgraph rooted at succ, with all non- "this-a->succ" actions pruned from the clone of "this" state
	// i.e., we took "a" from "this" to get to succ (the subgraph root); if we enter "this" again (inside the subgraph), then always take "a" again
	protected EState unfairClone(EModelFactory ef, EState term, EAction a, EState succ) // Need succ param for non-det
	{
		//EndpointState succ = take(a);
		Set<EState> all = new HashSet<>();
		all.add(succ);
		all.addAll(MPrettyState.getReachableStates(succ));
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
				map.put(s.id, s.cloneNode(ef, Collections.emptySet()));  // FIXME: remove labs arg from cloneNode and just clear the lab set here?
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
	
	// FIXME: refactor as "isSyncOnly" -- and make an isSync in IOAction
	public boolean isConnectOrWrapClientOnly()
	{
		return getStateKind() == EStateKind.OUTPUT && getAllActions().stream().allMatch((a) -> a.isRequest() || a.isWrapClient());
	}
	
	public EStateKind getStateKind()
	{
		List<EAction> as = this.getAllActions();
		if (as.size() == 0)
		{
			return EStateKind.TERMINAL;
		}
		else
		{
			/*EAction a = as.iterator().next();
			return (a.isSend() || a.isConnect() || a.isDisconnect() || a.isWrapClient() ) ? EStateKind.OUTPUT
						//: (a.isConnect() || a.isAccept()) ? Kind.CONNECTION  // FIXME: states can have mixed connects and sends
						//: (a.isConnect()) ? Kind.CONNECT
						: (a.isAccept()) ? EStateKind.ACCEPT  // Accept is always unary, guaranteed by treating as a unit message id (wrt. branching)  // No: not any more, connect-with-message
						: (a.isWrapServer()) ? EStateKind.WRAP_SERVER   // WrapServer is always unary, guaranteed by treating as a unit message id (wrt. branching)
						: (as.size() > 1) ? EStateKind.POLY_INPUT : EStateKind.UNARY_INPUT;*/
			if (as.stream().allMatch(a -> a.isSend() || a.isRequest() || a.isWrapClient()))  // wrapClient should be unary?
			{
				return EStateKind.OUTPUT;
			}
			else if (as.stream().allMatch(EAction::isReceive))
			{
				return (as.size() == 1) ? EStateKind.UNARY_INPUT : EStateKind.POLY_INPUT;
			}
			else if (as.stream().allMatch(EAction::isAccept))
			{
				return EStateKind.ACCEPT;  // Distinguish unary for API gen?  cf. receive
			}
			else if (as.size() == 1 && as.get(0).isDisconnect())
			{
				return EStateKind.OUTPUT;
			}
			else if (as.size() == 1 && as.get(0).isWrapServer())
			{
				return EStateKind.WRAP_SERVER;
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + as);
			}
		}
	}

	@Override
	public int hashCode()
	{
		int hash = 83;
		hash = 31 * hash + super.hashCode();  // N.B. uses state ID only
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof EState))
		{
			return false;
		}
		return super.equals(o);  // Checks canEquals
	}

	@Override
	protected boolean canEquals(MState<?, ?, ?, ?> s)
	{
		return s instanceof EState;
	}
}
