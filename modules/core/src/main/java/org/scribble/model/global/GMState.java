package org.scribble.model.global;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.scribble.model.MState;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.model.endpoint.actions.EReceive;
import org.scribble.model.endpoint.actions.ESend;
import org.scribble.model.global.actions.GMAction;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.Role;


// FIXME: make a WFModel front end class (cf. EndpointGraph)
// FIXME: refactor to use ModelState?
// FIXME: refactor to model.global package
public class GMState extends MState<GMAction, GMState, Global>
{
	/*private static int count = 0;  // FIXME: factor out with ModelAction
	
	public final int id;*/

	public final GMConfig config;
	//protected final LinkedHashMap<GModelAction, WFState> edges;
	//protected final List<GModelAction> actions;

	/*protected final List<GMAction> actions;
	protected final List<GMState> succs;*/
	
	public GMState(GMConfig config)
	{
		super(Collections.emptySet());  // FIXME: recvar set

		//this.id = GMState.count++;
		this.config = config;
		/*//this.edges = new LinkedHashMap<>();
		this.actions = new LinkedList<>();
		this.succs = new LinkedList<>();*/
	}
	
	/*@Override
	//public void addEdge(GModelAction a, WFState s)
	public void addEdge(GMAction a, GMState s)
	{
		//this.edges.put(a, s);
		Iterator<GMAction> as = this.actions.iterator();
		Iterator<GMState> ss = this.succs.iterator();
		while (as.hasNext())
		{
			GMAction tmpa = as.next();
			GMState tmps = ss.next();
			if (tmpa.equals(a) && tmps.equals(s))
			{
				return;
			}
		}
		this.actions.add(a);
		this.succs.add(s);
	}
	//*/
	
	
	// ... seems actions are the "static structure" of the graph
	// takeable are the "semantic" options of the system state ("session")
	// getActions should be the same as getTakeable if model fully built?
	
	/*
	@Override
	public List<GMAction> getActions()
	{
		return Collections.unmodifiableList(this.actions);
	}
	//*/

	//public Set<GModelAction> getAcceptable()
	public Map<Role, List<EAction>> getTakeable()  // NB: config semantics, not graph edges (cf, ModelState) -- getActions for that
	{
		//return Collections.unmodifiableSet(this.edges.keySet());
		return this.config.getTakeable();
	}
	
	/*public boolean isAcceptable(GModelAction a)
	{
		return this.edges.containsKey(a);
	}*/

	//public WFState accept(GModelAction a)
	public List<GMConfig> take(Role r, EAction a)
	{
		return this.config.take(r, a);
	}

	public List<GMConfig> sync(Role r1, EAction a1, Role r2, EAction a2)
	{
		return this.config.sync(r1, a1, r2, a2);
	}
	
	/*// No good for non-det models
	public WFState getSuccessor(GIOAction a)  // NB graph edges, not config semantics (cf, getAcceptable)
	{
		Iterator<GIOAction> as = this.actions.iterator();
		Iterator<WFState> ss = this.succs.iterator();
		while (as.hasNext())
		{
			if (as.next().equals(a))
			{
				return ss.next();
			}
		}
		return null;
	}*/

	/*
	@Override
	public List<GMState> getSuccessors()  // NB graph edges, not config semantics (cf, getAcceptable)
	{
		//return Collections.unmodifiableCollection(this.edges.values());
		return Collections.unmodifiableList(this.succs);
	}
	//*/
	
	/*public boolean isError()
	{
		/*return this.config.getReceptionErrors().isEmpty()
				&& this.config.getDeadlocks().isEmpty()
				&& this.config.getOrphanMessages().isEmpty();* /
		return isTerminal() && !this.config.isSafeTermination();  // FIXME: is this characterisation more "complete"?  // FIXME: isTerminal not considering non-initiated accepts
	}*/
	
	public GMStateErrors getErrors()
	{
		Map<Role, EReceive> stuck = this.config.getStuckMessages();
		Set<Set<Role>> waitfor = this.config.getWaitForErrors();
		//Set<Set<Role>> waitfor = Collections.emptySet();
		Map<Role, Set<ESend>> orphs = this.config.getOrphanMessages();
		Map<Role, EState> unfinished = this.config.getUnfinishedRoles();
		return new GMStateErrors(stuck, waitfor, orphs, unfinished);
	}
	
	/*
	@Override
	public boolean isTerminal()
	{
		//return this.edges.isEmpty();
		return this.actions.isEmpty();
	}
	//*/

	// **doesn't use super.hashCode (cf., equals) -- FIXME?
	@Override
	public final int hashCode()
	{
		int hash = 79;
		//int hash = super.hashCode();
		hash = 31 * hash + this.config.hashCode();
		return hash;
	}

	// **doesn't use this.id, cf. super.equals -- FIXME?
	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof GMState))
		{
			return false;
		}
		return this.config.equals(((GMState) o).config);  
				// Not using id, cf. ModelState -- FIXME: use a factory pattern that associates unique states and ids? -- use id for hash, and make a separate "semantic equals"
				// Care is needed if hashing, since mutable (currently better to manually use ids, c.f. ModelState)
	}
	
	@Override
	public String toString()
	{
		return this.id + ":" + this.config.toString();
		//return Integer.toString(this.id) + ": " + this.actions;  // FIXME
		//return Integer.toString(this.id);  // FIXME
	}
	
	/*public final String toDot()
	{
		String s = "digraph G {\n" // rankdir=LR;\n
				+ "compound = true;\n";
		s += toDot(new HashSet<>());
		return s + "\n}";
	}

	//protected final String toDot(Set<S> seen)
	protected final String toDot(Set<GMState> seen)
	{
		seen.add(this);
		String s = toNodeDot();
		//for (Entry<GModelAction, WFState> e : this.edges.entrySet())
		for (int i = 0; i < this.actions.size(); i++)
		{
			/*GModelAction msg = e.getKey();
			WFState p = e.getValue();* /
			GMAction msg = this.actions.get(i);
			GMState p = this.succs.get(i);
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
		return "label=\"" + this.id + ":" + labs.substring(1, labs.length() - 1) + "\"";
		//return "label=\"" + this.id + "\"";  // FIXME
	}
	
	protected String getDotNodeId()
	{
		return "\"" + this.id + "\"";
	}

	// Override to change edge drawing from "this" as src
	//protected String toEdgeDot(GModelAction msg, WFState next)
	protected String toEdgeDot(GMAction msg, GMState next)
	{
		return toEdgeDot(getDotNodeId(), next.getDotNodeId(), next.getEdgeLabel(msg));
	}
	
	// "this" is the dest node of the edge
	// Override to change edge drawing to "this" as dest
	//protected String getEdgeLabel(GModelAction msg)
	protected String getEdgeLabel(GMAction msg)
	{
		return "label=\"" + msg + "\"";
	}
	*/

	/*public Set<WFState> findTerminalStates()
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
	}*/
	
	public static Set<GMState> getAllReachable(GMState start)
	{
		Map<Integer, GMState> all = new HashMap<>();
		Map<Integer, GMState> todo = new LinkedHashMap<>();
		todo.put(start.id, start);  // Suppressed: assumes ModelState subclass correctly instantiates S parameter
		while (!todo.isEmpty())
		{
			Iterator<GMState> i = todo.values().iterator();
			GMState next = i.next();
			todo.remove(next.id);
			for (GMState s : next.getAllSuccessors())
			{
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

	public String toAut()
	{
		Set<GMState> all = new HashSet<>();
		all.add(this);
		all.addAll(getAllReachable(this));
		String aut = "";
		int edges = 0;
		Set<Integer> seen = new HashSet<>();
		for (GMState s : all)
		{
			if (seen.contains(s.id))
			{
				continue;
			}
			seen.add(s.id);
			Iterator<GMAction> as = s.getAllActions().iterator();
			Iterator<GMState> ss = s.getAllSuccessors().iterator();
			for (; as.hasNext(); edges++)
			{
				GMAction a = as.next();
				GMState succ = ss.next();
				String msg = a.toStringWithMessageIdHack();  // HACK
				aut += "\n(" + s.id + ",\"" + msg + "\"," + succ.id + ")";
			}
		}
		return "des (" + this.id + "," + edges + "," + all.size() + ")" + aut + "\n";
	}
}
