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
// Helper class for EGraphBuilder -- can access the protected setters of EState (via superclass helper methods)
// Tailored to support graph building from syntactic local protocol choice and recursion
public class EGraphBuilderUtil
		extends GraphBuilderUtil<RecVar, EAction, EState, Local>
{
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

	protected EState entry;	// GraphBuilderUtil usage contract: entry on leaving a node is the same as on entering -- cf., EGraphBuilderUtil.visitSeq restores the original entry on leaving
	protected EState exit;   // Tracking exit is convenient for merges (otherwise have to generate dummy merge nodes)

	public EGraphBuilderUtil(ModelFactory mf)
	{
		super(mf);
		reset();
	}
	
	// N.B. must be called before every "new visit", including first -- called by constructor and finalise
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
		EState s = this.mf.newEState(labs);
		this.states.add(s);
		return s;
	}

	// Records 's' as predecessor state, and 'a' as previous action and the "enacting action" for "fresh" recursion scopes
	@Override
	public void addEdge(EState s, EAction a, EState succ)
	{
		super.addEdge(s, a, succ);
		
		//for (Deque<Set<EAction>> ens : this.enacting.values())
		for (Deque<List<EAction>> ens : this.collecting.values())
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
		for (EState entry : this.collecting.keySet())
		{
			////Deque<Set<EAction>> tmp = this.enacting.get(rv);
			Deque<List<EAction>> tmp = this.collecting.get(entry);
			////tmp.push(new HashSet<>(tmp.peek()));
			//tmp.push(new HashSet<>());  // Initially empty to record nested enablings in choice blocks
			tmp.push(new LinkedList<>());  // Initially empty to record nested enablings in choice blocks
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

	public void pushChoiceBlock()
	{
		for (EState entry : this.collecting.keySet())
		{
			//Deque<Set<EAction>> tmp = this.enacting.get(rv);
			Deque<List<EAction>> tmp = this.collecting.get(entry);
			//tmp.push(new HashSet<>());  // Must be empty for addEdge to record (nested) enabling
			tmp.push(new LinkedList<>());
		}
	}

	public void popChoiceBlock()
	{
		for (EState entry : this.collecting.keySet())
		{
			/*Set<EAction> pop = this.enacting.get(rv).pop();
			Set<EAction> peek = this.enacting.get(rv).peek();*/
			List<EAction> pop = this.collecting.get(entry).pop();
			List<EAction> peek = this.collecting.get(entry).peek();
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
		Deque<EState> tmp = this.recEntries.get(rv);  // Should be same as getEntry()
		if (tmp == null)
		{
			tmp = new LinkedList<>();
			this.recEntries.put(rv, tmp);
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
		
		if (this.collecting.containsKey(entry))
		{
			return;
		}
		/////Deque<Set<EAction>> tmp2 = this.enacting.get(recvar);
		//Deque<List<EAction>> tmp2 = this.collecting.get(entry);  
				// Collecting per recvar: unnecessary as all recvars for a given state are equivalent (i.e., have the same enacting) -- so could just do per rec entry state
				// But easier to do per recvar, to avoid distinguishing between recvars in nested contexts
		//if (tmp2 == null)
		//{
			Deque<List<EAction>> tmp2 = new LinkedList<>();  // New Stack for this recvar
			this.collecting.put(entry, tmp2);
		//}
		//tmp2.push(new HashSet<>());  // Push new Set element onto stack
		tmp2.push(new LinkedList<>());  // Push new Set element onto stack
	}	
	
	public void popRecursionEntry(RecVar rv)
	{
		EState entry = this.recEntries.get(rv).pop();  // Pop the entry of this rec
				// Same as getEntry()
				// Because contract for GraphBuilderUtil is entry on leaving a node is the same as on entry, cf., EGraphBuilder.visitSeq restores original entry on leaving
				// (And visitRecursion just does visitSeq for the recursion body, with the current entry/exit)
		if (!getEntry().equals(entry))  // TODO: deprecate, temporary testing
		{
			throw new RuntimeException("Shouldn't get here: ");
		}
		if (this.recEntries.get(rv).isEmpty())
		{
			this.recEntries.remove(rv);
		}
		
		if (!this.collecting.containsKey(entry))
		{
			// This state already done -- i.e., already popped another recvar for this same state, e.g., good.efsm.gcontinue.choiceunguarded.Test05b
			return;
		}

		//Set<EAction> pop = this.enacting.get(recvar).pop();
		List<EAction> pop = this.collecting.get(entry).pop();
		if (this.collecting.get(entry).isEmpty())  // All Lists popped from the stack of this recvar -- could just clear instead
		{
			this.collecting.remove(entry);  
					// N.B. we pushed on entry of *outermost rec*, but above popped on exit of *innermost* rec (due to checking entry in this.collecting)
					// This remove is necessary to prevent above containsKey check also succeeding  when leaving the outer rec, e.g., good.efsm.gcontinue.choiceunguarded.Test05b
					// Also necessary to prevent continued updating of this.collecting after leaving a rec (because this.collecting still has entry), e.g., good.efsm.gchoice.Test10
		}

		/*Map<RecVar, List<EAction>> tmp = this.enacting.get(curr);
		if (tmp == null)
		{
			tmp = new HashMap<>();
			this.enacting.put(curr, tmp);
		}
		tmp.put(rv, pop);*/
		if (!this.enacting.containsKey(entry))  
				// For a given state, the enacting for all associated recvars is the same (the recvars are "equivalent) -- so only need to record per state
		{
			this.enacting.put(entry, pop);
		}
		else if (!this.enacting.get(entry).equals(pop))  // TODO: deprecate, temporary testing
		{
			throw new RuntimeException("Shouldn't get in here: " + entry + " ,, "
					+ this.enacting.get(entry) + " ,, " + rv + " ,, " + pop);
		}
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
		addEdgeAux(s, new IntermediateContinueEdge(this.mf, rv), entry);
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
		return this.recEntries.get(recvar).peek();
	}	
	
	// Returns List, cf. getSuccessors/Actions (vs. GetDet...)
	public List<EState> getPredecessors(EState s)
	{
		// TODO: make more efficient
		return this.states.stream().filter(x -> x.getSuccs().contains(s))
				.collect(Collectors.toList());
	}

	/*
	 * Finalise graph by treating IntermediateContinueEdges
	 */
	public EGraph finalise()
	{
		EState entry = this.entry.cloneNode(this.mf, this.entry.getLabels()); //this.ef.newEState(this.entry.getLabels());
		EState exit = this.exit.cloneNode(this.mf, this.exit.getLabels()); //this.ef.newEState(this.exit.getLabels());

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
		
		reset();
		
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
				//IntermediateContinueEdge ice = (IntermediateContinueEdge) a;  // Choice --imed--> recursion entry (origSucc)
				////for (IOAction e : this.enactingMap.get(succ))
				//RecVar rv = new RecVar(ice.mid.toString());
				//for (EAction e : this.enacting.get(origSucc).get(rv))
				for (EAction e : this.enacting.get(origSucc))
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
			res = orig.cloneNode(this.mf, orig.getLabels());
			clones.put(orig, res);
		}
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


