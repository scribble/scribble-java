package org.scribble.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
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
	//private S root;
	
	private final Map<RecVar, Deque<S>> recvars = new HashMap<>();  // Should be a stack of S?
	//private final Map<SubprotocolSig, S> subprotos = new HashMap<>();  // Not scoped sigs
	private final Map<RecVar, Deque<A>> enacting = new HashMap<>();

	private Deque<List<S>> pred = new LinkedList<>();
	private Deque<List<A>> prev = new LinkedList<>();
	
	private S entry;
	private S exit;  // Good for merges (otherwise have to generate dummy merge nodes)
	
	public GraphBuilder()
	{

	}
	
	public void reset()
	{
		this.recvars.clear();
		this.entry = newState(Collections.emptySet());
		//this.root = this.entry;
		this.exit = newState(Collections.emptySet());
		
		this.pred.push(new LinkedList<>());
		this.prev.push(new LinkedList<>());
	}
	
	public abstract S newState(Set<RecVar> labs);
	/*{
		return new S(labs);
	}*/
	
	public void addEntryLabel(RecVar lab)
	{
		this.entry.addLabel(lab);
	}

	/*public void removeLastEdge(S s)
	{
		s.removeLastEdge();
	}*/
	public void removeEdge(S s, A a, S succ) throws ScribbleException
	{
		s.removeEdge(a, succ);
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
		
		for (Deque<A> ens : this.enacting.values())
		{
			if (!ens.isEmpty())
			{
				if (ens.peek() == null)
				{
					ens.pop();
					ens.push(a);
				}
			}
		}
	}
	
	public void enterChoice()  // FIXME: refactor
	{
		this.pred.push(new LinkedList<>());
		this.prev.push(new LinkedList<>());
	}

	public void pushChoiceBlock()
	{
		this.pred.push(null);  // Signifies following statement is "unguarded" in this choice block
		this.prev.push(null);
	}

	public void popChoiceBlock()
	{
		List<S> pred = this.pred.pop();
		List<A> prev = this.prev.pop();
		List<S> peek1 = this.pred.peek();
		if (peek1 == null)
		{
			this.pred.pop();
			peek1 = new LinkedList<>();
			this.pred.push(peek1);
		}
		peek1.addAll(pred);
		List<A> peek2 = this.prev.peek();
		if (peek2 == null)
		{
			this.prev.pop();
			peek2 = new LinkedList<>();
			this.prev.push(peek2);
		}
		peek2.addAll(prev);
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
		
		Deque<A> tmp2 = this.enacting.get(recvar);
		if (tmp2 == null)
		{
			tmp2 = new LinkedList<>();
			this.enacting.put(recvar, tmp2);
		}
		tmp2.push(null);
	}	

	public void popRecursionEntry(RecVar recvar)
	{
		this.recvars.get(recvar).pop();
		this.enacting.get(recvar).pop();
	}	
	
	public List<S> getPredecessors()
	{
		return this.pred.peek();
	}
	
	public List<A> getPreviousActions()
	{
		return this.prev.peek();
	}

	public S getRecursionEntry(RecVar recvar)
	{
		return this.recvars.get(recvar).peek();
	}	
	
	public A getEnacting(RecVar rv)
	{
		return this.enacting.get(rv).peek();
	}
	
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
