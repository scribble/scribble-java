package org.scribble.model.wf;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.scribble.model.global.GIOAction;
import org.scribble.model.local.IOAction;
import org.scribble.model.local.Receive;
import org.scribble.model.local.Send;
import org.scribble.sesstype.name.Role;


// FIXME: make a WFModel front end class (cf. EndpointGraph)
// FIXME: refactor to use ModelState?
// FIXME: refactor to model.global package
public class WFState
{
	private static int count = 0;  // FIXME: factor out with ModelAction
	
	public final int id;

	public final WFConfig config;
	//protected final LinkedHashMap<GModelAction, WFState> edges;
	//protected final List<GModelAction> actions;
	protected final List<GIOAction> actions;
	protected final List<WFState> succs;
	
	public WFState(WFConfig config)
	{
		this.id = WFState.count++;
		this.config = config;
		//this.edges = new LinkedHashMap<>();
		this.actions = new LinkedList<>();
		this.succs = new LinkedList<>();
	}
	
	//public void addEdge(GModelAction a, WFState s)
	public void addEdge(GIOAction a, WFState s)
	{
		//this.edges.put(a, s);
		Iterator<GIOAction> as = this.actions.iterator();
		Iterator<WFState> ss = this.succs.iterator();
		while (as.hasNext())
		{
			GIOAction tmpa = as.next();
			WFState tmps = ss.next();
			if (tmpa.equals(a) && tmps.equals(s))
			{
				return;
			}
		}
		this.actions.add(a);
		this.succs.add(s);
	}
	
	public List<GIOAction> getActions()
	{
		return Collections.unmodifiableList(this.actions);
	}

	//public Set<GModelAction> getAcceptable()
	public Map<Role, List<IOAction>> getTakeable()  // NB: config semantics, not graph edges (cf, ModelState) -- getActions for that
	{
		//return Collections.unmodifiableSet(this.edges.keySet());
		return this.config.getTakeable();
	}
	
	/*public boolean isAcceptable(GModelAction a)
	{
		return this.edges.containsKey(a);
	}*/

	//public WFState accept(GModelAction a)
	public List<WFConfig> accept(Role r, IOAction a)
	{
		return this.config.accept(r, a);
	}

	public List<WFConfig> sync(Role r1, IOAction a1, Role r2, IOAction a2)
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

	public List<WFState> getSuccessors()  // NB graph edges, not config semantics (cf, getAcceptable)
	{
		//return Collections.unmodifiableCollection(this.edges.values());
		return Collections.unmodifiableList(this.succs);
	}
	
	public boolean isError()
	{
		/*return this.config.getReceptionErrors().isEmpty()
				&& this.config.getDeadlocks().isEmpty()
				&& this.config.getOrphanMessages().isEmpty();*/
		return isTerminal() && !this.config.isSafeTermination();  // FIXME: is this characterisation more "complete"?
	}
	
	public WFStateErrors getErrors()
	{
		Map<Role, Receive> stuck = this.config.getStuckMessages();
		Set<Set<Role>> waitfor = this.config.getWaitForErrors();
		//Set<Set<Role>> waitfor = Collections.emptySet();
		Map<Role, Set<Send>> orphs = this.config.getOrphanMessages();
		return new WFStateErrors(stuck, waitfor, orphs);
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
		//for (Entry<GModelAction, WFState> e : this.edges.entrySet())
		for (int i = 0; i < this.actions.size(); i++)
		{
			/*GModelAction msg = e.getKey();
			WFState p = e.getValue();*/
			GIOAction msg = this.actions.get(i);
			WFState p = this.succs.get(i);
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
	protected String toEdgeDot(GIOAction msg, WFState next)
	{
		return toEdgeDot(getDotNodeId(), next.getDotNodeId(), next.getEdgeLabel(msg));
	}
	
	// "this" is the dest node of the edge
	// Override to change edge drawing to "this" as dest
	//protected String getEdgeLabel(GModelAction msg)
	protected String getEdgeLabel(GIOAction msg)
	{
		return "label=\"" + msg + "\"";
	}

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
}
