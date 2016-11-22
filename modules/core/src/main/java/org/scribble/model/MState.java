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

import org.scribble.main.RuntimeScribbleException;
import org.scribble.main.ScribbleException;
import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.RecVar;

public abstract class MState<A extends MAction<K>, S extends MState<A, S, K>, K extends ProtocolKind>
{
	private static int count = 0;  // FIXME: factor out with ModelAction
	
	public final int id;

	protected final Set<RecVar> labs;  // Was RecVar and SubprotocolSigs, now using inlined protocol for FSM building so just RecVar
	//private final Set<String> labs;  // Something better to cover both RecVar and SubprotocolSigs?

	// **: clients should use the pair of getAllAcceptable/getSuccessors for correctness -- getAcceptable/accept don't support non-det
	//protected final LinkedHashMap<A, S> edges;  // Want predictable ordering of entries for e.g. API generation (state enumeration)*/
	protected final List<A> actions;
	protected final List<S> succs;
	
	// FIXME: refactor RecVar into EState
	public MState(Set<RecVar> labs)  // Immutable singleton node
	//public GModelState(Set<String> labs)  // Immutable singleton node
	//public GModelState()  // Immutable singleton node
	{
		this.id = MState.count++;
		this.labs = new HashSet<>(labs);
		//this.edges = new LinkedHashMap<>();
		this.actions = new LinkedList<>();
		this.succs = new LinkedList<>();
	}
	
	protected void addLabel(RecVar lab)
	{
		this.labs.add(lab);
	}
	
	protected final void removeEdge(A a, S s) throws ScribbleException
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
	//protected final void addEdge(A a, S s)
	public final void addEdge(A a, S s)  // FIXME: currently public for GMChecker building -- make a global version of EGraphBuilderUtil
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
	
	public Set<RecVar> getRecLabels()
	{
		return Collections.unmodifiableSet(this.labs);
	}
	
	// Renamed from "getAcceptable" to distinguish from accept actions
	// The "deterministic" variant, c.f., getAllTakeable
	//public Set<A> getActions()
	public final List<A> getActions()
	{
		Set<A> as = new HashSet<>(this.actions);
		if (as.size() != this.actions.size())
		{
			throw new RuntimeScribbleException("[TODO] Non-deterministic state: " + this.actions + "  (Try -minlts if available)");  // This getter checks for determinism -- affects e.g. API generation  
		}
		//return as;
		return getAllActions();
	}

	public final List<A> getAllActions()
	{
		//return Collections.unmodifiableSet(this.edges.keySet());
		return Collections.unmodifiableList(this.actions);
	}
	
	public final boolean hasAction(A a)
	{
		//return this.edges.containsKey(a);
		return this.actions.contains(a);
	}

	public final S getSuccessor(A a)
	{
		Set<A> as = new HashSet<>(this.actions);
		if (as.size() != this.actions.size())
		{
			throw new RuntimeException("FIXME: " + this.actions);
		}
		return getSuccessors(a).get(0);
	}

	// For non-deterministic actions
	public final List<S> getSuccessors(A a)
	{
		//return this.edges.get(a);
		return IntStream.range(0, this.actions.size())
			.filter((i) -> this.actions.get(i).equals(a))
			.mapToObj((i) -> this.succs.get(i))
			.collect(Collectors.toList());
	}
	
	public final List<S> getSuccessors()
	{
		Set<A> as = new HashSet<>(this.actions);
		if (as.size() != this.actions.size())
		{
			throw new RuntimeScribbleException("[TODO] Non-deterministic state: " + this.actions + "  (Try -minlts if available)");  // This getter checks for determinism -- affects e.g. API generation  
		}
		return getAllSuccessors();
	}

	public final List<S> getAllSuccessors()
	{
		//return Collections.unmodifiableCollection(this.edges.values());
		return Collections.unmodifiableList(this.succs);
	}

	public final boolean isTerminal()
	{
		//return this.edges.isEmpty();
		return this.actions.isEmpty();
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
		if (!(o instanceof MState))
		{
			return false;
		}
		return this.id == ((MState<?, ?, ?>) o).id;  // Good to use id, due to edge mutability
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
		return Integer.toString(this.id);  // FIXME -- ?
	}
	
	public final String toDot()
	{
		String s = "digraph G {\n" // rankdir=LR;\n
				+ "compound = true;\n";
		s += toDot(new HashSet<>());
		return s + "\n}";
	}

	//protected final String toDot(Set<S> seen)
	protected final String toDot(Set<MState<A, S, K>> seen)
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

	public static <A extends MAction<K>, S extends MState<A, S, K>, K extends ProtocolKind>
			S getTerminal(S start)
	{
		if (start.isTerminal())
		{
			return start;
		}
		Set<S> terms = MState.getAllReachableStates(start).stream().filter((s) -> s.isTerminal()).collect(Collectors.toSet());
		if (terms.size() > 1)
		{
			throw new RuntimeException("Shouldn't get in here: " + terms);
		}
		return (terms.isEmpty()) ? null : terms.iterator().next();  // FIXME: return empty Set instead of null?
	}

	// Note: doesn't implicitly include start (only if start is explicitly reachable from start, of course)
	/*public static <A extends ModelAction<K>, S extends ModelState<A, S, K>, K extends ProtocolKind>
			Set<S> getAllReachable(S start)*/
	@SuppressWarnings("unchecked")
	public static <A extends MAction<K>, S extends MState<A, S, K>, K extends ProtocolKind>
			Set<S> getAllReachableStates(MState<A, S, K> start)
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
			for (S s : next.getAllSuccessors())
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
	public static <A extends MAction<K>, S extends MState<A, S, K>, K extends ProtocolKind>
			//Set<A> getAllReachableActions(S start)
			Set<A> getAllReachableActions(MState<A, S, K> start)
	{
		Set<S> all = new HashSet<>();
		all.add((S) start);  // Suppressed: assumes ModelState subclass correctly instantiates S parameter
		all.addAll(MState.getAllReachableStates(start));
		Set<A> as = new HashSet<>();
		for (S s : all)
		{
			as.addAll(s.getAllActions());
		}
		return as;
	}
	
	public final String toAut()
	{
		Set<MState<A, S, K>> all = new HashSet<>();
		all.add(this);
		all.addAll(getAllReachableStates(this));
		String aut = "";
		int edges = 0;
		Set<Integer> seen = new HashSet<>();
		for (MState<A, S, K> s : all)
		{
			if (seen.contains(s.id))
			{
				continue;
			}
			seen.add(s.id);
			Iterator<A> as = s.getAllActions().iterator();
			Iterator<S> ss = s.getAllSuccessors().iterator();
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
	
	public boolean canReach(MState<A, S, K> s)
	{
		return MState.getAllReachableStates(this).contains(s);
	}
}
