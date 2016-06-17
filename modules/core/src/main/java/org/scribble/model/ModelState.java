package org.scribble.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.RecVar;

//public class ModelState<K extends ProtocolKind>
public abstract class ModelState<A extends ModelAction<K>, S extends ModelState<A, S, K>, K extends ProtocolKind>
{
	private static int count = 0;  // FIXME: factor out with ModelAction
	
	public final int id;

	protected final Set<RecVar> labs;  // Was RecVar and SubprotocolSigs, now using inlined protocol for FSM building so just RecVar
	//private final Set<String> labs;  // Something better to cover both RecVar and SubprotocolSigs?

	// **: clients should use the pair of getAllAcceptable/getSuccessors for correctness -- getAcceptable/accept don't support non-det
	//protected final LinkedHashMap<A, S> edges;  // Want predictable ordering of entries for e.g. API generation (state enumeration)*/
	protected final List<A> actions;
	protected final List<S> succs;
	
	public ModelState(Set<RecVar> labs)  // Immutable singleton node
	//public GModelState(Set<String> labs)  // Immutable singleton node
	//public GModelState()  // Immutable singleton node
	{
		this.id = ModelState.count++;
		this.labs = new HashSet<>(labs);
		//this.edges = new LinkedHashMap<>();
		this.actions = new LinkedList<>();
		this.succs = new LinkedList<>();
	}
	
	protected abstract S newState(Set<RecVar> labs);
	
	protected void addLabel(RecVar lab)
	{
		this.labs.add(lab);
	}
	
	/*protected void removeLastEdge()
	{
		this.actions.remove(this.actions.size() - 1);
		this.succs.remove(this.succs.size() - 1);
	}*/
	protected void removeEdge(A a, S s) throws ScribbleException
	{
		Iterator<A> ia = this.actions.iterator();
		Iterator<S> is = this.succs.iterator();
		while (ia.hasNext())
		{
			A tmpa = ia.next();
			S tmps = is.next();
			if (tmpa.equals(a) && tmps.equals(s))
			{
				ia.remove();
				is.remove();
				return;
			}
		}
		//throw new RuntimeException("No such transition to remove: " + a + "->" + s);
		throw new ScribbleException("No such transition to remove: " + a + "->" + s);  // Hack? EFSM building on bad-reachability protocols now done before actual reachability check
	}
	
	// Mutable (can also overwrite edges)
	protected void addEdge(A a, S s)
	{
		//this.edges.put(a, s);
		Iterator<A> as = this.actions.iterator();  // Needed?..
		Iterator<S> ss = this.succs.iterator();
		while (as.hasNext())  // Duplicate edges preemptively pruned here, but could leave to later minimisation
		{
			A tmpa = as.next();
			S tmps = ss.next();
			if (tmpa.equals(a) && tmps.equals(s))
			{
				return;
			}
		}  // ..needed?
		this.actions.add(a);
		this.succs.add(s);
	}
	
	public Set<RecVar> getLabels()
	{
		return Collections.unmodifiableSet(this.labs);
	}
	
	// Renamed from "getAcceptable" to distinguish from accept actions
	public Set<A> getTakeable()
	{
		Set<A> as = new HashSet<>(this.actions);
		if (as.size() != this.actions.size())
		{
			throw new RuntimeException("[TODO] Non-deterministic state: " + this.actions + "  (Try -minfsm)");  // This getter checks for determinism -- affects e.g. API generation  
		}
		return as;
	}

	public List<A> getAllTakeable()
	{
		//return Collections.unmodifiableSet(this.edges.keySet());
		return Collections.unmodifiableList(this.actions);
	}
	
	public boolean isTakeable(A a)
	{
		//return this.edges.containsKey(a);
		return this.actions.contains(a);
	}

	public S take(A a)
	{
		Set<A> as = new HashSet<>(this.actions);
		if (as.size() != this.actions.size())
		{
			throw new RuntimeException("FIXME: " + this.actions);
		}
		return takeAll(a).get(0);
	}

	public List<S> takeAll(A a)
	{
		//return this.edges.get(a);
		return IntStream.range(0, this.actions.size())
			.filter((i) -> this.actions.get(i).equals(a))
			.mapToObj((i) -> this.succs.get(i))
			.collect(Collectors.toList());
	}
	
	public List<S> getSuccessors()
	{
		//return Collections.unmodifiableCollection(this.edges.values());
		return Collections.unmodifiableList(this.succs);
	}

	public boolean isTerminal()
	{
		//return this.edges.isEmpty();
		return this.actions.isEmpty();
	}

	@Override
	public final int hashCode()
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
		if (!(o instanceof ModelState))
		{
			return false;
		}
		return this.id == ((ModelState<?, ?, ?>) o).id;  // Good to use id, due to edge mutability
	}
	
	public String toLongString()
	{
		String s = "\"" + this.id + "\":[";
		Iterator<S> ss = this.succs.iterator();
		s += this.actions.stream().map((a) -> a + "=\"" + ss.next().id + "\"").collect(Collectors.joining(", "));
		return s + "]";
	}

	@Override
	public String toString()
	{
		return Integer.toString(this.id);  // FIXME
	}
	
	public final String toDot()
	{
		String s = "digraph G {\n" // rankdir=LR;\n
				+ "compound = true;\n";
		s += toDot(new HashSet<>());
		return s + "\n}";
	}

	//protected final String toDot(Set<S> seen)
	protected final String toDot(Set<ModelState<A, S, K>> seen)
	{
		seen.add(this);
		String dot = toNodeDot();
		//for (Entry<A, S> e : this.edges.entrySet())
		for (int i = 0; i < this.actions.size(); i ++)
		{
			/*A msg = e.getKey();
			S p = e.getValue();*/
			A a = this.actions.get(i);
			S s = this.succs.get(i);
			dot += "\n" + toEdgeDot(a, s);
			if (!seen.contains(s))
			{
				dot += "\n" + s.toDot(seen);
			}
		}
		return dot;
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
		//return "label=\"" + labs.substring(1, labs.length() - 1) + "\"";
		return "label=\"" + this.id + ": " + labs.substring(1, labs.length() - 1) + "\"";  // FIXME
	}
	
	protected String getDotNodeId()
	{
		return "\"" + this.id + "\"";
	}

	// Override to change edge drawing from "this" as src
	protected String toEdgeDot(A msg, S next)
	{
		return toEdgeDot(getDotNodeId(), next.getDotNodeId(), next.getEdgeLabel(msg));
	}
	
	// "this" is the dest node of the edge
	// Override to change edge drawing to "this" as dest
	protected String getEdgeLabel(A msg)
	{
		return "label=\"" + msg + "\"";
	}

	public static <A extends ModelAction<K>, S extends ModelState<A, S, K>, K extends ProtocolKind>
			S getTerminal(S start)
	{
		if (start.isTerminal())
		{
			return start;
		}
		Set<S> terms = ModelState.getAllReachable(start).stream().filter((s) -> s.isTerminal()).collect(Collectors.toSet());
		if (terms.size() > 1)
		{
			throw new RuntimeException("Shouldn't get in here: " + terms);
		}
		return (terms.isEmpty()) ? null : terms.iterator().next();  // FIXME: return empty Set instead of null?
	}
			/*S findTerminalState(Set<S> visited, S curr)
	{
		if (!visited.contains(curr))
		{
			if (curr.isTerminal())
			{
				return curr;
			}
			visited.add(curr);
			for (S succ : curr.getSuccessors())
			{
				S res = findTerminalState(visited, succ);
				if (res != null)
				{
					return res;
				}
			}
		}
		return null;
	}*/

	// Note: doesn't implicitly include start (only if start is explicitly reachable from start, of course)
	/*public static <A extends ModelAction<K>, S extends ModelState<A, S, K>, K extends ProtocolKind>
			Set<S> getAllReachable(S start)*/
	@SuppressWarnings("unchecked")
	public static <A extends ModelAction<K>, S extends ModelState<A, S, K>, K extends ProtocolKind>
			Set<S> getAllReachable(ModelState<A, S, K> start)
	{
		Map<Integer, S> all = new HashMap<>();
		Map<Integer, S> todo = new LinkedHashMap<>();
		todo.put(start.id, (S) start);  // Suppressed: assumes ModelState subclass correctly instantiates S parameter
		while (!todo.isEmpty())
		{
			Iterator<S> i = todo.values().iterator();
			S next = i.next();
			todo.remove(next.id);
			/*if (all.containsKey(next.id))
			{
				continue;
			}
			all.put(next.id, next);*/
			for (S s : next.getSuccessors())
			{
				/*if (!all.containsKey(s.id) && !todo.containsKey(s.id))
				{
					todo.put(s.id, s);
				}*/
				if (!all.containsKey(s.id))
				{	
					all.put(s.id, s);
					//if (!todo.containsKey(s.id))  // Redundant
					{
						todo.put(s.id, s);
					}
				}
			}
		}
		return new HashSet<>(all.values());
	}

	@SuppressWarnings("unchecked")
	public static <A extends ModelAction<K>, S extends ModelState<A, S, K>, K extends ProtocolKind>
			//Set<A> getAllReachableActions(S start)
			Set<A> getAllReachableActions(ModelState<A, S, K> start)
	{
		Set<S> all = new HashSet<>();
		all.add((S) start);  // Suppressed: assumes ModelState subclass correctly instantiates S parameter
		all.addAll(ModelState.getAllReachable(start));
		Set<A> as = new HashSet<>();
		for (S s : all)
		{
			as.addAll(s.getAllTakeable());
		}
		return as;
	}
	
	public String toAut()
	{
		Set<ModelState<A, S, K>> all = new HashSet<>();
		all.add(this);
		all.addAll(getAllReachable(this));
		String aut = "";
		int edges = 0;
		Set<Integer> seen = new HashSet<>();
		for (ModelState<A, S, K> s : all)
		{
			if (seen.contains(s.id))
			{
				continue;
			}
			seen.add(s.id);
			Iterator<A> as = s.getAllTakeable().iterator();
			Iterator<S> ss = s.getSuccessors().iterator();
			for (; as.hasNext(); edges++)
			{
				A a = as.next();
				S succ = ss.next();
				String msg = a.toStringWithMessageIdHack();  // HACK
				aut += "\n(" + s.id + ",\"" + msg + "\"," + succ.id + ")";
			}
		}
		return "des (" + this.id + "," + edges + "," + all.size() + ")" + aut + "\n";
	}
	
	public S clone()
	{
		Set<ModelState<A, S, K>> all = new HashSet<>();
		all.add(this);
		all.addAll(ModelState.getAllReachable(this));
		Map<Integer, S> map = new HashMap<>();  // original s.id -> clones
		for (ModelState<A, S, K> s : all)
		{
			map.put(s.id, newState(s.labs));
		}
		for (ModelState<A, S, K> s : all)
		{
			Iterator<A> as = s.getAllTakeable().iterator();
			Iterator<S> ss = s.getSuccessors().iterator();
			S clone = map.get(s.id);
			while (as.hasNext())
			{
				A a = as.next();
				S succ = ss.next();
				clone.addEdge(a, map.get(succ.id));
			}
		}
		return map.get(this.id);
	}
	
	public boolean canReach(ModelState<A, S, K> s)
	{
		return ModelState.getAllReachable(this).contains(s);
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
