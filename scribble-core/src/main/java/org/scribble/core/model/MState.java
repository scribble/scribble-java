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
package org.scribble.core.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.core.type.kind.ProtoKind;
import org.scribble.util.RuntimeScribException;

public abstract class MState
<
		L,                             // Node label type (cosmetic)
		A extends MAction<K>,          // Edge type
		S extends MState<L, A, S, K>,  // State type
		K extends ProtoKind            // Global/Local -- CHECKME: useful?
>
{
	private static int count = 1;  // A shared index counter for every single MState (and subclass) instance
	
	public final int id;

	// Labels and edges are mutable (via protected methods)
	protected final Set<L> labs;  // Was RecVar and SubprotocolSigs, now using inlined protocol for FSM building so just RecVar
	protected final List<A> actions;
	protected final List<S> succs;
	
	public MState(Set<L> labs)
	{
		this.id = MState.count++;
		this.labs = new HashSet<>(labs);
		this.actions = new LinkedList<>();
		this.succs = new LinkedList<>();
	}
	
	// Mutating setter
	protected final void addLabel(L lab)
	{
		this.labs.add(lab);
	}
	
	public final Set<L> getLabels()
	{
		return Collections.unmodifiableSet(this.labs);
	}
	
	// Mutating setter (can also overwrite edges)
	protected void addEdge(A a, S s)
	{
		/*if (this.equals(s)) 
		{
			// CHECKME: refactor unfair transform to eliminate this special case -- or better to have? generalise to all "identical" non-det paths?...
			// ...special case was previously for all non-det "this-a->s" for the same "s", but also works when specialised to recursive such cases, i.e., this.equals(s) (because only recursion is relevant to unfair transform)
			//
			// Needed? -- seems so, for the unfair transform (specifically for recursive non-det paths of length 1, see good.efsm.gchoice.Test09)
			// Cf. EState.unfairTransform... ? -- if (curr.getStateKind() == EStateKind.OUTPUT && curr.getActions().size() > 1)  // >1 is what makes this algorithm terminating
			// ...because of unfairClone, na.equals(a) && ns.equals(succ) -- a pair of non-det (a, s) edges will *both* be added in the unfair-clone, instead of pruned down to one output-case, so above >1 termination condition never reached
			Iterator<A> as = this.actions.iterator(); 
			Iterator<S> ss = this.succs.iterator();
			while (as.hasNext())  // Duplicate edges preemptively pruned here, but could leave to later minimisation
			{
				A na = as.next();  // N.B. cannot "inline" into below if-condition, due to short circuiting
				S ns = ss.next();
				if (na.equals(a) && ns.equals(s))
				{
					return;
				}
			}
		}*/
		this.actions.add(a);
		this.succs.add(s);
	}
	
	// Pre: (this, a, s) is a current edge -- mutating setter
	protected final void removeEdge(A a, S s)
			//throws ScribException  // CHECKME: used? -- cf., Hack? EFSM building on bad-reachability protocols now done before actual reachability check
	{
		Iterator<A> as = this.actions.iterator();
		Iterator<S> ss = this.succs.iterator();
		while (as.hasNext())
		{
			A na = as.next();  // N.B. cannot "inline" into below if-condition, due to short circuiting
			S ns = ss.next();
			if (na.equals(a) && ns.equals(s))
			{
				as.remove();  // Must follow next
				ss.remove();
				return;
			}
		}
		throw new RuntimeException("No such transition: " + a + "->" + s);
	}

	public final List<A> getActions()
	{
		return Collections.unmodifiableList(this.actions);
	}
	
	public final boolean hasAction(A a)
	{
		return this.actions.contains(a);
	}

	public final List<S> getSuccs()
	{
		return Collections.unmodifiableList(this.succs);
	}

	public final List<S> getSuccs(A a)
	{
		Iterator<A> as = this.actions.iterator();
		Iterator<S> ss = this.succs.iterator();
		List<S> res = new LinkedList<>();
		while (as.hasNext())
		{
			A na = as.next();
			S ns = ss.next();
			if (na.equals(a))
			{
				res.add(ns);
			}
		}
		return res;
	}
	
	// Variant of getActions with implicit run-time check on determinism -- currently used by codegen utils (that have that assumption)
	// (Pre: actions are deterministic)
	public final List<A> getDetActions()
	{
		Set<A> as = new HashSet<>(this.actions);
		if (as.size() != this.actions.size())
		{
			throw new RuntimeScribException("[TODO] Non-deterministic state: "
					+ this.actions + "  (Try -minlts if available)");
					// This getter checks for determinism -- mainly affects API generation  
		}
		return getActions();
	}

	// Variant with implicit run-time check on determinism
	// (Pre: actions are deterministic)
	public S getDetSuccessor(A a)
	{
		Set<A> as = new HashSet<>(this.actions);
		if (as.size() != this.actions.size())
		{
			throw new RuntimeException("[FIXME] : " + this.actions);
		}
		return getSuccs(a).get(0);
	}

	public final boolean isTerminal()
	{
		return this.actions.isEmpty();
	}

	public S getTerminal()
	{
		//getReachableStates().stream().filter(x -> x.isTerminal()).findFirst();
		Set<S> terms = getReachableStates().stream()
				.filter(s -> s.isTerminal()).collect(Collectors.toSet());
		if (terms.size() > 1)
		{
			throw new RuntimeException("Shouldn't get in here: " + terms);
		}
		return terms.isEmpty() //.isPresent()
				? null : terms.iterator().next();  // CHECKME: return empty Set instead of null?  null used by EState.toGraph
	}

	// CHECKME: add "caching" versions to, e.g., Graphs?
	// N.B. doesn't implicitly include start (only if start is reachable from start by at least one transition)
  // Concrete subclass implementation should call, e.g., getReachableStatesAux(this) -- for S param, putting "this" into Map
	public abstract Set<S> getReachableStates();

	// N.B. doesn't implicitly include start (only if start is explicitly reachable from start by at least one transition)
	protected Set<S> getReachableStatesAux(S start)
	{
		Map<Integer, S> all = new HashMap<>();
		Map<Integer, S> todo = new LinkedHashMap<>();  // Linked unnecessary, but to follow the iteration pattern
		todo.put(this.id, start);  // Suppressed: assumes ModelState subclass correctly instantiates S parameter
		while (!todo.isEmpty())
		{
			Iterator<Entry<Integer, S>> i = todo.entrySet().iterator();
			Entry<Integer, S> next = i.next();
			i.remove();  // Must follow next
			for (S s : next.getValue().getSuccs())
			{
				if (!all.containsKey(s.id))
				{	
					all.put(s.id, s);
					todo.put(s.id, s);
				}
			}
		}
		return new HashSet<>(all.values());  // Often will want to add this
	}

	public Set<A> getReachableActions()
	{
		return getReachableStates().stream().flatMap(x -> x.getActions().stream())
				.collect(Collectors.toSet());
	}
	
	public boolean canReach(MState<L, A, S, K> s)
	{
		return getReachableStates().contains(s);
	}

	@Override
	public String toString()
	{
		return Integer.toString(this.id);  // CHECKME: ?
	}

	@Override
	public int hashCode()
	{
		int hash = 73;
		hash = 31 * hash + this.id;  // N.B. using state ID only
		return hash;
	}

	// N.B. Based only on state ID
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof MState))
		{
			return false;
		}
		MState<?, ?, ?, ?> them = (MState<?, ?, ?, ?>) o;
		return them.canEquals(this) && this.id == them.id;
				// Convenient to use id, due to edge mutability
	}
	
	protected abstract boolean canEquals(MState<?, ?, ?, ?> s);
}













	/*
	public final List<S> getDetSuccessors()
	{
		Set<A> as = new HashSet<>(this.actions);
		if (as.size() != this.actions.size())
		{
			throw new RuntimeScribException("[TODO] Non-deterministic state: "
					+ this.actions + "  (Try -minlts if available)");
					// This getter checks for determinism -- affects e.g. API generation  
		}
		return getSuccessors();
	}

	// TODO: make protected
	public static <L, A extends MAction<K>, S extends MState<L, A, S, K>, K extends ProtoKind>
			S getTerminal(S start)
	{
		if (start.isTerminal())
		{
			return start;
		}
		Set<S> terms = start.getReachableStates().stream()
				.filter(s -> s.isTerminal()).collect(Collectors.toSet());
		if (terms.size() > 1)
		{
			throw new RuntimeException("Shouldn't get in here: " + terms);
		}
		return (terms.isEmpty()) ? null : terms.iterator().next();  // FIXME: return empty Set instead of null?
	}

	// Note: doesn't implicitly include start (only if start is explicitly reachable from start, of course)
	/*public static <A extends ModelAction<K>, S extends ModelState<A, S, K>, K extends ProtocolKind>
			Set<S> getAllReachable(S start)* /
	// TODO: make protected
	// CHECKME: cache results?
	@SuppressWarnings("unchecked")
	public static <L, A extends MAction<K>, S extends MState<L, A, S, K>, K extends ProtoKind>
			Set<S> getReachableStates(MState<L, A, S, K> start)
	{
		Map<Integer, S> all = new HashMap<>();
		Map<Integer, S> todo = new LinkedHashMap<>();
		todo.put(start.id, (S) start);  // Suppressed: assumes ModelState subclass correctly instantiates S parameter
		while (!todo.isEmpty())
		{
			Iterator<S> i = todo.values().iterator();
			S next = i.next();
			todo.remove(next.id);
			/*if (all.containsKey(next.id))
			{
				continue;
			}
			all.put(next.id, next);* /
			for (S s : next.getAllSuccessors())
			{
				/*if (!all.containsKey(s.id) && !todo.containsKey(s.id))
				{
					todo.put(s.id, s);
				}* /
				if (!all.containsKey(s.id))
				{	
					all.put(s.id, s);
					//if (!todo.containsKey(s.id))  // Redundant
					{
						todo.put(s.id, s);
					}
				}
			}
		}
		return new HashSet<>(all.values());
	}
	
	@SuppressWarnings("unchecked")
	// TODO: make protected
	// CHECKME: cache results?
	public static <L, A extends MAction<K>, S extends MState<L, A, S, K>, K extends ProtoKind>
			//Set<A> getAllReachableActions(S start)
			Set<A> getReachableActions(MState<L, A, S, K> start)
	{
		Set<S> all = new HashSet<>();
		all.add((S) start);  // Suppressed: assumes ModelState subclass correctly instantiates S parameter
		all.addAll(MState.getReachableStates(start));
		Set<A> as = new HashSet<>();
		for (S s : all)
		{
			as.addAll(s.getAllActions());
		}
		return as;
	}*/