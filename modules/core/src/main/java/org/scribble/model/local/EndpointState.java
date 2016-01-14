package org.scribble.model.local;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.scribble.sesstype.name.RecVar;

// http://sandbox.kidstrythisathome.com/erdos/
public class EndpointState
{
	public static enum Kind { OUTPUT, UNARY_INPUT, POLY_INPUT, TERMINAL }
	
	private static int count = 0;
	
	public final int id;

	private final Set<RecVar> labs;  // Was RecVar and SubprotocolSigs, now using inlined protocol for FSM building so just RecVar
	private final LinkedHashMap<IOAction, EndpointState> edges;  // Want predictable ordering of entries for e.g. API generation (state enumeration)
	
	protected EndpointState(Set<RecVar> labs)
	{
		this.id = EndpointState.count++;
		this.labs = new HashSet<>(labs);
		this.edges = new LinkedHashMap<>();
	}
	
	protected void addLabel(RecVar lab)
	{
		this.labs.add(lab);
	}
	
	// Mutable (can also overwrite edges)
	protected void addEdge(IOAction a, EndpointState s)
	{
		this.edges.put(a, s);
	}
	
	public Set<RecVar> getLabels()
	{
		return Collections.unmodifiableSet(this.labs);
	}
	
	public Set<IOAction> getAcceptable()
	{
		return Collections.unmodifiableSet(this.edges.keySet());
	}
	
	public boolean isAcceptable(IOAction a)
	{
		return this.edges.containsKey(a);
	}

	public EndpointState accept(IOAction a)
	{
		return this.edges.get(a);
	}
	
	public Collection<EndpointState> getSuccessors()
	{
		return Collections.unmodifiableCollection(this.edges.values());
	}
	
	public boolean isTerminal()
	{
		return this.edges.isEmpty();
	}

	@Override
	public int hashCode()
	{
		int hash = 73;
		hash = 31 * hash + this.id;
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof EndpointState))
		{
			return false;
		}
		return this.id == ((EndpointState) o).id;
	}
	
	@Override
	public String toString()
	{
		String s = "\"" + this.id + "\":[";
		if (!this.edges.isEmpty())
		{
			Iterator<Entry<IOAction, EndpointState>> es = this.edges.entrySet().iterator();
			Entry<IOAction, EndpointState> first = es.next();
			s += first.getKey() + "=\"" + first.getValue().id + "\"";
			while (es.hasNext())
			{
				Entry<IOAction, EndpointState> e = es.next();
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

	protected final String toDot(Set<EndpointState> seen)
	{
		seen.add(this);
		String s = toNodeDot();
		for (Entry<IOAction, EndpointState> e : this.edges.entrySet())
		{
			IOAction msg = e.getKey();
			EndpointState p = e.getValue();
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
	protected String toEdgeDot(IOAction msg, EndpointState next)
	{
		return toEdgeDot(getDotNodeId(), next.getDotNodeId(), next.getEdgeLabel(msg));
	}
	
	// "this" is the dest node of the edge
	// Override to change edge drawing to "this" as dest
	protected String getEdgeLabel(IOAction msg)
	{
		return "label=\"" + msg + "\"";
	}

	public static EndpointState findTerminalState(Set<EndpointState> visited, EndpointState curr)
	{
		if (!visited.contains(curr))
		{
			if (curr.isTerminal())
			{
				return curr;
			}
			visited.add(curr);
			for (EndpointState succ : curr.getSuccessors())
			{
				EndpointState res = findTerminalState(visited, succ);
				if (res != null)
				{
					return res;
				}
			}
		}
		return null;
	}
	
	public Kind getStateKind()
	{
		Set<IOAction> as = this.getAcceptable();
		return (as.size() == 0)
				? Kind.TERMINAL
				: (as.iterator().next() instanceof Send)
						? Kind.OUTPUT
						: (as.size() > 1) ? Kind.POLY_INPUT : Kind.UNARY_INPUT;
	}
}
