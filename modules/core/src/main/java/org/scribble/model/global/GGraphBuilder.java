package org.scribble.model.global;

import java.util.Set;

import org.scribble.model.GraphBuilder;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.RecVar;

// Helper class for EndpointGraphBuilder -- can access the protected setters of EndpointState
@Deprecated
public class GGraphBuilder extends GraphBuilder<GModelAction, GModelState, Global>
{
	/*private EndpointState root;
	
	private final Map<RecVar, Deque<EndpointState>> recvars = new HashMap<>();  // Should be a stack of EndpointState?
	//private final Map<SubprotocolSig, EndpointState> subprotos = new HashMap<>();  // Not scoped sigs
	private final Map<RecVar, Deque<IOAction>> enacting = new HashMap<>();

	private Deque<EndpointState> pred = new LinkedList<>();
	private Deque<IOAction> prev = new LinkedList<>();
	
	private EndpointState entry;
	private EndpointState exit;  // Good for merges (otherwise have to generate dummy merge nodes)*/
	
	public GGraphBuilder()
	{

	}
	
	/*public void reset()
	{
		this.recvars.clear();
		this.entry = newState(Collections.emptySet());
		this.root = this.entry;
		this.exit = newState(Collections.emptySet());
	}*/
	
	public GModelState newState(Set<RecVar> labs)
	{
		return new GModelState(labs);
	}
	
	/*public void addEntryLabel(RecVar lab)
	{
		//this.entry.addLabel(lab);
		throw new RuntimeException("Deprecated: " + this);
	}

	// Records 's' as predecessor state, and 'a' as previous action and the "enacting action" for "fresh" recursion scopes
	public void addEdge(EndpointState s, IOAction a, EndpointState succ)
	{
		/*s.addEdge(a, succ);
		if (!this.pred.isEmpty())
		{
			this.pred.pop();
			this.prev.pop();
		}
		this.pred.push(s);
		this.prev.push(a);
		
		for (Deque<IOAction> ens : this.enacting.values())
		{
			if (!ens.isEmpty())
			{
				if (ens.peek() == null)
				{
					ens.pop();
					ens.push(a);
				}
			}
		}* /
		throw new RuntimeException("Deprecated: " + this);
	}
	
	public void pushChoiceBlock()
	{
		this.pred.push(null);  // Signifies following statement is "unguarded" in this choice block
		this.prev.push(null);
	}

	public void popChoiceBlock()
	{
		this.pred.pop();
		this.prev.pop();
	}
	
	public boolean isUnguardedInChoice()
	//public boolean isUnguardedInChoice(RecVar rv)
	{
		return
				!this.entry.equals(this.root) && // Hacky? for protocols that start with unguarded choice-rec, e.g. choice at A { rec X { ... at root
				!this.pred.isEmpty() && this.pred.peek() == null;
	}
	
	public void pushRecursionEntry(RecVar recvar, EndpointState entry)
	{
		/*if (!isUnguardedInChoice())  // Don't record rec entry if it is an unguarded choice-rec
		{
			this.entry.addLabel(recvar);
		}* /
		//this.recvars.put(recvar, this.entry);
		Deque<EndpointState> tmp = this.recvars.get(recvar);
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
		}* /
		tmp.push(entry);
		
		Deque<IOAction> tmp2 = this.enacting.get(recvar);
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
	
	public EndpointState getPredecessor()
	{
		return this.pred.peek();
	}
	
	public IOAction getPreviousAction()
	{
		return this.prev.peek();
	}

	public EndpointState getRecursionEntry(RecVar recvar)
	{
		return this.recvars.get(recvar).peek();
	}	
	
	public IOAction getEnacting(RecVar rv)
	{
		return this.enacting.get(rv).peek();
	}
	
	/*public EndpointState getSubprotocolEntry(SubprotocolSig subsig)
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
	}* /
	
	public EndpointState getEntry()
	{
		return this.entry;
	}

	public void setEntry(EndpointState entry)
	{
		this.entry = entry;
	}

	public EndpointState getExit()
	{
		return this.exit;
	}

	public void setExit(EndpointState exit)
	{
		this.exit = exit;
	}*/
}
