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

// EGraphBuilderUtil usage contract (1): all states must be made via newState
// EGraphBuilderUtil usage contract (2): this.entry on leaving a node is the same as on entering
// Tailored to support graph building from syntactic local protocol choice and recursion
public class EGraphBuilderUtil
		extends GraphBuilderUtil<RecVar, EAction, EState, Local>
{
	protected EState entry;	// GraphBuilderUtil usage contract: entry on leaving a node is the same as on entering -- cf., EGraphBuilderUtil.visitSeq restores the original entry on leaving
	protected EState exit;  // Tracking exit is convenient for merges (otherwise have to generate dummy merge nodes)

  // N.B. new states should be made by this.newState, not this.mf.newEState
	private final Set<EState> states = new HashSet<>();

	// Entry state for each recursion
	private final Map<RecVar, Deque<EState>> recEntries = new HashMap<>();  // CHECKME: Deque is for shadowing?

	// Record enacting per-recvar, since a state could have multi-recvars -- CHECKME: caters for shadowing? (cf. recording only per unique recvar)
	// CHECKME: for a given state, the enacting list is the same for each recvar?
	private final Map<EState, List<EAction>> enacting = new HashMap<>();

	// Temporary storage of enacting while collecting in progress
	// First action(s) inside a rec scope ("enacting" means how to enact an unguarded choice-continue)
	// These are collected by:
	// - On each recursion entry, push a new list for its entry state -- list pushing/popping mainly for handling choices (see below)
	// - (N.B. only need to do per state, not per rec(var) -- all recvars of a given state equivalently identify that state)
	// - General idea: via addEdge, record the first action encountered for all "*open*" rec states, i.e., add the action to all current lists that are *still empty*
	// - For choice: 
	// -- On choice entry, first push a new list for *all* rec states for recording the choice cases (all, including "non-open", because inconvenient to distinguish open/non-open from nested contexts)
	// -- On block entry, push another new list for *all* rec states for recording the current block
	// -- (Record, via addEdge, the first action of the current block for all open rec states)
	// -- On block exit, pop the block list into the parent choice list for *all* rec states
	// - On choice exit, pop the choice list into the parent (recursion/choice-block) list for all *open* rec states, i.e., if the parent list is *still empty*
	// - On recursion exit, pop the list into the enacting map for the rec entry state
	private final Map<EState, Deque<List<EAction>>> collecting = new HashMap<>();

	protected EGraphBuilderUtil(ModelFactory mf)
	{
		super(mf);
		reset();
	}
	
	// N.B. called before every "new visit", including first (so util is reusable) -- called by constructor and finalise
	@Override
	protected void reset()  
	{
		//clear();
		this.entry = newState(Collections.emptySet());
		this.exit = newState(Collections.emptySet());
	}

	/*protected void clear()  // CHECKME: redundant? -- ideally builder should be implemented so that it is redundant?
	{
		this.states.clear();
		this.recEntries.clear();
		this.enacting.clear();
		this.collecting.clear();
	}*/
	
	// For the util to additionally record states -- use this, don't use this.mf.newEState
	public EState newState(Set<RecVar> labs)
	{
		EState s = this.mf.local.EState(labs);
		this.states.add(s);
		return s;
	}

	// Records 's' as predecessor state, and 'a' as previous action and the "enacting action" for "fresh" recursion scopes
	@Override
	public void addEdge(EState s, EAction a, EState succ)
	{
		super.addEdge(s, a, succ);
		for (Deque<List<EAction>> ens : this.collecting.values())
		{
			List<EAction> tmp = ens.peek();  // enterRecursion always adds an initial List to the Deque 
			if (tmp.isEmpty())
			{
				tmp.add(a);
			}
		}
	}

	/*
	 * Dealing with Choice contexts
	 */
	public void enterChoice()
	{
		for (EState entry : this.collecting.keySet())
		{
			Deque<List<EAction>> deq = this.collecting.get(entry);
			deq.push(new LinkedList<>());  // Initially empty to record nested enablings in choice blocks
		}
	}

	public void leaveChoice()
	{
		for (EState entry : this.collecting.keySet())
		{
			List<EAction> pop = this.collecting.get(entry).pop();
			List<EAction> peek = this.collecting.get(entry).peek();
			if (peek.isEmpty())  // Cf. addEdge
			{
				peek.addAll(pop);
			}
		}
	}

	public void enterChoiceBlock()
	{
		for (EState entry : this.collecting.keySet())
		{
			Deque<List<EAction>> deq = this.collecting.get(entry);
			deq.push(new LinkedList<>());
		}
	}

	public void leaveChoiceBlock()
	{
		for (EState entry : this.collecting.keySet())
		{
			List<EAction> block = this.collecting.get(entry).pop();
			List<EAction> blocks = this.collecting.get(entry).peek();  // Parent choice List
			blocks.addAll(block);
		}
	}
	
	/*
	 * Dealing with Recursion contexts
	 */
	public void enterRecursion(RecVar rv, EState entry)
	{
		// Update this.recEntries
		Deque<EState> recdeq = this.recEntries.get(rv);
		if (recdeq == null)
		{
			recdeq = new LinkedList<>();
			this.recEntries.put(rv, recdeq);
		}
		recdeq.push(entry);  // Same as this.entry
		
		// Udpate this.collecting, if necessary
		if (this.collecting.containsKey(entry) || this.enacting.containsKey(entry))  
				// Already doing/done this rec state for another of its recvar
				// Does checking this.enacting do anything?
		{
			return;
		}
		Deque<List<EAction>> coldeq = new LinkedList<>();  // New Deque for this recvar
		this.collecting.put(entry, coldeq);  // !this.collecting.containsKey(entry), leaveRecursion does remove -- necessary for addEdge/Choice to correctly add to this.collecting
		coldeq.push(new LinkedList<>());  // Push new List onto deq
	}	
	
	public void leaveRecursion(RecVar rv)
	{
		// Update this.recEntries
		EState entry = this.recEntries.get(rv).pop();  // Pop the entry of this rec
				// Same as this.entry
				// Because contract for GraphBuilderUtil is entry on leaving a node is the same as on entry, cf., EGraphBuilder.visitSeq restores original entry on leaving
				// (And visitRecursion just does visitSeq for the recursion body, with the current entry/exit)
		if (this.recEntries.get(rv).isEmpty())
		{
			this.recEntries.remove(rv);  // To keep util clean, e.g., for reuse
		}
		
		// Udpate this.collecting, if necessary
		if (!this.collecting.containsKey(entry))
		{
			// This state already done -- i.e., already popped another recvar for this same state, e.g., good.efsm.gcontinue.choiceunguarded.Test05b
			// (For a given state, the enacting for all associated recvars is the same (the recvars are "equivalently" identify the state) -- so only need to record per state)
			return;
		}
		List<EAction> coll = this.collecting.get(entry).pop();
		if (this.collecting.get(entry).isEmpty())  // All Lists popped from the stack of this recvar -- could just clear instead
		{
			this.collecting.remove(entry);  
					// N.B. we pushed on entry of *outermost rec*, but above popped on exit of *innermost* rec (due to checking entry in this.collecting)
					// This remove is necessary to prevent above containsKey check also succeeding  when leaving the outer rec, e.g., good.efsm.gcontinue.choiceunguarded.Test05b
					// Also necessary to prevent continued updating of this.collecting after leaving a rec (because this.collecting still has entry), e.g., good.efsm.gchoice.Test10
		}
		this.enacting.put(entry, coll);
				// !this.enacting.containsKey(entry) -- if it did contain (remove enacting check from enterRecursion), this.enacting.get(entry).equals(coll)
	}	
	
	/*
	 * Edge construction for Continues
	 */
	public void fixContinueEdge(EState pred, EAction a, EState curr, RecVar rv)
	{
		removeEdgeAux(pred, a, curr);  // N.B. "a" could be an enacting, that's fine (removeEdgeAux doesn't change that)
		EState entry = getRecursionEntry(rv);
		addEdge(pred, a, entry);
	}

	// Choice-unguarded continues -- fixed in finalise pass
	public void addUnguardedContinueEdge(EState s, RecVar rv)  // Intermed continue edge makes its own dummy action
	{
		EState entry = getRecursionEntry(rv);
		addEdgeAux(s, new UnguardedContinueEdge(this.mf, rv), entry);
	}

	/*// CHECKME: update enacting? (cf. this.addEdge)
	public void removeEdge(EState s, EAction a, EState succ)
			throws ScribException
	{
		removeEdgeAux(s, a, succ);
	}*/

	public EState getRecursionEntry(RecVar recvar)
	{
		return this.recEntries.get(recvar).peek();
	}	
	
	// Returns List (not Set), cf. getSuccs/Actions
	public List<EState> getPreds(EState s)
	{
		// Easier to recompute than "cache" (this.preds), due to removeEdge(Aux)? Cf. addEdge (Or due to maintaining this.collecting/enacting)
		// (Only currently used by visitContinue, so cost OK)
		return this.states.stream().filter(x -> x.getSuccs().contains(s))
				.collect(Collectors.toList());
	}

	/*
	 * Finalise graph by treating IntermediateContinueEdges.
	 * Calls reset() on completion.
	 */
	public EGraph finalise()
	{
		EState entryClone = this.entry.cloneNode(this.mf, this.entry.getLabels());
		EState exitClone = this.exit.cloneNode(this.mf, this.exit.getLabels());
		Map<EState, EState> clones = new HashMap<>();  // Will be mostly populated by fixUnguardedContinueEdges
		clones.put(this.entry, entryClone);
		clones.put(this.exit, exitClone);
		Set<EState> seenOrig = new HashSet<>();
		fixUnguardedContinueEdges(seenOrig, clones, this.entry, entryClone);
		if (!seenOrig.contains(this.exit))
		{
			exitClone = null;
		}
		reset();
		return new EGraph(entryClone, exitClone);
	}
	
	protected void fixUnguardedContinueEdges(Set<EState> seenOrig,
			Map<EState, EState> clones, EState currOrig, EState currClone)
	{
		if (seenOrig.contains(currOrig))
		{
			return;
		}
		seenOrig.add(currOrig);
		Iterator<EAction> as = currOrig.getActions().iterator();  // Actions are immutable (no orig/clone)
		Iterator<EState> succsOrig = currOrig.getSuccs().iterator();
		while (as.hasNext())
		{
			EAction a = as.next();
			EState succOrig = succsOrig.next();
			if (!(a instanceof UnguardedContinueEdge))
			{
				EState succClone = getClone(clones, succOrig);
				addEdgeAux(currClone, a, succClone);
				fixUnguardedContinueEdges(seenOrig, clones, succOrig, succClone);
			}
			else  // Choice--unguardedcontedge-->succOrig, i.e., curr is a Choice, succOrig is a rec entry state
			{
				EState recEntry = succOrig;
				for (EAction e : this.enacting.get(recEntry)) // recEntry--enacting-->succEntry
				{
					for (EState succEntry : recEntry.getSuccs(e))
					{
						EState succEntryClone = getClone(clones, succEntry);  // succEntryClone is a (cloned) successor of recEntry
						addEdgeAux(currClone, e, succEntryClone);  
								// Choice--enacting-->succSuccClone, where succSuccClone is succ(rec-entry, enacting) (rec-entry is SuccOrig)
								// i.e., skip recEntry
						fixUnguardedContinueEdges(seenOrig, clones, recEntry,  // Carry on from recEntry, that's where this edge was going "morally"
								getClone(clones, recEntry));
						
						// CHECKME: "transitive" (nested?) unguarded-continue-edge rewriting possible?  supported?
						// ...CHECKME: incomplete -- won't fully correctly handle situations involving, e.g., transitive continue-edge fixing?
						
					}
				}
			}
		}
	}

	private EState getClone(Map<EState, EState> clones, EState orig)
	{
		if (clones.containsKey(orig))
		{
			 return clones.get(orig);
		}
		EState res = orig.cloneNode(this.mf, orig.getLabels());
		clones.put(orig, res);
		return res;
	}
	
	public void addEntryLabel(RecVar lab)
	{
		addEntryLabAux(this.entry, lab);
	}

	public EState getEntry()
	{
		return this.entry;
	}

	public void setEntry(EState entry)
	{
		this.entry = entry;
	}

	public EState getExit()
	{
		return this.exit;
	}

	public void setExit(EState exit)
	{
		this.exit = exit;
	}
}


