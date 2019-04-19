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
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.core.model.GraphBuilderUtil;
import org.scribble.core.model.ModelFactory;
import org.scribble.core.model.endpoint.actions.EAction;
import org.scribble.core.type.kind.Local;
import org.scribble.core.type.name.RecVar;
import org.scribble.util.ScribException;

	// N.B. init must be called before every "new visit", including first -- no: now init implicit on construction, and finalise also does init
// (E)GraphBuilderUtil usage contract (2): all states must be made via newState
// FIXME TODO replace EGraphBuilderUtil
// Helper class for EGraphBuilder -- can access the protected setters of EState (via superclass helper methods)
// Tailored to support graph building from syntactic local protocol choice and recursion
public class EGraphBuilderUtil
		extends GraphBuilderUtil<RecVar, EAction, EState, Local>
{
	public final ModelFactory ef;  // N.B. new states should be made by this.newState, not this.ef.newEState

	private final Map<RecVar, Deque<EState>> recvars = new HashMap<>();  // CHECKME: Deque is for shadowing?

	// First action(s) inside a rec scope ("enacting" means how to enact an unguarded choice-continue)
	// These are collected by:
	// - On each recursion entry, push a new list for that recvar -- list pushing/popping mainly for handling choices (see below)
	// - General idea: via addEdge, record the first action encountered for all "*open*" recvars, i.e., add the action to all current lists that are *still empty*
	// - For choice: 
	// -- On choice entry, first push a new list for *all* recvars for recording the choice cases (all, including "non-open", because inconvenient to distinguish open/non-open from nested contexts)
	// -- On block entry, push another new list for *all* recvars for recording the current block
	// -- (Record, via addEdge, the first action of the current block for all open recvars)
	// -- On block exit, pop the block list into the parent choice list for *all* recvars
	// -- On choice exit, pop the choice list into the parent (recursion/choice-block) list for all *open* recvars, i.e., if the parent list is *still empty*
	// -- On recursion exit, just pop the list
	private final Map<RecVar, Deque<List<EAction>>> enacting = new HashMap<>();

	//private final Map<EState, Map<RecVar, Set<EAction>>> enactingMap = new HashMap<>();  // Record enacting per-recvar, since a state could have multi-recvars
	private final Map<EState, Map<RecVar, List<EAction>>> enactingMap = new HashMap<>();

	public EGraphBuilderUtil(ModelFactory ef)
	{
		this.ef = ef;
		////clear();
		//reset();
		init(null);
	}

	/*// FIXME: cannot be reused for different protos, because of recvars and clear/reset
	// CHECKME: refactor as factory method?  (e.g., push?)
	public EGraphBuilderUtil2(EGraphBuilderUtil2 b)
	{
		this.ef = b.ef;
		init(null);  // FIXME: init param not used
		b.outerRecvars.entrySet().forEach(x ->
				this.outerRecvars.put(x.getKey(), x.getValue()));
		b.recvars.entrySet().forEach(x ->
				this.outerRecvars.put(x.getKey(), x.getValue().peek()));
	}*/
	
	// N.B. must be called before every "new visit", including first
	// FIXME: now cannot be reused for different protos, because of recvars and clear/reset
	@Override
	public void init(EState init)  // FIXME: init not used (but inherited from super)
	{
		clear();
		/*reset(this.ef.newEState(Collections.emptySet()),
				this.ef.newEState(Collections.emptySet()));*/
		reset(newState(Collections.emptySet()), newState(Collections.emptySet()));
	}

	protected void clear()
	{
		this.recvars.clear();
		this.enacting.clear();
		
		this.enactingMap.clear();
		
		this.states.clear();
	}
	
	private final Set<EState> states = new HashSet<>();
	
	// For the util to additionally record states 
	// CHECKME: make interface less messy w.r.t. this.ef.newEState 
	// CHECKME: factor up to super?
	//@Override
	public EState newState(Set<RecVar> labs)
	{
		//return new EState(labs);
		EState s = this.ef.newEState(labs);
		this.states.add(s);
		return s;
	}

	// Records 's' as predecessor state, and 'a' as previous action and the "enacting action" for "fresh" recursion scopes
	@Override
	public void addEdge(EState s, EAction a, EState succ)
	{
		addEdgeAux(s, a, succ);
		
		//for (Deque<Set<EAction>> ens : this.enacting.values())
		for (Deque<List<EAction>> ens : this.enacting.values())
		{
			if (!ens.isEmpty())  // Unnecessary?
			{
				//Set<EAction> tmp = ens.peek();
				List<EAction> tmp = ens.peek();
				if (tmp.isEmpty())
				{
					tmp.add(a);
				}
			}
		}
	}
	

	/*
	 * Dealing with Choice contexts
	 */
	public void enterChoice()
	{
		for (RecVar rv : this.enacting.keySet())
		{
			////Deque<Set<EAction>> tmp = this.enacting.get(rv);
			Deque<List<EAction>> tmp = this.enacting.get(rv);
			////tmp.push(new HashSet<>(tmp.peek()));
			//tmp.push(new HashSet<>());  // Initially empty to record nested enablings in choice blocks
			tmp.push(new LinkedList<>());  // Initially empty to record nested enablings in choice blocks
		}
	}

	public void leaveChoice()
	{
		for (RecVar rv : this.enacting.keySet())
		{
			List<EAction> pop = this.enacting.get(rv).pop();
			List<EAction> peek = this.enacting.get(rv).peek();
			if (peek.isEmpty())  // Cf. addEdge
			{
				peek.addAll(pop);
			}
		}
	}

	public void pushChoiceBlock()
	{
		for (RecVar rv : this.enacting.keySet())
		{
			//Deque<Set<EAction>> tmp = this.enacting.get(rv);
			Deque<List<EAction>> tmp = this.enacting.get(rv);
			//tmp.push(new HashSet<>());  // Must be empty for addEdge to record (nested) enabling
			tmp.push(new LinkedList<>());
		}
	}

	public void popChoiceBlock()
	{
		for (RecVar rv : this.enacting.keySet())
		{
			/*Set<EAction> pop = this.enacting.get(rv).pop();
			Set<EAction> peek = this.enacting.get(rv).peek();*/
			List<EAction> pop = this.enacting.get(rv).pop();
			List<EAction> peek = this.enacting.get(rv).peek();
			//if (peek.isEmpty())
			{
				peek.addAll(pop);
			}
		}
	}
	

	/*
	 * Dealing with Recursion contexts
	 */
	public void pushRecursionEntry(RecVar rv, EState entry)
	{
		/*if (!isUnguardedInChoice())  // Don't record rec entry if it is an unguarded choice-rec
		{
			this.entry.addLabel(recvar);
		}*/
		//this.recvars.put(recvar, this.entry);
		Deque<EState> tmp = this.recvars.get(rv);
		if (tmp == null)
		{
			tmp = new LinkedList<>();
			this.recvars.put(rv, tmp);
		}
		/*if (isUnguardedInChoice())
		{
			tmp.push(tmp.peek());  // Works because unguarded recs unfolded (including nested recvar shadowing -- if unguarded choice-rec, it will be unfolded and rec entry recorded for guarded unfolding)
		}
		else
		{
			tmp.push(this.entry);
		}*/
		tmp.push(entry);
		
		//Deque<Set<EAction>> tmp2 = this.enacting.get(recvar);
		Deque<List<EAction>> tmp2 = this.enacting.get(rv);
		if (tmp2 == null)
		{
			tmp2 = new LinkedList<>();  // New Stack for this recvar
			this.enacting.put(rv, tmp2);
		}
		//tmp2.push(new HashSet<>());  // Push new Set element onto stack
		tmp2.push(new LinkedList<>());  // Push new Set element onto stack
	}	
	
	public void popRecursionEntry(RecVar rv)
	{
		this.recvars.get(rv).pop();  // Pop the entry of this rec

		//Set<EAction> pop = this.enacting.get(recvar).pop();
		List<EAction> pop = this.enacting.get(rv).pop();
		if (this.enacting.get(rv).isEmpty())  // All Lists popped from the stack of this recvar -- could just clear instead
		{
			this.enacting.remove(rv);
		}
		
		EState curr = getEntry();  
				// Same as getRecursionEntry(rv)
				// Because contract for GraphBuilderUtil is entry on leaving a node is the same as on entry, cf., EGraphBuilder.visitSeq restores original entry on leaving
				// (And visitRecursion just does visitSeq for the recursion body, with the current entry/exit)

		//Map<RecVar, Set<EAction>> tmp = this.enactingMap.get(curr);
		Map<RecVar, List<EAction>> tmp = this.enactingMap.get(curr);
		if (tmp == null)
		{
			tmp = new HashMap<>();
			this.enactingMap.put(curr, tmp);
		}
		tmp.put(rv, pop);
	}	
	

	/*
	 * Edge construction for Continues
	 */

	// Choice-unguarded continues -- fixed in finalise pass
	// Now currently used for all continues, cf. LContinue::buildGraph
	public void addContinueEdge(EState s, RecVar rv)
	{
		/*this.contStates.add(s);
		this.contRecVars.add(rv);*/
		EState entry = getRecursionEntry(rv);
		addEdgeAux(s, new IntermediateContinueEdge(this.ef, rv), entry);
		//addEdge(s, new IntermediateContinueEdge(rv), entry); // **FIXME: broken on purpose for testing
	}

	// Cf. add ContinueEdge
	public void addRecursionEdge(EState s, EAction a, RecVar rv)
	{
		EState entry = getRecursionEntry(rv);
		addEdge(s, a, entry);
	}

	// CHECKME handle preds/prevs like above (cf. this.addEdge)
	public void removeEdge(EState s, EAction a, EState succ)
			throws ScribException
	{
		removeEdgeAux(s, a, succ);
	}

	public EState getRecursionEntry(RecVar recvar)
	{
		return this.recvars.get(recvar).peek();
	}	
	
	// Returns List, cf. getSuccessors/Actions (vs. GetDet...)
	public List<EState> getPredecessors(EState s)
	{
		return this.states.stream().filter(x -> x.getSuccs().contains(s))
				.collect(Collectors.toList());
	}

	/*
	 * Finalise graph by treating IntermediateContinueEdges
	 */
	public EGraph finalise()
	{
		EState entry = this.entry.cloneNode(this.ef, this.entry.getLabels()); //this.ef.newEState(this.entry.getLabels());
		EState exit = this.exit.cloneNode(this.ef, this.exit.getLabels()); //this.ef.newEState(this.exit.getLabels());

		Map<EState, EState> map = new HashMap<>();
		map.put(this.entry, entry);
		map.put(this.exit, exit);
		Set<EState> seen = new HashSet<>();
		fixContinueEdges(seen, map, this.entry, entry);
		if (!seen.contains(this.exit))
		{
			exit = null;
		}
		
		/*Map<Integer, EndpointState> all = getAllStates(res);
		EndpointState dfa = determinise(all, res, resTerm);
		System.out.println("111: " + dfa.toDot());*/
		
		init(null);
		
		return new EGraph(entry, exit);
	}
	
	// FIXME: incomplete -- won't fully correctly handle situations involving, e.g., transitive continue-edge fixing?
	protected void fixContinueEdges(Set<EState> seen, Map<EState, EState> clones,
			EState orig, EState clone)
	{
		if (seen.contains(orig))
		{
			return;
		}
		seen.add(orig);
		Iterator<EAction> as = orig.getActions().iterator();
		Iterator<EState> ss = orig.getSuccs().iterator();
		while (as.hasNext())
		{
			EAction a = as.next();
			EState origSucc = ss.next();
			EState cloneSucc = getClone(clones, origSucc);
			
			if (!(a instanceof IntermediateContinueEdge))
			{
				addEdgeAux(clone, a, cloneSucc);
			
				fixContinueEdges(seen, clones, origSucc, cloneSucc);
			}
			else
			{
				IntermediateContinueEdge ice = (IntermediateContinueEdge) a;  // Choice --imed--> recursion entry (origSucc)
				//for (IOAction e : this.enactingMap.get(succ))
				RecVar rv = new RecVar(ice.mid.toString());
				for (EAction e : this.enactingMap.get(origSucc).get(rv))
				{
					for (EState n : origSucc.getSuccs(e))
					{
						cloneSucc = getClone(clones, n);  // cloneSucc is a successor of origSucc
						addEdgeAux(clone, e, cloneSucc);  // Choice --enacting--> cloneSucc, i.e., succ(rec-entry, enacting)

						fixContinueEdges(seen, clones, origSucc, cloneSucc);
					}
				}
			}
		}
	}

	private EState getClone(Map<EState, EState> clones, EState orig)
	{
		EState res;
		if (clones.containsKey(orig))
		{
			 res = clones.get(orig);
		}
		else
		{
			//next = this.ef.newEState(succ.getLabels());
			res = orig.cloneNode(this.ef, orig.getLabels());
			clones.put(orig, res);
		}
		return res;
	}
}


