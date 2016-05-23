package org.scribble.visit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.ast.local.LProtocolBlock;
import org.scribble.del.local.LProtocolDefDel;
import org.scribble.main.ScribbleException;
import org.scribble.model.global.GIOAction;
import org.scribble.model.local.EndpointGraph;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.model.wf.WFBuffers;
import org.scribble.model.wf.WFConfig;
import org.scribble.model.wf.WFState;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;

public class GlobalModelChecker extends ModuleContextVisitor
{
	public GlobalModelChecker(Job job)
	{
		super(job);
	}

	@Override
	public void enter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.enter(parent, child);
		child.del().enterCompatCheck(parent, child, this);
	}
	
	@Override
	public ScribNode leave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveCompatCheck(parent, child, this, visited);
		return super.leave(parent, child, visited);
	}

	/*@Override
	protected WFChoicePathEnv makeRootProtocolDeclEnv(ProtocolDecl<? extends ProtocolKind> pd)
	{
		return new WFChoicePathEnv();
	}*/

	@Override
	public ScribNode visit(ScribNode parent, ScribNode child) throws ScribbleException
	{
		/*if (child instanceof Choice<?>)
		{
			return visitOverrideForChoice((InteractionSeq<?>) parent, (Choice<?>) child);
		}*/
		if (child instanceof ProtocolDecl<?>)
		{
			if (child instanceof GProtocolDecl)
			{
				return visitOverrideForGProtocolDecl((Module) parent, (GProtocolDecl) child);
			}
			else
			{
				return child;
			}
		}
		else
		{
			return super.visit(parent, child);
		}
	}

	// Refactor to GProtocolDeclDel
	private GProtocolDecl visitOverrideForGProtocolDecl(Module parent, GProtocolDecl child) throws ScribbleException
	{
		GProtocolDecl gpd = (GProtocolDecl) child;
		GProtocolName fullname = gpd.getFullMemberName(parent);
		
		Map<Role, EndpointState> fsms = new HashMap<>();
		
		for (Role self : gpd.header.roledecls.getRoles())
		{
			/*GProtocolBlock gpb = gpd.getDef().getBlock();
			LProtocolBlock proj = ((GProtocolBlockDel) gpb.del()).project(gpb, self);*/
			LProtocolBlock proj = ((LProtocolDefDel) this.getJobContext().getProjection(fullname, self).getLocalProtocolDecls().get(0).def.del()) .getInlinedProtocolDef().getBlock();
			
			EndpointGraphBuilder graph = new EndpointGraphBuilder(getJob());
			graph.builder.reset();
			proj.accept(graph);  // Don't do on root decl, side effects job context
			EndpointGraph fsm = new EndpointGraph(graph.builder.getEntry(), graph.builder.getExit());

			//System.out.println("bbb: " + fsm);
			
			fsms.put(self, fsm.init);
		}
			
		WFConfig c0 = new WFConfig(fsms, new WFBuffers(fsms.keySet()));
		WFState init = new WFState(c0);
		
		HashSet<WFState> seen = new HashSet<>();
		LinkedHashSet<WFState> todo = new LinkedHashSet<>();
		todo.add(init);
		
		int count = 0;
		while (!todo.isEmpty())
		{
			Iterator<WFState> i = todo.iterator();
			WFState curr = i.next();
			i.remove();
			seen.add(curr);

			count ++;
			if (count % 50 == 0)
			{
				getJob().debugPrintln("(" + fullname + ") Checking global states: " + count);
			}
			
			Map<Role, List<IOAction>> acceptable = curr.getAcceptable();

			//System.out.println("ccc: " + acceptable);

			for (Role r : acceptable.keySet())
			{
				for (IOAction a : acceptable.get(r))
				{
					for (WFConfig next : curr.accept(r, a))
					{
						WFState succ = new WFState(next);
						if (seen.contains(succ))  // FIXME: make a WFModel builder
						{
							for (WFState tmp : seen)
							{
								if (tmp.equals(succ))
								{
									succ = tmp;
								}
							}
						}
						for (WFState tmp : todo)
						{
							if (tmp.equals(succ))
							{
								succ = tmp;
							}
						}
						curr.addEdge(a.toGlobal(r), succ);
						if (!seen.contains(succ) && !todo.contains(succ))
						{
							todo.add(succ);
						}
					}
				}
			}
		}
		getJob().debugPrintln("(" + fullname + ") Checked global states: " + count);

		//System.out.println("ddd:\n" + init.toDot());

		Set<WFState> terms = init.findTerminalStates();
		Set<WFState> errors = terms.stream().filter((s) -> s.isError()).collect(Collectors.toSet());
		if (!errors.isEmpty())
		{
			String e = "";
			for (WFState error : errors)
			{
				//List<GModelAction> trace = dfs(new LinkedList<>(), Arrays.asList(init), error);
				List<GIOAction> trace = dfs(new LinkedList<>(), Arrays.asList(init), error);
				e += "\n" + error.toString() + "\n" + trace;
			}
			throw new ScribbleException("\n" + init.toDot() + "\nGlobal model safety violations:" + e);
		}

		this.getJobContext().addGlobalModel(gpd.getFullMemberName((Module) parent), init);
		
		return child;
	}
	
	//private static List<GModelAction> dfs(List<GModelAction> trace, List<WFState> seen, WFState term)
	private static List<GIOAction> dfs(List<GIOAction> trace, List<WFState> seen, WFState term)
	{
		WFState curr = seen.get(seen.size() - 1);
		//Iterator<GModelAction> as = curr.getActions().iterator();
		Iterator<GIOAction> as = curr.getActions().iterator();
		Iterator<WFState> ss = curr.getSuccessors().iterator();
		while (as.hasNext())
		{
			//GModelAction a = as.next();
			GIOAction a = as.next();
			WFState s = ss.next();
			if (s.equals(term))
			{
				trace.add(a);
				return trace;
			}
			if (!seen.contains(s))
			{
				List<GIOAction> tmp1 = new LinkedList<>(trace);
				tmp1.add(a);
				List<WFState> tmp2 = new LinkedList<>(seen);
				tmp2.add(s);
				List<GIOAction> res = dfs(tmp1, tmp2, term);
				if (res != null)
				{
					return res;
				}
			}
		}
		return null;
	}
	
	/*private static void checkWF(Set<GModelState> seen, GModelState curr, GModel model, Map<GModelState, Set<GModelState>> reach)
	{
		if (seen.contains(curr))
		{
			return;
		}
		Collection<GModelState> succs = curr.getSuccessors();

		if (curr.isChoice())  // ** Identifying choice states and their exits is determined by syntax, not model structure 
		{
			List<GModelPath> paths = new LinkedList<>();
			getPaths(paths, new GModelPath(), curr, curr.getChoiceExit(), reach);

			System.out.println("ccc: " + curr + ", " + curr.getChoiceExit() + "\n" + paths);
			
			//...for each role, project each path (can be empty projection)
			//...check consistent enablers -- maybe not needed if enough recursion paths checked?
			//...except subject, check non-uniform continuation implies distinct (input) enabling -- this should also support non-subject output
			
			Set<Role> roles = paths.stream().flatMap((p) -> p.getRoles().stream()).collect(Collectors.toSet());

			System.out.println("ddd: " + roles);
			
			for (Role r : roles)
			{
				List<IOTrace> projs = paths.stream().map((p) -> p.project(r)).collect(Collectors.toList());

				System.out.println("eee: " + r);
				System.out.println(projs);
			}
		}

		seen.add(curr);
		for (GModelState succ : succs)
		{
			checkWF(seen, succ, model, reach);
		}
	}

	// Pre: curr is state at end of path
	private static void getPaths(List<GModelPath> paths, GModelPath path, GModelState curr, GModelState dest, Map<GModelState, Set<GModelState>> reach)
	{
		for (GModelAction a : curr.getAcceptable())
		{
			GModelState succ = curr.accept(a);
			if (succ.equals(dest))
			{
				paths.add(path.append(a));
			}
			else
			{
				if (reach.get(succ).contains(dest))
				{
					if (!path.containsEdge(a))
					{
						GModelPath tmp = path.append(a);
						getPaths(paths, tmp, succ, dest, reach);
					}
				}
			}
		}
	}
	
	private static void getReachability(Set<GModelState> seen, GModelState curr, Map<GModelState, Set<GModelState>> reach, Map<GModelState, Set<GModelState>> invreach)
	{
		if (seen.contains(curr))
		{
			return;
		}
		Collection<GModelState> succs = curr.getSuccessors();
		for (GModelState succ : succs)
		{
			Set<GModelState> tmp1 = reach.get(curr);
			if (tmp1 == null)
			{
				tmp1 = new HashSet<>();
				reach.put(curr, tmp1);
			}
			tmp1.add(succ);

			Set<GModelState> tmp2 = invreach.get(succ);
			if (tmp2 == null)
			{
				tmp2 = new HashSet<>();
				invreach.put(succ, tmp2);
			}
			tmp2.add(curr);
			
			Set<GModelState> tmp3 = invreach.get(curr);
			if (tmp3 != null)
			{
				for (GModelState s : tmp3)
				{
					tmp2.add(s);
					
					Set<GModelState> tmp4 = reach.get(s);
					if (tmp4 == null)  // Not needed?
					{
						tmp4 = new HashSet<>();
						reach.put(s, tmp4);
					}
					tmp4.add(succ);
				}
			}
			HashSet<GModelState> bar = new HashSet<>(seen);
			bar.add(curr);
			getReachability(bar, succ, reach, invreach);
		}
	}
	
	/*private ScribNode visitOverrideForChoice(InteractionSeq<?> parent, Choice<?> child) throws ScribbleException
	{
		if (child instanceof Choice<?>)
		{
			Choice<?> cho = (Choice<?>) child;
			if (!this.visited.contains(cho))
			{
				this.visited.add(cho);
				//ScribNode n = cho.visitChildren(this);
				ScribNode n = super.visit(parent, child);
				this.visited.remove(cho);
				return n;
			}
			else
			{
				return cho;
			}
		}
		else
		{
			return super.visit(parent, child);
		}
	}
	
	@Override
	protected void unfoldingEnter(ScribNode parent, ScribNode child) throws ScribbleException
	//protected void pathCollectionEnter(ScribNode parent, ScribNode child) throws ScribbleException
	{
		super.unfoldingEnter(parent, child);
		//super.pathCollectionEnter(parent, child);
		child.del().enterWFChoicePathCheck(parent, child, this);
	}
	
	@Override
	protected ScribNode unfoldingLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	//protected ScribNode pathCollectionLeave(ScribNode parent, ScribNode child, ScribNode visited) throws ScribbleException
	{
		visited = visited.del().leaveWFChoicePathCheck(parent, child, this, visited);
		return super.unfoldingLeave(parent, child, visited);
		//return super.pathCollectionLeave(parent, child, visited);
		/* // NOTE: deviates from standard Visitor pattern: calls super first to collect paths -- maybe refactor more "standard way" by collecting paths in a prior pass and save them in context
		visited = super.pathCollectionLeave(parent, child, visited);
		return visited.del().leaveWFChoicePathCheck(parent, child, this, visited);* /
	}*/
}
