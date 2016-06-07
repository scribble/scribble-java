package org.scribble.model.global;

import java.util.Set;

import org.scribble.model.ModelState;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.RecVar;

@Deprecated
public class GModelState extends ModelState<GModelAction, GModelState, Global>
{
	/*private static int count = 0;
	
	public final int id;

	private final Set<RecVar> labs;
	//private final Set<String> labs;  // Something better to cover both RecVar and SubprotocolSigs?
	private final LinkedHashMap<GModelAction, GModelState> edges;*/
	
	private GModelState exit;  // Hack for now
	
	public GModelState(Set<RecVar> labs)  // Immutable singleton node
	//public GModelState(Set<String> labs)  // Immutable singleton node
	//public GModelState()  // Immutable singleton node
	{
		/*this.id = GModelState.count++;
		this.labs = new HashSet<>(labs);
		this.edges = new LinkedHashMap<>();*/
		super(labs);
	}
	
	public boolean isChoice()
	{
		return this.exit != null;
	}

	public GModelState getChoiceExit()
	{
		return this.exit;
	}

	public void setChoiceExit(GModelState exit)  // Hack for now
	{
		this.exit = exit;
	}

	@Override
	protected GModelState newState(Set<RecVar> labs)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	/*protected void addLabel(RecVar lab)
	{
		this.labs.add(lab);
	}
	
	// Mutable (can also overwrite edges)
	protected void addEdge(GModelAction a, GModelState s)
	{
		this.edges.put(a, s);
	}
	
	public Set<RecVar> getLabels()
	{
		return Collections.unmodifiableSet(this.labs);
	}
	
	public Set<GModelAction> getAcceptable()
	{
		return Collections.unmodifiableSet(this.edges.keySet());
	}
	
	public boolean isAcceptable(GModelAction a)
	{
		return this.edges.containsKey(a);
	}

	public GModelState accept(GModelAction a)
	{
		return this.edges.get(a);
	}
	
	public Collection<GModelState> getSuccessors()
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
		if (!(o instanceof GModelState))
		{
			return false;
		}
		return this.id == ((GModelState) o).id;
	}
	
	@Override
	public String toString()
	{
		String s = "\"" + this.id + "\":[";
		if (!this.edges.isEmpty())
		{
			Iterator<Entry<GModelAction, GModelState>> es = this.edges.entrySet().iterator();
			Entry<GModelAction, GModelState> first = es.next();
			s += first.getKey() + "=\"" + first.getValue().id + "\"";
			while (es.hasNext())
			{
				Entry<GModelAction, GModelState> e = es.next();
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

	protected final String toDot(Set<GModelState> seen)
	{
		seen.add(this);
		String s = toNodeDot();
		for (Entry<GModelAction, GModelState> e : this.edges.entrySet())
		{
			GModelAction msg = e.getKey();
			GModelState p = e.getValue();
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
	protected String toEdgeDot(GModelAction msg, GModelState next)
	{
		return toEdgeDot(getDotNodeId(), next.getDotNodeId(), next.getEdgeLabel(msg));
	}
	
	// "this" is the dest node of the edge
	// Override to change edge drawing to "this" as dest
	protected String getEdgeLabel(GModelAction msg)
	{
		return "label=\"" + msg + "\"";
	}

	public static GModelState findTerminalState(Set<GModelState> visited, GModelState curr)
	{
		if (!visited.contains(curr))
		{
			if (curr.isTerminal())
			{
				return curr;
			}
			visited.add(curr);
			for (GModelState succ : curr.getSuccessors())
			{
				GModelState res = findTerminalState(visited, succ);
				if (res != null)
				{
					return res;
				}
			}
		}
		return null;
	}
	
	
	/*protected Map<ModelAction, ModelState> getEdges()
	{
		return this.edges;
	}* /

	protected void addLabel(RecVar lab)
	{
		this.labs.add(lab);
	}
	
	public void addEdge(GModelAction a, GModelState s)
	{
		this.edges.put(a, s);
	}
	
	public Set<RecVar> getLabels()
	//public Set<String> getLabels()
	{
		return new HashSet<>(this.labs);
	}
	
	public Set<GModelAction> getAcceptable()
	{
		return new HashSet<>(this.edges.keySet());
	}
	
	public boolean isAcceptable(GModelAction a)
	{
		return this.edges.containsKey(a);
	}

	public GModelState accept(GModelAction a)
	{
		return this.edges.get(a);
	}
	
	public Collection<GModelState> getSuccessors()
	{
		return this.edges.values();
	}
	
	public boolean isTerminal()
	{
		return this.edges.isEmpty();
	}
	
	public Set<GModelState> findTerminals()
	{
		Set<GModelState> terms = new HashSet<>();
		findTerminals(new HashSet<>(), this, terms);
		return terms;
	}

	private static void findTerminals(Set<GModelState> seen, GModelState curr, Set<GModelState> terms)
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
			for (Entry<GModelAction, GModelState> e : curr.edges.entrySet())
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
		hash = 31 * hash + this.edges.hashCode();* /
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof GModelState))
		{
			return false;
		}
		GModelState s = (GModelState) o;
		return this.id == s.id;// && this.labs.equals(s.labs) && this.edges.equals(s.edges);
	}
	
	@Override
	public String toString()
	{
		String s = "\"" + this.id + "\":[";
		if (!this.edges.isEmpty())
		{
			Iterator<Entry<GModelAction, GModelState>> es = this.edges.entrySet().iterator();
			Entry<GModelAction, GModelState> first = es.next();
			s += first.getKey() + "=\"" + first.getValue().id + "\"";
			while (es.hasNext())
			{
				Entry<GModelAction, GModelState> e = es.next();
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

	protected final String toDot(Set<GModelState> seen)
	{
		seen.add(this);
		String s = toNodeDot();
		for (Entry<GModelAction, GModelState> e : this.edges.entrySet())
		{
			GModelAction msg = e.getKey();
			GModelState p = e.getValue();
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
		return "label=\"" + labs.substring(1, labs.length() - 1) + "\"";* /
		return "";
	}
	
	protected String getDotNodeId()
	{
		return "\"" + this.id + "\"";
	}

	// Override to change edge drawing from "this" as src
	protected String toEdgeDot(GModelAction msg, GModelState next)
	{
		return toEdgeDot(getDotNodeId(), next.getDotNodeId(), next.getEdgeLabel(msg));
	}
	
	// "this" is the dest node of the edge
	// Override to change edge drawing to "this" as dest
	protected String getEdgeLabel(GModelAction msg)
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
