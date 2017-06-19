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

import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.scribble.main.ScribbleException;
import org.scribble.model.GraphBuilderUtil;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.model.global.actions.SAction;
import org.scribble.sesstype.Payload;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.Op;
import org.scribble.sesstype.name.RecVar;
import org.scribble.sesstype.name.Role;

// Helper class for EGraphBuilder -- can access the protected setters of EState (via superclass helper methods)
// Tailored to support graph building from syntactic local protocol choice and recursion
public class EGraphBuilderUtil extends GraphBuilderUtil<RecVar, EAction, EState, Local>
{
	private final Map<RecVar, Deque<EState>> recvars = new HashMap<>();
	//private final Map<RecVar, Deque<Set<EAction>>> enacting = new HashMap<>();  // First action(s) inside a rec scope ("enacting" means how to enact an unguarded choice-continue)
	private final Map<RecVar, Deque<List<EAction>>> enacting = new HashMap<>();
		// CHECKME: Set sufficient? or List? (consider non-determinism -- does it matter?)

	//private final Map<EState, Map<RecVar, Set<EAction>>> enactingMap = new HashMap<>();  // Record enacting per-recvar, since a state could have multi-recvars
	private final Map<EState, Map<RecVar, List<EAction>>> enactingMap = new HashMap<>();

	private final Deque<List<EState>> pred = new LinkedList<>();
	private final Deque<List<EAction>> prev = new LinkedList<>();

	public EGraphBuilderUtil()
	{
		clear();
	}

	protected void clear()
	{
		this.recvars.clear();
		this.enacting.clear();

		this.pred.clear();
		this.prev.clear();
		this.pred.push(new LinkedList<>());
		this.prev.push(new LinkedList<>());
		
		this.enactingMap.clear();
	}
	
	@Override
	public void reset()
	{
		clear();
		super.reset();
	}
	
	@Override
	public EState newState(Set<RecVar> labs)
	{
		return new EState(labs);
	}

	// Records 's' as predecessor state, and 'a' as previous action and the "enacting action" for "fresh" recursion scopes
	@Override
	public void addEdge(EState s, EAction a, EState succ)
	{
		addEdgeAux(s, a, succ);

		//if (!this.pred.isEmpty())
		{
			this.pred.pop();
			this.prev.pop();
		}
		this.pred.push(new LinkedList<>(Arrays.asList(s)));
		this.prev.push(new LinkedList<>(Arrays.asList(a)));
		
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
		this.pred.push(new LinkedList<>());
		this.prev.push(new LinkedList<>());
		
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
		List<EState> pred = this.pred.pop();
		List<EAction> prev = this.prev.pop();
		if (!pred.isEmpty())
		{
			this.pred.pop();
			this.prev.pop();
			this.pred.push(pred);
			this.prev.push(prev);
		}
		
		for (RecVar rv : this.enacting.keySet())
		{
			/*Set<EAction> pop = this.enacting.get(rv).pop();
			Set<EAction> peek = this.enacting.get(rv).peek();*/
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
		this.pred.push(null);  // Signifies following statement is "unguarded" in this choice block
		this.prev.push(null);
		
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
		List<EState> pred = this.pred.pop();
		List<EAction> prev = this.prev.pop();

		if (pred != null)  // Unguarded choice-continue?
		{
			List<EState> peek1 = this.pred.peek();
			if (peek1 == null)
			{
				this.pred.pop();
				peek1 = new LinkedList<>();
				this.pred.push(peek1);
			}
			peek1.addAll(pred);
		}

		if (prev != null)
		{
			List<EAction> peek2 = this.prev.peek();
			if (peek2 == null)
			{
				this.prev.pop();
				peek2 = new LinkedList<>();
				this.prev.push(peek2);
			}
			peek2.addAll(prev);
		}

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
	public void pushRecursionEntry(RecVar recvar, EState entry)
	{
		/*if (!isUnguardedInChoice())  // Don't record rec entry if it is an unguarded choice-rec
		{
			this.entry.addLabel(recvar);
		}*/
		//this.recvars.put(recvar, this.entry);
		Deque<EState> tmp = this.recvars.get(recvar);
		if (tmp == null)
		{
			tmp = new LinkedList<>();
			this.recvars.put(recvar, tmp);
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
		Deque<List<EAction>> tmp2 = this.enacting.get(recvar);
		if (tmp2 == null)
		{
			tmp2 = new LinkedList<>();  // New Stack for this recvar
			this.enacting.put(recvar, tmp2);
		}
		//tmp2.push(new HashSet<>());  // Push new Set element onto stack
		tmp2.push(new LinkedList<>());  // Push new Set element onto stack
	}	
	
	public void popRecursionEntry(RecVar recvar)
	{
		this.recvars.get(recvar).pop();  // Pop the entry of this rec

		//Set<EAction> pop = this.enacting.get(recvar).pop();
		List<EAction> pop = this.enacting.get(recvar).pop();
		if (this.enacting.get(recvar).isEmpty())  // All Sets popped from the stack of this recvar
		{
			this.enacting.remove(recvar);
		}

		EState curr = getEntry();
		//Map<RecVar, Set<EAction>> tmp = this.enactingMap.get(curr);
		Map<RecVar, List<EAction>> tmp = this.enactingMap.get(curr);
		if (tmp == null)
		{
			tmp = new HashMap<>();
			this.enactingMap.put(curr, tmp);
		}
		tmp.put(recvar, pop);
	}	
	

	/*
	 * Edge construction for Continues
	 */
	public boolean isUnguardedInChoice()
	//public boolean isUnguardedInChoice(RecVar rv)
	{
		return
				////!this.entry.equals(this.root) && // Hacky? for protocols that start with unguarded choice-rec, e.g. choice at A { rec X { ... at root
				//!this.pred.isEmpty() &&  // This and above fixed by initialising non-null pred/prev?
				this.pred.peek() == null;
	}
	
	// Choice-unguarded continues -- fixed in finalise pass
	public void addContinueEdge(EState s, RecVar rv)
	{
		/*this.contStates.add(s);
		this.contRecVars.add(rv);*/
		EState entry = getRecursionEntry(rv);
		addEdgeAux(s, new IntermediateContinueEdge(rv), entry);
		//addEdge(s, new IntermediateContinueEdge(rv), entry); // **FIXME: broken on purpose for testing
	}

	// Doesn't set predecessor, cf. addEdge (and cf. addEdgeAux)
	// Choice-guarded continues (can be done in one pass)
	public void addRecursionEdge(EState s, EAction a, EState succ)  // Cf. LGraphBuilder.addContinueEdge, for choice-unguarded cases -- addRecursionEdge, for guarded cases, should also be in LGraphBuilder, but here for convenience (private state)
	{
		addEdgeAux(s, a, succ);
		
		// Still needed here?
		//for (Deque<Set<EAction>> ens : this.enacting.values())
		for (Deque<List<EAction>> ens : this.enacting.values())
		{
			if (!ens.isEmpty())
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
	
	// succ assumed to be this.getEntry()
	public void removeEdgeFromPredecessor(EState s, EAction a) throws ScribbleException  // Removing prev edge, to be replaced by addRecursionEdge
	{
		//s.removeEdge(a, this.getEntry());
		removeEdgeAux(s, a, this.getEntry());
		//this.pred.peek().remove(s);  // Need to update both preds and prevs accordingly (consider non-det)
		Iterator<EState> preds = this.pred.peek().iterator();
		Iterator<EAction> prevs = this.prev.peek().iterator();
		while (preds.hasNext())
		{
			EState nexts = preds.next();
			EAction nexta = prevs.next();
			if (nexts.equals(s) && nexta.equals(a))
			{
				preds.remove();
				prevs.remove();
				return;
			}
		}
		throw new RuntimeException("Shouldn't get in here: " + s + ", " + a);
	}
	
	public List<EState> getPredecessors()
	{
		//return this.pred.peek();
		return new LinkedList<>(this.pred.peek());  // Cf. removeEdgeFromPredecessor
	}
	
	public List<EAction> getPreviousActions()
	{
		//return this.prev.peek();
		return new LinkedList<>(this.prev.peek());
	}

	public EState getRecursionEntry(RecVar recvar)
	{
		return this.recvars.get(recvar).peek();
	}	
	

	/*
	 * Finalise graph by treating IntermediateContinueEdges
	 */
	public EGraph finalise()
	{
		EState res = new EState(this.entry.getLabels());
		EState resTerm = new EState(this.exit.getLabels());
		Map<EState, EState> map = new HashMap<>();
		map.put(this.entry, res);
		map.put(this.exit, resTerm);
		Set<EState> seen = new HashSet<>();
		fixContinueEdges(seen, map, this.entry, res);
		if (!seen.contains(this.exit))
		{
			resTerm = null;
		}
		
		/*Map<Integer, EndpointState> all = getAllStates(res);
		EndpointState dfa = determinise(all, res, resTerm);
		System.out.println("111: " + dfa.toDot());*/
		
		return new EGraph(res, resTerm);
	}
	
	// FIXME: incomplete -- won't fully correctly handle situations involving, e.g., transitive continue-edge fixing?
	private void fixContinueEdges(Set<EState> seen, Map<EState, EState> map, EState curr, EState res)
	{
		if (seen.contains(curr))
		{
			return;
		}
		seen.add(curr);
		Iterator<EAction> as = curr.getAllActions().iterator();
		Iterator<EState> ss = curr.getAllSuccessors().iterator();
		while (as.hasNext())
		{
			EAction a = as.next();
			EState succ = ss.next();
			EState next;
			next = getNext(map, succ);
			
			if (!(a instanceof IntermediateContinueEdge))
			{
				addEdgeAux(res, a, next);
			
				fixContinueEdges(seen, map, succ, next);
			}
			else
			{
				IntermediateContinueEdge ice = (IntermediateContinueEdge) a;
				//for (IOAction e : this.enactingMap.get(succ))
				RecVar rv = new RecVar(ice.mid.toString());
				for (EAction e : this.enactingMap.get(succ).get(rv))
				{
					for (EState n : succ.getSuccessors(e))
					{
						next = getNext(map, n);
						addEdgeAux(res, e, next);

						fixContinueEdges(seen, map, succ, next);
					}
				}
			}
		}
	}

	private EState getNext(Map<EState, EState> map, EState succ)
	{
		EState next;
		if (map.containsKey(succ))
		{
			 next = map.get(succ);
		}
		else
		{
			next = new EState(succ.getLabels());
			map.put(succ, next);
		}
		return next;
	}
}


class IntermediateContinueEdge extends EAction
{
	public IntermediateContinueEdge(RecVar rv)
	{
		super(Role.EMPTY_ROLE, new Op(rv.toString()), Payload.EMPTY_PAYLOAD);  // HACK
	}
	
	@Override
	public EAction toDual(Role self)
	{
		throw new RuntimeException("Shouldn't get in here: " + this);
	}

	@Override
	public SAction toGlobal(Role self)
	{
		throw new RuntimeException("Shouldn't get in here: " + this);
	}

	@Override
	protected String getCommSymbol()
	{
		return "#";
	}
	
	@Override
	public int hashCode()
	{
		int hash = 1021;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof IntermediateContinueEdge))
		{
			return false;
		}
		return ((IntermediateContinueEdge) o).canEqual(this) && super.equals(o);
	}

	@Override
	public boolean canEqual(Object o)
	{
		return o instanceof IntermediateContinueEdge;
	}
}
