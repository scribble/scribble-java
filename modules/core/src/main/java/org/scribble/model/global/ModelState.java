package org.scribble.model.global;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

// "Global FSM", cf. (local) FSM
// FIXME: factor out with FSM (use global and local kinds? difference is transition action kinds)
public class ModelState
{
	private static int count = 0;
	
	public final int id;

	//private final Set<RecVar> labs;
	//private final Set<String> labs;  // Something better to cover both RecVar and SubprotocolSigs?
	private final Map<ModelAction, ModelState> edges;
	
	//protected ModelState(Set<RecVar> labs)  // Immutable singleton node
	//public ModelState(Set<String> labs)  // Immutable singleton node
	public ModelState()  // Immutable singleton node
	{
		this.id = ModelState.count++;
		//this.labs = new HashSet<>(labs);
		this.edges = new HashMap<>();
	}
	
	/*protected Map<ModelAction, ModelState> getEdges()
	{
		return this.edges;
	}*/
	
	public void addEdge(ModelAction a, ModelState s)
	{
		this.edges.put(a, s);
	}
	
	/*//public Set<RecVar> getLabels()
	public Set<String> getLabels()
	{
		return new HashSet<>(this.labs);
	}*/
	
	public Set<ModelAction> getAcceptable()
	{
		return new HashSet<>(this.edges.keySet());
	}
	
	public boolean isAcceptable(ModelAction a)
	{
		return this.edges.containsKey(a);
	}

	public ModelState accept(ModelAction a)
	{
		return this.edges.get(a);
	}
	
	public Collection<ModelState> getSuccessors()
	{
		return this.edges.values();
	}
	
	public boolean isTerminal()
	{
		return this.edges.isEmpty();
	}
	
	public Set<ModelState> findTerminals()
	{
		Set<ModelState> terms = new HashSet<>();
		findTerminals(new HashSet<>(), this, terms);
		return terms;
	}

	private static void findTerminals(Set<ModelState> seen, ModelState curr, Set<ModelState> terms)
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
			for (Entry<ModelAction, ModelState> e : curr.edges.entrySet())
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
		if (!(o instanceof ModelState))
		{
			return false;
		}
		ModelState s = (ModelState) o;
		return this.id == s.id;// && this.labs.equals(s.labs) && this.edges.equals(s.edges);
	}
	
	@Override
	public String toString()
	{
		String s = "\"" + this.id + "\":[";
		if (!this.edges.isEmpty())
		{
			Iterator<Entry<ModelAction, ModelState>> es = this.edges.entrySet().iterator();
			Entry<ModelAction, ModelState> first = es.next();
			s += first.getKey() + "=\"" + first.getValue().id + "\"";
			while (es.hasNext())
			{
				Entry<ModelAction, ModelState> e = es.next();
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

	protected final String toDot(Set<ModelState> seen)
	{
		seen.add(this);
		String s = toNodeDot();
		for (Entry<ModelAction, ModelState> e : this.edges.entrySet())
		{
			ModelAction msg = e.getKey();
			ModelState p = e.getValue();
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
		/*String labs = this.labs.toString();
		return "label=\"" + labs.substring(1, labs.length() - 1) + "\"";*/
		return "";
	}
	
	protected String getDotNodeId()
	{
		return "\"" + this.id + "\"";
	}

	// Override to change edge drawing from "this" as src
	protected String toEdgeDot(ModelAction msg, ModelState next)
	{
		return toEdgeDot(getDotNodeId(), next.getDotNodeId(), next.getEdgeLabel(msg));
	}
	
	// "this" is the dest node of the edge
	// Override to change edge drawing to "this" as dest
	protected String getEdgeLabel(ModelAction msg)
	{
		return "label=\"" + msg + "\"";
	}
	
	/*public ModelState copy()
	{
		ModelState copy = new ModelState(this.labs);
		copy(new HashSet<>(), this, copy);
		return copy;
	}

	public static void copy(Set<ModelState> seen, ModelState curr, ModelState copy)
	{
		if (seen.contains(curr))
		{
			return;
		}
		seen.add(curr);
		for(Entry<Op, ModelState> e : curr.edges.entrySet())
		{
			Op op = e.getKey();
			ModelState next = e.getValue();
			ModelState tmp = new ModelState(next.labs);
			copy.edges.put(op, tmp);
			copy(seen, next, tmp);
		}
	}*/

}
