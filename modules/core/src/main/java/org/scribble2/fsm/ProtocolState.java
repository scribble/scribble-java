package org.scribble2.fsm;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.scribble2.sesstype.name.RecVar;

public class ProtocolState
{
	private static int count = 0;
	
	public final int id;

	private final Set<RecVar> labs;
	private final Map<IOAction, ProtocolState> edges;
	
	public ProtocolState(Set<RecVar> labs)  // Immutable singleton node
	{
		this.id = ProtocolState.count++;
		this.labs = new HashSet<>(labs);
		this.edges = new HashMap<>();
	}
	
	/*protected Map<IOAction, ProtocolState> getEdges()
	{
		return this.edges;
	}*/
	
	protected void addEdge(IOAction op, ProtocolState s)
	{
		this.edges.put(op, s);
	}
	
	public Set<RecVar> getLabels()
	{
		return new HashSet<>(this.labs);
	}
	
	public Set<IOAction> getAcceptable()
	{
		return new HashSet<>(this.edges.keySet());
	}
	
	public boolean isAcceptable(IOAction op)
	{
		return this.edges.containsKey(op);
	}

	public ProtocolState accept(IOAction op)
	{
		return this.edges.get(op);
	}
	
	public Collection<ProtocolState> getSuccessors()
	{
		return this.edges.values();
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
			for (Entry<IOAction, ProtocolState> e : curr.edges.entrySet())
			{
				findTerminals(seen, e.getValue(), terms);
			}
		}
	}

	@Override
	public int hashCode()
	{
		int hash = 73;
		hash = 31 * hash + this.id;  // Would be enough by itself, but keep consistent with equals
		/*hash = 31 * hash + this.labs.hashCode();
		hash = 31 * hash + this.edges.hashCode();*/
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
		return this.id == s.id;// && this.labs.equals(s.labs) && this.edges.equals(s.edges);
	}
	
	@Override
	public String toString()
	{
		String s = "\"" + this.id + "\":[";
		if (!this.edges.isEmpty())
		{
			Iterator<Entry<IOAction, ProtocolState>> es = this.edges.entrySet().iterator();
			Entry<IOAction, ProtocolState> first = es.next();
			s += first.getKey() + "=\"" + first.getValue().id + "\"";
			while (es.hasNext())
			{
				Entry<IOAction, ProtocolState> e = es.next();
				s += ", " + e.getKey() + "=\"" + e.getValue().id + "\"";
			}
		}
		return s + "]";
	}
	
	public final String toDot()
	{
		String s = "digraph G {\n" // rankdir=LR;\n
				+ "compound = true;\n";
		s += toDot(new HashSet<>());
		return s + "\n}";
	}

	protected final String toDot(Set<ProtocolState> seen)
	{
		seen.add(this);
		String s = toNodeDot();
		for (Entry<IOAction, ProtocolState> e : this.edges.entrySet())
		{
			IOAction msg = e.getKey();
			ProtocolState p = e.getValue();
			s += "\n" + toEdgeDot(msg, p);
			if (!seen.contains(p))
			{
				s += "\n" + p.toDot(seen);
			}
		}
		return s;
	}

	protected final String toEdgeDot(String src, String dest, String lab)
	{
		return src + " -> " + dest + " [ " + lab + " ];";
	}

	// dot node declaration
	// Override to change drawing declaration of "this" node
	protected String toNodeDot()
	{
		return getDotNodeId() + " [ " + getNodeLabel() + " ];";
	}
	
	protected String getNodeLabel()
	{
		String labs = this.labs.toString();
		return "label=\"" + labs.substring(1, labs.length() - 1) + "\"";
	}
	
	protected String getDotNodeId()
	{
		return "\"" + this.id + "\"";
	}

	// Override to change edge drawing from "this" as src
	protected String toEdgeDot(IOAction msg, ProtocolState next)
	{
		return toEdgeDot(getDotNodeId(), next.getDotNodeId(), next.getEdgeLabel(msg));
	}
	
	// "this" is the dest node of the edge
	// Override to change edge drawing to "this" as dest
	protected String getEdgeLabel(IOAction msg)
	{
		return "label=\"" + msg + "\"";
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
}
