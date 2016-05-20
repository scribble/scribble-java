package org.scribble.model.wf;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.scribble.model.global.GModelAction;
import org.scribble.model.local.IOAction;
import org.scribble.sesstype.name.Role;

public class WFState
{
	private static int count = 0;  // FIXME: factor out with ModelAction
	
	public final int id;

	public final WFConfig config;
	protected final LinkedHashMap<GModelAction, WFState> edges;
	
	public WFState(WFConfig config)
	{
		this.id = WFState.count++;
		this.config = config;
		this.edges = new LinkedHashMap<>();
	}
	
	// Mutable (can also overwrite edges)
	public void addEdge(GModelAction a, WFState s)
	{
		this.edges.put(a, s);
	}
	
	//public Set<GModelAction> getAcceptable()
	public Map<Role, Set<IOAction>> getAcceptable()
	{
		//return Collections.unmodifiableSet(this.edges.keySet());
		return this.config.getAcceptable();
	}
	
	/*public boolean isAcceptable(GModelAction a)
	{
		return this.edges.containsKey(a);
	}*/

	//public WFState accept(GModelAction a)
	public WFConfig accept(Role r, IOAction a)
	{
		return this.config.accept(r, a);
	}
	
	public Collection<WFState> getSuccessors()
	{
		return Collections.unmodifiableCollection(this.edges.values());
	}
	
	public boolean isError()
	{
		return isTerminal() && !this.config.isEnd();
	}
	
	public boolean isTerminal()
	{
		return this.edges.isEmpty();
	}

	@Override
	public final int hashCode()
	{
		int hash = 73;
		hash = 31 * hash + this.config.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof WFState))
		{
			return false;
		}
		return this.config.equals(((WFState) o).config);
	}
	
	@Override
	public String toString()
	{
		return this.id + ": " + this.config.toString();
	}
	
	public final String toDot()
	{
		String s = "digraph G {\n" // rankdir=LR;\n
				+ "compound = true;\n";
		s += toDot(new HashSet<>());
		return s + "\n}";
	}

	//protected final String toDot(Set<S> seen)
	protected final String toDot(Set<WFState> seen)
	{
		seen.add(this);
		String s = toNodeDot();
		for (Entry<GModelAction, WFState> e : this.edges.entrySet())
		{
			GModelAction msg = e.getKey();
			WFState p = e.getValue();
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
		String labs = this.config.toString();
		return "label=\"" + labs.substring(1, labs.length() - 1) + "\"";
	}
	
	protected String getDotNodeId()
	{
		return "\"" + this.id + "\"";
	}

	// Override to change edge drawing from "this" as src
	protected String toEdgeDot(GModelAction msg, WFState next)
	{
		return toEdgeDot(getDotNodeId(), next.getDotNodeId(), next.getEdgeLabel(msg));
	}
	
	// "this" is the dest node of the edge
	// Override to change edge drawing to "this" as dest
	protected String getEdgeLabel(GModelAction msg)
	{
		return "label=\"" + msg + "\"";
	}

	public Set<WFState> findTerminalStates()
	{
		Set<WFState> res = new HashSet<WFState>();
		findTerminalStates(new HashSet<>(), this, res);
		return res;
	}

	public static void findTerminalStates(Set<WFState> visited, WFState curr, Set<WFState> term)
	{
		if (!visited.contains(curr))
		{
			if (curr.isTerminal())
			{
				term.add(curr);
			}
			visited.add(curr);
			for (WFState succ : curr.getSuccessors())
			{
				findTerminalStates(visited, succ, term);
			}
		}
	}
}
