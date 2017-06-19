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
package org.scribble.model.global;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.model.MPrettyPrint;
import org.scribble.model.endpoint.EFSM;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EStateKind;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.model.global.actions.SAction;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;

public class SGraph implements MPrettyPrint
{
	public final GProtocolName proto;
	//private final Map<Role, EGraph> efsms;
	//private final boolean fair;
	
	public final SState init;
	public Map<Integer, SState> states; // State ID -> GMState

	private Map<Integer, Set<Integer>> reach; // State ID -> reachable states (not reflexive)
	private Set<Set<Integer>> termSets;

	protected SGraph(GProtocolName proto, Map<Integer, SState> states, SState init)
	{
		this.proto = proto;
		this.init = init;
		this.states = Collections.unmodifiableMap(states);
		this.reach = getReachabilityMap();
	}
	
	public SModel toModel()
	{
		return new SModel(this);
	}

	public Set<Set<Integer>> getTerminalSets()
	{
		if (this.termSets != null)
		{
			return this.termSets;
		}

		Set<Set<Integer>> termSets = new HashSet<>();
		Set<Set<Integer>> checked = new HashSet<>();
		for (Integer i : reach.keySet())
		{
			SState s = this.states.get(i);
			Set<Integer> rs = this.reach.get(s.id);
			if (!checked.contains(rs) && rs.contains(s.id))
			{
				checked.add(rs);
				if (isTerminalSetMember(s))
				{
					termSets.add(rs);
				}
			}
		}
		this.termSets = Collections.unmodifiableSet(termSets);
		return this.termSets;
	}

	private boolean isTerminalSetMember(SState s)
	{
		Set<Integer> rs = this.reach.get(s.id);
		Set<Integer> tmp = new HashSet<>(rs);
		tmp.remove(s.id);
		for (Integer r : tmp)
		{
			if (!this.reach.containsKey(r) || !this.reach.get(r).equals(rs))
			{
				return false;
			}
		}
		return true;
	}

	// Pre: reach.get(start).contains(end) // FIXME: will return null if initial
	// state is error
	public List<SAction> getTrace(SState start, SState end)
	{
		SortedMap<Integer, Set<Integer>> candidates = new TreeMap<>();
		Set<Integer> dis0 = new HashSet<Integer>();
		dis0.add(start.id);
		candidates.put(0, dis0);

		Set<Integer> seen = new HashSet<>();
		seen.add(start.id);

		return getTraceAux(new LinkedList<>(), seen, candidates, end);
	}

	// Djikstra's
	private List<SAction> getTraceAux(List<SAction> trace, Set<Integer> seen,
			SortedMap<Integer, Set<Integer>> candidates, SState end)
	{
		Integer dis = candidates.keySet().iterator().next();
		Set<Integer> cs = candidates.get(dis);
		Iterator<Integer> it = cs.iterator();
		Integer currid = it.next();
		it.remove();
		if (cs.isEmpty())
		{
			candidates.remove(dis);
		}

		SState curr = this.states.get(currid);
		Iterator<SAction> as = curr.getAllActions().iterator();
		Iterator<SState> ss = curr.getAllSuccessors().iterator();
		while (as.hasNext())
		{
			SAction a = as.next();
			SState s = ss.next();
			if (s.id == end.id)
			{
				trace.add(a);
				return trace;
			}

			if (!seen.contains(s.id) && this.reach.containsKey(s.id)
					&& this.reach.get(s.id).contains(end.id))
			{
				seen.add(s.id);
				Set<Integer> tmp1 = candidates.get(dis + 1);
				if (tmp1 == null)
				{
					tmp1 = new HashSet<>();
					candidates.put(dis + 1, tmp1);
				}
				tmp1.add(s.id);
				List<SAction> tmp2 = new LinkedList<>(trace);
				tmp2.add(a);
				List<SAction> res = getTraceAux(tmp2, seen, candidates, end);
				if (res != null)
				{
					return res;
				}
			}
		}
		return null;
	}

	// Not reflexive
	public Map<Integer, Set<Integer>> getReachabilityMap()
	{
		if (this.reach != null)
		{
			return this.reach;
		}

		Map<Integer, Integer> idToIndex = new HashMap<>(); // state ID -> array
																												// index
		Map<Integer, Integer> indexToId = new HashMap<>(); // array index -> state
																												// ID
		int i = 0;
		for (SState s : this.states.values())
		{
			idToIndex.put(s.id, i);
			indexToId.put(i, s.id);
			i++;
		}
		this.reach = getReachabilityAux(idToIndex, indexToId);

		return this.reach;
	}

	private Map<Integer, Set<Integer>> getReachabilityAux(
			Map<Integer, Integer> idToIndex, Map<Integer, Integer> indexToId)
	{
		int size = idToIndex.keySet().size();
		boolean[][] reach = new boolean[size][size];

		for (Integer s1id : idToIndex.keySet())
		{
			for (SState s2 : this.states.get(s1id).getAllSuccessors())
			{
				reach[idToIndex.get(s1id)][idToIndex.get(s2.id)] = true;
			}
		}

		for (boolean again = true; again;)
		{
			again = false;
			for (int i = 0; i < size; i++)
			{
				for (int j = 0; j < size; j++)
				{
					if (reach[i][j])
					{
						for (int k = 0; k < size; k++)
						{
							if (reach[j][k] && !reach[i][k])
							{
								reach[i][k] = true;
								again = true;
							}
						}
					}
				}
			}
		}

		Map<Integer, Set<Integer>> res = new HashMap<>();
		for (int i = 0; i < size; i++)
		{
			Set<Integer> tmp = res.get(indexToId.get(i));
			for (int j = 0; j < size; j++)
			{
				if (reach[i][j])
				{
					if (tmp == null)
					{
						tmp = new HashSet<>();
						res.put(indexToId.get(i), tmp);
					}
					tmp.add(indexToId.get(j));
				}
			}
		}

		return Collections.unmodifiableMap(res);
	}

	@Override
	public String toDot()
	{
		return this.init.toDot();
	}

	@Override
	public String toAut()
	{
		return this.init.toAut();
	}

	@Override
	public String toString()
	{
		return this.init.toString();
	}

	// Factory method: not fully integrated with SGraph constructor because of Job arg (debug printing)
	// Also checks for non-deterministic payloads
	// Maybe refactor into an SGraph builder util; cf., EGraphBuilderUtil -- but not Visitor (cf., EndpointGraphBuilder), this isn't an AST algorithm
	public static SGraph buildSGraph(Map<Role, EGraph> egraphs, boolean explicit, Job job, GProtocolName fullname) throws ScribbleException
	{
		for (Role r : egraphs.keySet())
		{
			job.debugPrintln("(" + fullname + ") Building global model using EFSM for " + r + ":\n" + egraphs.get(r).init.toDot());
		}

		Map<Role, EFSM> efsms = egraphs.entrySet().stream().collect(Collectors.toMap((e) -> e.getKey(), (e) -> e.getValue().toFsm()));

		SBuffers b0 = new SBuffers(efsms.keySet(), !explicit);
		SConfig c0 = new SConfig(efsms, b0);
		SState init = new SState(c0);

		Map<Integer, SState> seen = new HashMap<>();
		LinkedHashSet<SState> todo = new LinkedHashSet<>();
		todo.add(init);

		// FIXME: factor out model building and integrate with getAllNodes (seen == all)
		int count = 0;
		while (!todo.isEmpty())
		{
			Iterator<SState> i = todo.iterator();
			SState curr = i.next();
			i.remove();
			seen.put(curr.id, curr);

			if (job.debug)
			{
				count++;
				if (count % 50 == 0)
				{
					job.debugPrintln("(" + fullname + ") Building global states: " + count);
				}
			}
			
			Map<Role, List<EAction>> fireable = curr.getFireable();

			//job.debugPrintln("Acceptable at (" + curr.id + "): " + acceptable);

			for (Role r : fireable.keySet())
			{
				List<EAction> fireable_r = fireable.get(r);
				
				// Hacky?  // FIXME: factor out and make more robust (e.g. for new state kinds) -- e.g. "hasPayload" in IOAction
				//EndpointState currstate = curr.config.states.get(r);
				EFSM currfsm = curr.config.efsms.get(r);
				EStateKind k = currfsm.getStateKind();
				if (k == EStateKind.OUTPUT)
				{
					for (EAction a : fireable_r)  // Connect implicitly has no payload (also accept, so skip)
					{
						if (fireable_r.stream().anyMatch((x) ->
								!a.equals(x) && a.peer.equals(x.peer) && a.mid.equals(x.mid) && !a.payload.equals(x.payload)))
						{
							throw new ScribbleException("Bad non-deterministic action payloads: " + fireable_r);
						}
					}
				}
				else if (k == EStateKind.UNARY_INPUT || k == EStateKind.POLY_INPUT || k == EStateKind.ACCEPT)
				{
					for (EAction a : fireable_r)
					{
						if (currfsm.getAllFireable().stream().anyMatch((x) ->
								!a.equals(x) && a.peer.equals(x.peer) && a.mid.equals(x.mid) && !a.payload.equals(x.payload)))
						{
							throw new ScribbleException("Bad non-deterministic action payloads: " + currfsm.getAllFireable());
						}
					}
				}
			}  // Need to do all action payload checking before next building step, because doing sync actions will also remove peer's actions from takeable set

			for (Role r : fireable.keySet())
			{
				List<EAction> fireable_r = fireable.get(r);
				
				for (EAction a : fireable_r)
				{
					if (a.isSend() || a.isReceive() || a.isDisconnect())
					{
						getNextStates(todo, seen, curr, a.toGlobal(r), curr.fire(r, a));
					}
					else if (a.isAccept() || a.isConnect())
					{	
						List<EAction> as = fireable.get(a.peer);
						EAction d = a.toDual(r);
						if (as != null && as.contains(d))
						{
							as.remove(d);  // Removes one occurrence
							//getNextStates(seen, todo, curr.sync(r, a, a.peer, d));
							SAction g = (a.isConnect()) ? a.toGlobal(r) : d.toGlobal(a.peer);  // Edge will be drawn as the connect, but should be read as the sync. of both -- something like "r1, r2: sync" may be more consistent (or take a set of actions as the edge label)
							getNextStates(todo, seen, curr, g, curr.sync(r, a, a.peer, d));
						}
					}
					else if (a.isWrapClient() || a.isWrapServer())
					{
						List<EAction> as = fireable.get(a.peer);
						EAction w = a.toDual(r);
						if (as != null && as.contains(w))
						{
							as.remove(w);  // Removes one occurrence
							SAction g = (a.isConnect()) ? a.toGlobal(r) : w.toGlobal(a.peer);
							getNextStates(todo, seen, curr, g, curr.sync(r, a, a.peer, w));
						}
					}
					else
					{
						throw new RuntimeException("Shouldn't get in here: " + a);
					}
				}
			}
		}

		SGraph graph = new SGraph(fullname, seen, init);

		job.debugPrintln("(" + fullname + ") Built global model..\n" + graph.init.toDot() + "\n(" + fullname + ") .." + graph.states.size() + " states");

		return graph;
	}

	private static void getNextStates(LinkedHashSet<SState> todo, Map<Integer, SState> seen, SState curr, SAction a, List<SConfig> nexts)
	{
		for (SConfig next : nexts)
		{
			SState news = new SState(next);
			SState succ = null; 
			//if (seen.contains(succ))  // FIXME: make a SGraph builder
			/*if (seen.containsValue(succ))
			{
				for (WFState tmp : seen)
				{
					if (tmp.equals(succ))
					{
						succ = tmp;
					}
				}
			}*/
			for (SState tmp : seen.values())  // Key point: checking "semantically" if model state already created
			{
				if (tmp.equals(news))
				{
					succ = tmp;
				}
			}
			if (succ == null)
			{
				for (SState tmp : todo)  // If state created but not "seen" yet, then it will be "todo"
				{
					if (tmp.equals(news))
					{
						succ = tmp;
					}
				}
			}
			if (succ == null)
			{
				succ = news;
				todo.add(succ);
			}
			//curr.addEdge(a.toGlobal(r), succ);
			curr.addEdge(a, succ);  // FIXME: make a Builder util, cf. EGraphBuilderUtil
			//if (!seen.contains(succ) && !todo.contains(succ))
			/*if (!seen.containsKey(succ.id) && !todo.contains(succ))
			{
				todo.add(succ);
			}*/
		}
	}
}
