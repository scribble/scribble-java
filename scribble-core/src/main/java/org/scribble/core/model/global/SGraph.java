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
package org.scribble.core.model.global;

import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.scribble.core.model.MPrettyPrint;
import org.scribble.core.model.global.actions.SAction;
import org.scribble.core.type.name.GProtoName;
import org.scribble.util.Pair;

public class SGraph implements MPrettyPrint
{
	public final GProtoName proto;  // For debugging only?  deprecate?
	
	public final SState init;
	public final Map<Integer, SState> states;  // s.id -> s

	private final Set<Set<SState>> termSets;

	// Unlike EState, SGraph is not just a "simple wrapper" for an existing graph of nodes -- it is a computed structure, so no lightweight "toGraph" wrapper method; cf., EState
	protected SGraph(GProtoName proto, Map<Integer, SState> states, SState init)
	{
		this.proto = proto;
		this.init = init;
		this.states = Collections.unmodifiableMap(states);  // s.id -> s
		//this.reach = getReachabilityMap();
		
		// TODO: refactor
		tarjan();
		Set<Set<SState>> termSets = new HashSet<>();
		for (Set<SState> scc : this.sscs)
		{
			if (scc.size() == 1 && scc.iterator().next().isTerminal() 
					|| scc.stream().anyMatch(
							y -> y.getSuccs().stream().anyMatch(x -> !scc.contains(x))))
			{
				continue;
			}
			//termSets.add(scc.stream().map(x -> x.id).collect(Collectors.toSet()));
			termSets.add(Collections.unmodifiableSet(scc));
		}
		this.termSets = Collections.unmodifiableSet(termSets);
	}
	
	public Set<Set<SState>> getTermSets()
	{
		return this.termSets;  // Already unmodifiable
	}

	// Returns null if end cannot be reached
	public List<SAction> getTraceFromInit(SState end)
	{
		Map<Integer, Pair<SAction, Integer>> traces = bfsFromInit(end);
		LinkedList<SAction> trace = new LinkedList<>();
		for (int id = end.id; id != init.id; )  // Skipped if init.id == end.id (e.g., error in init state, such as a bad connect)
		{
			Pair<SAction, Integer> p = traces.get(id);
			trace.push(p.left);
			id = p.right;
		}
		return trace;
	}

	// Returns null if end cannot be reached
	private Map<Integer, Pair<SAction, Integer>> bfsFromInit(SState end)  // (cf., Dijkstra's with all weights 1)
	{
		// SState.id faster as keys than full SConfig
		Set<Integer> seen = new HashSet<>();
		List<Integer> todo = new LinkedList<>();
		seen.add(this.init.id);
		todo.add(this.init.id);
		Map<Integer, Pair<SAction, Integer>> traces = new HashMap<>();
		while (!todo.isEmpty())
		{
			Iterator<Integer> i = todo.iterator();
			Integer currid = i.next();
			i.remove();
			if (currid == end.id)
			{
				return traces;
			}
			SState curr = this.states.get(currid);
			Iterator<SAction> as = curr.getActions().iterator();  // CHECKME: how about non-det actions? (cf. below, todo.contains(succ.id)? e.g., bad.liveness.messagelive.Test01)
			for (SState succ : curr.getSuccs())
			{
				SAction a = as.next();
				if (seen.contains(succ.id) || todo.contains(succ.id))
				{
					continue;
				}
				seen.add(succ.id);
				traces.put(succ.id, new Pair<>(a, currid));
				todo.add(succ.id);
			}
		}
		return null;
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

	// Following are "one-time" usage, on instance construction
	// SState.id faster as keys than full SConfig
	private int counter = 0;
	private Map<Integer, Integer> indices = new HashMap<>();  // s.id -> index
	private Map<Integer, Integer> lowlinks = new HashMap<>();  // s.id -> lowlink
	private Deque<SState> stack = new LinkedList<>();
	private Set<Integer> onStack = new HashSet<>();
	private Set<Set<SState>> sscs = new HashSet<>();

	private void tarjan()
	{
		for (SState v : this.states.values())
		{
			if (!indices.containsKey(v.id))
			{
				strongConnect(v);
			}
		}
	}

	private void strongConnect(SState v)
	{
		// Set the depth index for v to the smallest unused index
		int index = this.counter++;
		this.indices.put(v.id, index);
		this.lowlinks.put(v.id, index);
		this.stack.push(v);
		this.onStack.add(v.id);

		// Consider successors of v
		for (SState w : v.getSuccs())
		{
			if (!this.indices.containsKey(w.id))
			{
				// Successor w has not yet been visited; recurse on it
				strongConnect(w);
				int vlowlink = this.lowlinks.get(v.id);
				int wlowlink = this.lowlinks.get(w.id);
				this.lowlinks.put(v.id, (vlowlink <= wlowlink ? vlowlink : wlowlink));
			}
			else if (this.onStack.contains(w.id))
			{
				// Successor w is in stack S and hence in the current SCC
				// If w is not on stack, then (v, w) is a cross-edge in the DFS tree and must be ignored
				// Note: The next line may look odd - but is correct.
				// It says w.index not w.lowlink; that is deliberate and from the original paper
				int vlowlink = this.lowlinks.get(v.id);
				int windex = this.indices.get(w.id);
				this.lowlinks.put(v.id, (vlowlink <= windex ? vlowlink : windex));
			}
		}

		// If v is a root node, pop the stack and generate an SCC
		if (this.lowlinks.get(v.id) == this.indices.get(v.id))
		{
			Set<SState> ssc = new HashSet<>();
			SState w;
			do
			{
				w = this.stack.pop();
				this.onStack.remove(w.id);
				ssc.add(w);
			}
			while (w.id != v.id);
			this.sscs.add(ssc);
		}
	}













	/*
	private Map<Integer, Set<Integer>> reach; // State ID -> reachable states (not reflexive)

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
		Set<Integer> rs = this.reach.get(s.id);  // Pre: rs.contains(s.id)
		Set<Integer> tmp = new HashSet<>(rs);
		tmp.remove(s.id);  // Unnecessary to remove, but this.reach.get(r).equals(rs) check below trivially holds by def
		for (Integer r : tmp)
		{
			if (!this.reach.containsKey(r) || !this.reach.get(r).equals(rs))
			{
				return false;
			}
		}
		return true;
	}

	// Not reflexive
	public Map<Integer, Set<Integer>> getReachabilityMap()
	{
		if (this.reach != null)
		{
			return this.reach;
		}

		Map<Integer, Integer> idToIndex = new HashMap<>(); // state ID -> array index
		Map<Integer, Integer> indexToId = new HashMap<>(); // array index -> state ID
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
			for (SState s2 : this.states.get(s1id).getSuccs())
			{
				reach[idToIndex.get(s1id)][idToIndex.get(s2.id)] = true;
			}
		}

		for (boolean again = true; again; )
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

	public List<SAction> getTrace(SState start, SState end)
	{
		SortedMap<Integer, Set<Integer>> candidates = new TreeMap<>();
		Set<Integer> dis0 = new LinkedHashSet<Integer>();
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
		Iterator<SAction> as = curr.getActions().iterator();
		Iterator<SState> succs = curr.getSuccs().iterator();
		while (as.hasNext())
		{
			SAction a = as.next();
			SState succ = succs.next();
			if (succ.id == end.id)
			{
				trace.add(a);
				return trace;
			}

			if (!seen.contains(succ.id))
				//&& this.reach.containsKey(s.id) && this.reach.get(s.id).contains(end.id))
			{
				seen.add(succ.id);
				if (succ.isTerminal())  // Unnecesary, visiting succ will do "while (as.hasNext())"
				{
					continue;
				}
				Set<Integer> tmp1 = candidates.get(dis+1);
				if (tmp1 == null)
				{
					tmp1 = new HashSet<>();
					candidates.put(dis+1, tmp1);
				}
				tmp1.add(succ.id);
				List<SAction> tmp2 = new LinkedList<>(trace);
				tmp2.add(a);
				List<SAction> res = getTraceAux(tmp2, seen, candidates, end);  // FIXME: replace by a loop
				if (res != null)
				{
					return res;
				}
			}
		}
		return null;
	}
	//*/
}