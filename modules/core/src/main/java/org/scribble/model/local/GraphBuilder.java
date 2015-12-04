package org.scribble.model.local;

import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import org.scribble.sesstype.name.RecVar;

// Helper class for EndpointGraphBuilder -- can access the protected setters of EndpointState
public class GraphBuilder
{
	private final Map<RecVar, Deque<EndpointState>> recvars = new HashMap<>();  // Should be a stack of EndpointState?
	//private final Map<SubprotocolSig, EndpointState> subprotos = new HashMap<>();  // Not scoped sigs
	private final Map<RecVar, Deque<IOAction>> enacting = new HashMap<>();

	private Deque<EndpointState> pred = new LinkedList<>();
	private Deque<IOAction> prev = new LinkedList<>();
	
	private EndpointState entry;
	private EndpointState exit;  // Good for merges
	
	public GraphBuilder()
	{

	}
	
	public void reset()
	{
		this.recvars.clear();
		this.entry = newState(Collections.emptySet());
		this.exit = newState(Collections.emptySet());
	}
	
	public EndpointState newState(Set<RecVar> labs)
	{
		return new EndpointState(labs);
	}
	
	/*public void addEntryLabel(RecVar lab)
	{
		this.entry.addLabel(lab);
	}*/

	public void addEdge(EndpointState s, IOAction a, EndpointState succ)
	{
		s.addEdge(a, succ);
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
		}
	}
	
	public void pushChoice()
	{
		this.pred.push(null);
		this.prev.push(null);
	}

	public void popChoice()
	{
		this.pred.pop();
		this.prev.pop();
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
	
	public void pushRecursionEntry(RecVar recvar)
	{
		if (this.pred.isEmpty() || this.pred.peek() != null)
		{
			this.entry.addLabel(recvar);
		}
		//this.recvars.put(recvar, this.entry);
		Deque<EndpointState> tmp = this.recvars.get(recvar);
		if (tmp == null)
		{
			tmp = new LinkedList<>();
			this.recvars.put(recvar, tmp);
			//tmp.push(this.entry);
		}
		/*else
		{
			tmp.push(this.entry);
			//tmp.push(tmp.peek());  // No: e.g. rec X { 1() from A to B; choice at A { 2() from A to B; } or { continue X; } }
		}*/
		if (!this.pred.isEmpty() && this.pred.peek() == null)
		{
			tmp.push(tmp.peek());  // Works because unguarded recs unfolded (including nested recvar shadowing -- if unguarded choice-rec, it will be unfolded and rec entry recorded for guarded unfolding)
		}
		else
		{
			tmp.push(this.entry);
		}
		
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
	}*/
	
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
	}
}
