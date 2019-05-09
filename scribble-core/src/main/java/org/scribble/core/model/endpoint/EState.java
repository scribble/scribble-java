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
package org.scribble.core.model.endpoint;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.scribble.core.model.MPrettyState;
import org.scribble.core.model.MState;
import org.scribble.core.model.ModelFactory;
import org.scribble.core.model.endpoint.actions.EAction;
import org.scribble.core.model.visit.local.EStateVisitor;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.RecVar;
import org.scribble.util.Pair;
import org.scribble.util.ScribException;

// Label types used to be both RecVar and SubprotocolSigs; now using inlined protocol for FSM building so just RecVar
public class EState extends MPrettyState<RecVar, EAction, EState, Local>
{
	protected EState(Set<RecVar> labs)
	{
		super(labs);
	}
	
	// To be overridden by subclasses, to obtain the subclass nodes
  // CHECKME: remove labs arg, and modify the underlying Set if needed ?
	protected EState cloneNode(ModelFactory mf, Set<RecVar> labs)
	{
		//return ef.newEState(this.labs);
		return mf.local.EState(labs);
	}

	// Fully clones the reachable graph (i.e. the "general" graph -- cf., EGraph, the specific Scribble concept of an endpoint protocol graph)
	// N.B. cloning means the states, actions are immutable
	protected EState clone(ModelFactory mf)
	{
		Map<Integer, EState> clones = new HashMap<>();  // Original s.id -> clone
		Function<EState, EState> getClone = x ->
		{
			EState clone = clones.get(x.id);  // "clones" is effectively final
			if (clone == null)
			{
				clone = x.cloneNode(mf, x.labs);
				clones.put(x.id, clone);
			}
			return clone;
		};
		Set<EState> all = getReachableStates();
		all.add(this);
		for (EState s : all)  // Quadratic after getReachableStates, but simpler code for now
		{
			Iterator<EAction> as = s.getActions().iterator();
			Iterator<EState> succs = s.getSuccs().iterator();
			EState clone = getClone.apply(s);
			while (as.hasNext())
			{
				clone.addEdge(as.next(), getClone.apply(succs.next()));
			}
		}
		return clones.get(this.id);
	}

	// Pre: succ is the root of the subgraph, and succ is a successor of *"this"* (which is inside the subgraph)
	// i.e., this-a->succ ("a" is possibly non-det)
	// Returns the clone of the subgraph rooted at succ, with all non- "this-a->succ" actions pruned from the clone of "this" state
	// i.e., we took "a" from "this" to get to succ (the subgraph root); if we enter "this" again (inside the subgraph), then always take "a" again
	// N.B. cloning means the states, actions are immutable
	// Consider gproto annotations to specify which states to unfair-clone, i.e., choice-specific fairness
	private EState unfairCloneSubgraph(ModelFactory mf, EState term, EAction a, EState succ) // Need succ param for non-det
	{
		Map<Integer, EState> clones = new HashMap<>();  // Original s.id -> clone
		Function<EState, EState> getClone = x ->
		{
			EState clone = clones.get(x.id);  // "clones" is effectively final
			if (clone == null)
			{
				clone = (term != null && x.id == term.id)
						? term
								// Special case: term not cloned, otherwise a mixture of exapanded (and non-expanded) cases may lead to the term being cloned...
								// ...potentially multiple times and potentially while the original still remains
						: x.cloneNode(mf, Collections.emptySet());  
								// CHECKME: remove labs arg from cloneNode and just clear the lab set here ?
								// Cf. clone, keeps x.labs
				clones.put(x.id, clone);
			}
			return clone;
		};
		Set<EState> all = succ.getReachableStates();
		all.add(succ);
		for (EState s : all)  // Quadratic after getReachableStates, but simpler code for now
		{
			Iterator<EAction> as = s.getActions().iterator();
			Iterator<EState> succs = s.getSuccs().iterator();
			EState clone = getClone.apply(s);
			boolean done = false;  
					// Restricts adding of *at most* one "this-a->succ" edge out of potentially multiple non-det "a"
					// i.e., unfair expansion prunes non-det "a" ("determinizes" entry) from "this" into the cloned subgraph
					// Cf. MState.addEdge(A, S), previously implicitly pruned all non-det "this-a->s" for the same "s"
			while (as.hasNext())
			{
				EAction na = as.next();
				EState ns = succs.next();
				if (s.id != this.id)
				{
					clone.addEdge(na, getClone.apply(ns));
				}
				else if (na.equals(a) && ns.equals(succ) && !done)  
						// Non-det (to different succ) also pruned from clone of this -- but OK? non-det still preserved on original state, so any safety violations due to non-det will still come out?...
						// ...this is like non-fairness extended to defeat even non-determinism ?
				{
					// s.id == this.id, so "clone" is the clone of "this"
					clone.addEdge(na, getClone.apply(ns));
					done = true;
				}
			}
		}
		return clones.get(succ.id);
	}
	

	/* Returns Pair<>(init, term)
	 * 
	.. use getallreachable to get subgraph, make a graph clone method
	.. for each poly output, clone a (non-det) edge to clone of the reachable subgraph with the clone of the current node pruned to this single choice
	..     be careful of original non-det edges, need to do each separately
	.. do recursively on the subgraphs, will end up with a normal form with subgraphs without output choices
	.. is it equiv to requiring all roles to see every choice path?  except initial accepting roles -- yes
	.. easier to implement as a direct check on the standard global model, rather than model hacking -- i.e. liveness is not just about terminal sets, but about "branching condition", c.f. julien?
	.. the issue is connect/accept -- makes direct check a bit more complicated, maybe value in doing it by model hacking to rely on standard liveness checking?
	..     should be fine, check set of roles on each path is equal, except for accept-guarded initial roles
	*/
	public Pair<EState, EState> unfairTransform(ModelFactory mf)
	{
		EState init = clone(mf);  // All state refs from here on are clones, original Graph unmodified
		EState term = init.getTerminal();
		Set<EState> seen = new HashSet<>();
		Set<EState> todo = new LinkedHashSet<>();  // Linked unnecessary, but to follow the iteration pattern
		todo.add(init);
		while (!todo.isEmpty())
		{
			Iterator<EState> i = todo.iterator();
			EState curr = i.next();
			i.remove();
			if (seen.contains(curr))  
					// Convenient to check here (even though may get curr multiple times) to avoid doing the check before every todo.add
					// N.B. todo is also aliased from expandUnfairCases
			{
				continue;
			}
			seen.add(curr);
			
			if (curr.getStateKind() == EStateKind.OUTPUT
					&& curr.getActions().size() > 1)  // >1 makes this algorithm terminating -- in the expanded subgraph, the clone of curr will have all other edges pruned
			{
				expandUnfairCases(mf, term, todo, curr);
			}
			else
			{
				todo.addAll(curr.getSuccs());
			}
		}
		return new Pair<>(init, term);
	}

	// N.B. todo alias from unfairTransform -- refactor as return? (slower)
	// Pre: curr.getStateKind() == EStateKind.OUTPUT && curr.getActions().size() > 1
	// All involved states are already clones
	private void expandUnfairCases(ModelFactory mf, EState term, Set<EState> todo,
			EState curr)
	{
		Iterator<EAction> as = curr.getActions().iterator();
		Iterator<EState> succs = curr.getSuccs().iterator();
		List<EAction> aclones = new LinkedList<>();  // Actions involved in clones (not actually cloned themselves, immutable)
		List<EState> succClones = new LinkedList<>();
		Map<EState, List<EAction>> toRemove = new HashMap<>();  // Original succ -> actions "a" where "curr-a->succ"
				// List needed to handle removing multiple edges to the same state: e.g. mu X.(A->B:1 + A->B:2).X

		while (as.hasNext())
		{
			EAction a = as.next();
			EState succ = succs.next();
			if (!succ.canReach(curr))  // Do unfairCloneSubgraph for each (potentially) "recursive" action
			{
				todo.add(succ);
				continue;
			}
			EState succClone = curr.unfairCloneSubgraph(mf, term, a, succ);  // succ is a successor of curr
			aclones.add(a);
			succClones.add(succClone);
			List<EAction> tmp = toRemove.get(succ);
			if (tmp == null)
			{
				tmp = new LinkedList<>();
				toRemove.put(succ, tmp);
			}
			tmp.add(a);  // To replace original edge with new edge into unfair-cloned subgraph
		}

		if (!aclones.isEmpty())  // Redundant (toRemove would also be empty), but more clear
		{
			for (EState succ : toRemove.keySet())
			{
				for (EAction a1 : toRemove.get(succ))
				{
					curr.removeEdge(a1, succ);  // First, remove original edge to original succ
				}
			}
			Iterator<EAction> iaclones = aclones.iterator();
			Iterator<EState> isuccClones = succClones.iterator();
			while (iaclones.hasNext())
			{
				EAction a = iaclones.next();  // Same actions involved in toRemove loop above
				EState succClone = isuccClones.next();  // Clones of the succs involved in toRemove loop above
				curr.addEdge(a, succClone);  // Second, add edge to cloned succ (entry to the unfair-cloned subgraph)
				todo.add(succClone);  // This will cause unfairTransform to "recursively" visit the nodes of the unfair-cloned subgraph
						// Doesn't work if non-det preserved by the "unfairClone" aux (recursively, edges always >1 -- termination condition never met)
						// Idea is to bypass succ clone (for non-det, edges>1) but in general this will be cloned again before returning to it, so bypass doesn't work -- to solve this more generally probably need to keep a record of all clones to bypass future clones
			}
		}
	}
	
	// CHECKME: make an isSync in IOAction ?
  // This state is a *necessarily* "blocking" output sync-client (request, clientwrap) state
	public boolean isSyncClientOnly()
	{
		return getStateKind() == EStateKind.OUTPUT
				&& getActions().stream()
						.allMatch(x -> x.isRequest() || x.isClientWrap());
	}
	
	// CHECKME: return
	public void traverse(EStateVisitor v) throws ScribException
	{
		visitState(v);  // "visitNode"

		// "visitChildren"
		Iterator<EAction> as = this.actions.iterator();
		Iterator<EState> succs = this.succs.iterator();
		while (as.hasNext())
		{
			EAction a = as.next();
			EState succ = succs.next();
			if (!v.hasSeen(succ))  // cf. EdgeVisitor
			{
				succ.traverse(v.enter(this, a, succ));
			}
		}
	}
	
	protected void visitState(EStateVisitor v) throws ScribException
	{
		// "visitNode"
		EStateKind kind = getStateKind();  // CHECKME: explicitly cache kind in EState?  this lookup is slow?
		switch (kind)
		{
			case ACCEPT: v.visitAccept(this); break;
			case OUTPUT: v.visitOutput(this); break;
			case POLY_RECIEVE: v.visitPolyInput(this); break;
			case SERVER_WRAP: v.visitServerWrap(this); break;
			case TERMINAL: v.visitTerminal(this); break;
			case UNARY_RECEIVE: v.visitUnaryInput(this); break;
			default: throw new RuntimeException("Unknown state kind: " + kind);
		}
	}
	
	public EStateKind getStateKind()
	{
		List<EAction> as = this.getActions();
		if (as.size() == 0)
		{
			return EStateKind.TERMINAL;
		}
		else
		{
			if (as.stream()
					.allMatch(a -> a.isSend() || a.isRequest() || a.isClientWrap()))  // ClientWrap should be unary?
			{
				return EStateKind.OUTPUT;
			}
			else if (as.stream().allMatch(EAction::isReceive))
			{
				return (as.size() == 1) ? EStateKind.UNARY_RECEIVE : EStateKind.POLY_RECIEVE;
			}
			else if (as.stream().allMatch(EAction::isAccept))
			{
				return EStateKind.ACCEPT;  // Distinguish unary for API gen?  cf. receive
			}
			else if (as.size() == 1 && as.get(0).isDisconnect())
			{
				return EStateKind.OUTPUT;
			}
			else if (as.size() == 1 && as.get(0).isServerWrap())
			{
				return EStateKind.SERVER_WRAP;
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + as);
			}
		}
	}
	
	/*// Helper factory method for deriving an EGraph from an arbitary EState (but not the primary way to construct EGraphs; cf., EGraphBuilderUtil)
	public EGraph toGraph()
	{
		return new EGraph(this, getTerminal());  // Throws exception if >1 terminal; null if no terminal
	}*/

	@Override
	public Set<EState> getReachableStates()  // CHECKME: consider a "lazy" version?  Maybe as an Iterator or Stream
	{
		return getReachableStatesAux(this);
	}

	@Override
	public int hashCode()
	{
		int hash = 83;
		hash = 31 * hash + super.hashCode();  // N.B. ultimately uses state ID only
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
