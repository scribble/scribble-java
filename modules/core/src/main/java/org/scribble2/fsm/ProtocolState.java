package org.scribble2.fsm;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.scribble2.sesstype.name.Op;
import org.scribble2.sesstype.name.RecVar;

public class ProtocolState
{
	private static int count = 0;
	
	public final int id;

	private final Set<RecVar> labs;
	private final Map<Op, ProtocolState> edges;
	
	protected ProtocolState(Set<RecVar> labs)
	{
		this.id = ProtocolState.count++;
		this.labs = new HashSet<>(labs);
		this.edges = new HashMap<>();
	}
	
	public Set<RecVar> getLabels()
	{
		//return new HashSet<>(this.labs);
		return this.labs;
	}
	
	public Map<Op, ProtocolState> getEdges()
	{
		return this.edges;
	}
	
	public boolean isTerminal()
	{
		return this.edges.isEmpty();
	}
	
	public Set<ProtocolState> findTerminals()
	{
		Set<ProtocolState> terms = new HashSet<>();
		findTerminals(new HashSet<>(), this, terms);
		return terms;
	}

	private static void findTerminals(Set<ProtocolState> seen, ProtocolState curr, Set<ProtocolState> terms)
	{
		if (seen.contains(curr))
		{
			return;
		}
		seen.add(curr);
		if (curr.isTerminal())
		{
			terms.add(curr);
		}
		else
		{
			for (Entry<Op, ProtocolState> e : curr.edges.entrySet())
			{
				findTerminals(seen, e.getValue(), terms);
			}
		}
	}
	
	/*public ProtocolState copy()
	{
		ProtocolState copy = new ProtocolState(this.labs);
		copy(new HashSet<>(), this, copy);
		return copy;
	}

	public static void copy(Set<ProtocolState> seen, ProtocolState curr, ProtocolState copy)
	{
		if (seen.contains(curr))
		{
			return;
		}
		seen.add(curr);
		for(Entry<Op, ProtocolState> e : curr.edges.entrySet())
		{
			Op op = e.getKey();
			ProtocolState next = e.getValue();
			ProtocolState tmp = new ProtocolState(next.labs);
			copy.edges.put(op, tmp);
			copy(seen, next, tmp);
		}
	}*/
	
	protected void addEdge(Op op, ProtocolState s)
	{
		this.edges.put(op, s);
	}
	
	public boolean isValid(Op op)
	{
		return this.edges.containsKey(op);
	}

	public ProtocolState accept(Op op)
	{
		return this.edges.get(op);
	}
	
	public Collection<ProtocolState> getSuccessors()
	{
		return this.edges.values();
	}

	@Override
	public int hashCode()
	{
		int hash = 73;
		hash = 31 * hash + this.id;  // Would be enough by itself, but keep consistent with equals
		hash = 31 * hash + this.labs.hashCode();
		hash = 31 * hash + this.edges.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof ProtocolState))
		{
			return false;
		}
		ProtocolState s = (ProtocolState) o;
		return this.id == s.id && this.labs.equals(s.labs) && this.edges.equals(s.edges);
	}
}
