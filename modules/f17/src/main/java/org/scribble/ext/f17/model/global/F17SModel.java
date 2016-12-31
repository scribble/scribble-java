package org.scribble.ext.f17.model.global;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.ESend;
import org.scribble.sesstype.name.Role;

// 1-bounded LTS
// Factor out with SGraph/SModel?
public class F17SModel
{
	public final Map<Role, EState> E0;
	public final F17SState init;
	
	public Map<Integer, F17SState> allStates; // State ID -> GMState

	private Map<Integer, Set<Integer>> reach; // State ID -> reachable states (not reflexive)
	private Set<Set<Integer>> termSets;

	protected F17SModel(Map<Role, EState> E0, F17SState init, Map<Integer, F17SState> allStates)
	{
		this.E0 = Collections.unmodifiableMap(E0);
		this.init = init;
		this.allStates = Collections.unmodifiableMap(allStates);

		this.reach = getReachabilityMap();
		this.termSets = findTerminalSets();
	}
	
	public F17SafetyErrors getSafetyErrors()
	{
		Set<F17SState> conns = this.allStates.values().stream().filter((s) -> s.isConnectionError()).collect(Collectors.toSet());
		Set<F17SState> disconns = this.allStates.values().stream().filter((s) -> s.isDisconnectedError()).collect(Collectors.toSet());
		Set<F17SState> unconns = this.allStates.values().stream().filter((s) -> s.isUnconnectedError()).collect(Collectors.toSet());
		Set<F17SState> syncs = this.allStates.values().stream().filter((s) -> s.isSynchronisationError()).collect(Collectors.toSet());
		Set<F17SState> recepts = this.allStates.values().stream().filter((s) -> s.isReceptionError()).collect(Collectors.toSet());
		Set<F17SState> unfins = this.allStates.values().stream().filter((s) -> s.isUnfinishedRoleError(this.E0)).collect(Collectors.toSet());
		Set<F17SState> orphans = this.allStates.values().stream().filter((s) -> s.isOrphanError(this.E0)).collect(Collectors.toSet());
		return new F17SafetyErrors(conns, disconns, unconns, syncs, recepts, unfins, orphans);
	}
	
	public boolean isActive(F17SState s, Role r)
	{
		return F17SState.isActive(s.getP().get(r), this.E0.get(r).id);
	}
	
	public F17ProgressErrors getProgressErrors()
	{
		Map<Role, Set<Set<F17SState>>> roleProgress = new HashMap<>();
				/*this.E0.keySet().stream().collect(Collectors.toMap((r) -> r, (r) ->
					this.termSets.stream().map((ts) -> ts.stream().map((i) -> this.allStates.get(i)).collect(Collectors.toSet()))
						.filter((ts) -> ts.stream().allMatch((s) -> !s.getSubjects().contains(r)))
							.collect(Collectors.toSet())));*/
		for (Role r : this.E0.keySet())
		{
			for (Set<Integer> ts : this.termSets)	
			{
				if (ts.stream().allMatch((i) -> isActive(this.allStates.get(i), r)
						&& !this.allStates.get(i).getSubjects().contains(r)))
				{
					Set<Set<F17SState>> set = roleProgress.get(r);
					if (set == null)
					{
						set = new HashSet<>();
						roleProgress.put(r, set);
					}
					set.add(ts.stream().map((i) -> this.allStates.get(i)).collect(Collectors.toSet()));
				}	
			}
		}

		Map<ESend, Set<Set<F17SState>>> eventualReception = new HashMap<>();
		for (Role r1 : this.E0.keySet())
		{
			for (Role r2 : this.E0.keySet())
			{
				if (!r1.equals(r2))
				{
					for (Set<Integer> ts : this.termSets)	
					{
						F17SState s1 = this.allStates.get(ts.iterator().next());
						ESend es = s1.getQ().get(r1).get(r2);

						if (es != null && !(es instanceof F17EBot)
								&& ts.stream().allMatch((i) -> es.equals(this.allStates.get(i).getQ().get(r1).get(r2))))
						{
							Set<Set<F17SState>> set = eventualReception.get(es);
							if (set == null)
							{
								set = new HashSet<Set<F17SState>>();
								eventualReception.put(es,  set);
							}
							set.add(ts.stream().map((i) -> this.allStates.get(i)).collect(Collectors.toSet()));
						}
					}
				}
			}
		}
		
		return new F17ProgressErrors(roleProgress, eventualReception);
	}
	
	@Override
	public String toString()
	{
		return this.init.toString();
	}
	
	public String toDot()
	{
		return this.init.toDot();
	}
	
	@Override
	public final int hashCode()
	{
		int hash = 2887;
		hash = 31 * hash + this.init.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof F17SModel))
		{
			return false;
		}
		return this.init.id == ((F17SModel) o).init.id;
	}

	
	/**
	 *  Duplicated from SGraph
	 */

	public Set<Set<Integer>> getTerminalSets()
	{
		return this.termSets;
	}

	public Set<Set<Integer>> findTerminalSets()
	{
		Set<Set<Integer>> termSets = new HashSet<>();
		Set<Set<Integer>> checked = new HashSet<>();
		for (Integer i : reach.keySet())
		{
			F17SState s = this.allStates.get(i);
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
		//this.termSets = Collections.unmodifiableSet(termSets);
		return termSets;
	}

	private boolean isTerminalSetMember(F17SState s)
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

	/*// Pre: reach.get(start).contains(end) // FIXME: will return null if initial
	// state is error
	public List<SAction> getTrace(F17SState start, F17SState end)
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
			SortedMap<Integer, Set<Integer>> candidates, F17SState end)
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

		F17SState curr = this.states.get(currid);
		Iterator<SAction> as = curr.getAllActions().iterator();
		Iterator<F17SState> ss = curr.getAllSuccessors().iterator();
		while (as.hasNext())
		{
			SAction a = as.next();
			F17SState s = ss.next();
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
	}*/

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
		for (F17SState s : this.allStates.values())
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
			for (F17SState s2 : this.allStates.get(s1id).getAllSuccessors())
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
}
