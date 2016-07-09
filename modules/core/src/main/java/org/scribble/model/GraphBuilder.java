package org.scribble.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.RecVar;

// Helper class for EndpointGraphBuilder -- can access the protected setters of S
public abstract class GraphBuilder<A extends ModelAction<K>, S extends ModelState<A, S, K>, K extends ProtocolKind>
{
	private final Map<RecVar, Deque<S>> recvars = new HashMap<>();  // Should be a stack of S?
	private final Map<RecVar, Deque<Set<A>>> enacting = new HashMap<>();  // first action(s) inside a rec scope ("enacting" means how to enact an unguarded choice-continue)

	protected final Map<S, Map<RecVar, Set<A>>> enactingMap = new HashMap<>();  // FIXME: refactor: currently here for convenience, but used only by LGraphBuilder  // Record enacting per-recvar, since a state could have multi-recvars

	//private final Map<SubprotocolSig, S> subprotos = new HashMap<>();  // Not scoped sigs

	private final Deque<List<S>> pred = new LinkedList<>();
	private final Deque<List<A>> prev = new LinkedList<>();
	
	//protected S root;
	protected S entry;
	protected S exit;  // Good for merges (otherwise have to generate dummy merge nodes)
	
	public GraphBuilder()
	{
		clear();
	}

	/*public GraphBuilder(S entry, S exit)
	{
		clear();

		this.entry = entry;
		this.exit = exit;
	}*/

	protected void clear()
	{
		this.recvars.clear();
		this.enacting.clear();
		this.pred.clear();
		this.prev.clear();

		this.pred.push(new LinkedList<>());
		this.prev.push(new LinkedList<>());
		
		/*this.contStates.clear();
		this.contRecVars.clear();*/
		this.enactingMap.clear();
	}
	
	// Separated from constructor in order to use newState
	public void reset()
	{
		clear();
		
		this.entry = newState(Collections.emptySet());
		//this.root = this.entry;
		this.exit = newState(Collections.emptySet());
	}
	
	public abstract S newState(Set<RecVar> labs);
	/*{
		return new S(labs);
	}*/
	
	public void addEntryLabel(RecVar lab)
	{
		this.entry.addLabel(lab);
	}
	
	// visibility HACK
	protected void addEdgeAux(S s, A a, S succ)
	{
		s.addEdge(a, succ);
	}
	
	// Records 's' as predecessor state, and 'a' as previous action and the "enacting action" for "fresh" recursion scopes
	public void addEdge(S s, A a, S succ)
	{
		s.addEdge(a, succ);
		//if (!this.pred.isEmpty())
		{
			this.pred.pop();
			this.prev.pop();
		}
		this.pred.push(new LinkedList<>(Arrays.asList(s)));
		this.prev.push(new LinkedList<>(Arrays.asList(a)));
		
		for (Deque<Set<A>> ens : this.enacting.values())
		{
			if (!ens.isEmpty())  // Unnecessary?
			{
				Set<A> tmp = ens.peek();
				if (tmp.isEmpty())
				{
					tmp.add(a);
				}
			}
		}
	}

	// Doesn't set predecessor, cf. addEdge (and cf. addEdgeAux)
	protected void addRecursionEdge(S s, A a, S succ)  // Cf. LGraphBuilder.addContinueEdge, for choice-unguarded cases -- addRecursionEdge, for guarded cases, should also be in LGraphBuilder, but here for convenience (private state)
	{
		s.addEdge(a, succ);
		
		// Still needed here?
		for (Deque<Set<A>> ens : this.enacting.values())
		{
			if (!ens.isEmpty())
			{
				Set<A> tmp = ens.peek();
				if (tmp.isEmpty())
				{
					tmp.add(a);
				}
			}
		}
	}
	
	/*private final List<S> contStates = new LinkedList<>();
	private final List<RecVar> contRecVars = new LinkedList<>();*/

	/*public void removeLastEdge(S s)
	{
		s.removeLastEdge();
	}* /
	public void removeEdge(S s, A a, S succ) throws ScribbleException
	{
		s.removeEdge(a, succ);
	}*/

	// succ assumed to be this.getEntry()
	protected void removeEdgeFromPredecessor(S s, A a) throws ScribbleException  // Removing prev edge, to be replaced by addRecursionEdge  // Should be in LGraphBuilder, but here for convenience
	{
		//s.removeEdge(a, succ);
		s.removeEdge(a, this.getEntry());
		//this.pred.peek().remove(s);  // Need to update both preds and prevs accordingly (consider non-det)
		Iterator<S> preds = this.pred.peek().iterator();
		Iterator<A> prevs = this.prev.peek().iterator();
		while (preds.hasNext())
		{
			S nexts = preds.next();
			A nexta = prevs.next();
			if (nexts.equals(s) && nexta.equals(a))
			{
				preds.remove();
				prevs.remove();
				return;
			}
		}
		throw new RuntimeException("Shouldn't get in here: " + s + ", " + a);
	}
	
	public void enterChoice()  // FIXME: refactor (LChoiceDel.visitForFsmConversion)
	{
		this.pred.push(new LinkedList<>());
		this.prev.push(new LinkedList<>());
		
		for (RecVar rv : this.enacting.keySet())
		{
			Deque<Set<A>> tmp = this.enacting.get(rv);
			//tmp.push(new HashSet<>(tmp.peek()));
			tmp.push(new HashSet<>());  // Initially empty to record nested enablings in choice blocks
		}
	}

	public void leaveChoice()
	{
		List<S> pred = this.pred.pop();
		List<A> prev = this.prev.pop();
		if (!pred.isEmpty())
		{
			this.pred.pop();
			this.prev.pop();
			this.pred.push(pred);
			this.prev.push(prev);
		}
		
		for (RecVar rv : this.enacting.keySet())
		{
			Set<A> pop = this.enacting.get(rv).pop();
			Set<A> peek = this.enacting.get(rv).peek();
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
			Deque<Set<A>> tmp = this.enacting.get(rv);
			tmp.push(new HashSet<>());  // Must be empty for addEdge to record (nested) enabling
		}
	}

	public void popChoiceBlock()
	{
		List<S> pred = this.pred.pop();
		List<A> prev = this.prev.pop();

		if (pred != null)  // Unguarded choice-continue?
		{
			List<S> peek1 = this.pred.peek();
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
			List<A> peek2 = this.prev.peek();
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
			Set<A> pop = this.enacting.get(rv).pop();
			Set<A> peek = this.enacting.get(rv).peek();
			//if (peek.isEmpty())
			{
				peek.addAll(pop);
			}
		}
	}
	
	public boolean isUnguardedInChoice()
	//public boolean isUnguardedInChoice(RecVar rv)
	{
		return
				////!this.entry.equals(this.root) && // Hacky? for protocols that start with unguarded choice-rec, e.g. choice at A { rec X { ... at root
				//!this.pred.isEmpty() &&  // This and above fixed by initialising non-null pred/prev?
				this.pred.peek() == null;
	}
	
	public void pushRecursionEntry(RecVar recvar, S entry)
	{
		/*if (!isUnguardedInChoice())  // Don't record rec entry if it is an unguarded choice-rec
		{
			this.entry.addLabel(recvar);
		}*/
		//this.recvars.put(recvar, this.entry);
		Deque<S> tmp = this.recvars.get(recvar);
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
		
		Deque<Set<A>> tmp2 = this.enacting.get(recvar);
		if (tmp2 == null)
		{
			tmp2 = new LinkedList<>();  // New Stack for this recvar
			this.enacting.put(recvar, tmp2);
		}
		tmp2.push(new HashSet<>());  // Push new Set element onto stack
	}	
	
	public void popRecursionEntry(RecVar recvar)
	{
		this.recvars.get(recvar).pop();  // Pop the entry of this rec

		Set<A> pop = this.enacting.get(recvar).pop();
		if (this.enacting.get(recvar).isEmpty())  // All Sets popped from the stack of this recvar
		{
			this.enacting.remove(recvar);
		}

		S curr = getEntry();
		Map<RecVar, Set<A>> tmp = this.enactingMap.get(curr);
		if (tmp == null)
		{
			tmp = new HashMap<>();
			this.enactingMap.put(curr, tmp);
		}
		tmp.put(recvar, pop);
	}	
	
	public List<S> getPredecessors()
	{
		//return this.pred.peek();
		return new LinkedList<>(this.pred.peek());  // Cf. removeEdgeFromPredecessor
	}
	
	public List<A> getPreviousActions()
	{
		//return this.prev.peek();
		return new LinkedList<>(this.prev.peek());
	}

	public S getRecursionEntry(RecVar recvar)
	{
		return this.recvars.get(recvar).peek();
	}	
	
	/*public Set<A> getEnacting(RecVar rv)
	{
		// FIXME: is stack-ing enactings, need to return by searching from bottom upwards
		return this.enacting.get(rv).peek();
	}*/
	
	/*public S getSubprotocolEntry(SubprotocolSig subsig)
	{
		return this.subprotos.get(subsig);
	}

	public void setSubprotocolEntry(SubprotocolSig subsig)
	{
		this.subprotos.put(subsig, this.entry);
	}	

	public void removeSubprotocolEntry(SubprotocolSig subsig)
	{
		this.subprotos.remove(subsig);
	}*/
	
	public S getEntry()
	{
		return this.entry;
	}

	public void setEntry(S entry)
	{
		this.entry = entry;
	}

	public S getExit()
	{
		return this.exit;
	}

	public void setExit(S exit)
	{
		this.exit = exit;
	}
}
