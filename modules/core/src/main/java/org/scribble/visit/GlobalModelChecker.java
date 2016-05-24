package org.scribble.visit;

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
		Job job = getJob();
		
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

			System.out.println("EFSM:\n" + fsm);
			
			fsms.put(self, fsm.init);
		}
			
		WFConfig c0 = new WFConfig(fsms, new WFBuffers(fsms.keySet()));
		WFState init = new WFState(c0);
		
		Set<WFState> seen = new HashSet<>();
		LinkedHashSet<WFState> todo = new LinkedHashSet<>();
		todo.add(init);

		// FIXME: factor out model building and integrate with getAllNodes (seen == all)
		int count = 0;
		while (!todo.isEmpty())
		{
			Iterator<WFState> i = todo.iterator();
			WFState curr = i.next();
			i.remove();
			seen.add(curr);

			if (job.debug)
			{
				count++;
				if (count % 50 == 0)
				{
					job.debugPrintln("(" + fullname + ") Building global states: " + count);
				}
			}
			
			Map<Role, List<IOAction>> acceptable = curr.getAcceptable();

			System.out.println("ccc: " + acceptable);
					
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
		getJob().debugPrintln("(" + fullname + ") Built global states: " + count);

		//System.out.println("Global model:\n" + init.toDot());

		/*Set<WFState> all = new HashSet<>();
		getAllNodes(init, all);*/
		Set<WFState> all = seen;
		String errorMsg = "";

		Map<WFState, Set<WFState>> reach = getReachability(job, all);

		/*Set<WFState> terms = init.findTerminalStates();
		Set<WFState> errors = terms.stream().filter((s) -> s.isError()).collect(Collectors.toSet());*/
		Set<WFState> errors = all.stream().filter((s) -> s.isError()).collect(Collectors.toSet());
		if (!errors.isEmpty())
		{
			for (WFState error : errors)
			{
				//List<GModelAction> trace = dfs(new LinkedList<>(), Arrays.asList(init), error);
				//List <GIOAction> trace = dfs(new LinkedList<>(), Arrays.asList(init), error);
				List<GIOAction> trace = getTrace(init, error, reach);
				errorMsg += "\nSafety violation at " + error.toString() + ":\ntrace=" + trace;
			}
			//throw new ScribbleException(init.toDot() + "\nSafety violations:" + e);
			//e = "\nSafety violations:" + e;
		}
		
		if (!job.noLiveness)
		{
			/*Map<WFState, Set<WFState>> reach = new HashMap<>();
			//getReachability(getJob(), all, reach);
			reach = getReachability(job, all);*/
			Set<Set<WFState>> termsets = new HashSet<>();
			findTerminalSets(reach, termsets);

			//System.out.println("Terminal sets: " + termsets.stream().map((s) -> s.toString()).collect(Collectors.joining("\n")));
			
			for (Set<WFState> termset : termsets)
			{
				Set<Role> safety = new HashSet<>();
				Set<Role> liveness = new HashSet<>();
				checkTerminalSet(init, termset, safety, liveness);
				if (!safety.isEmpty())
				{
					errorMsg += "\nSafety violation for " + safety + " in terminal set:\n" + termset;
				}
				if (!liveness.isEmpty())
				{
					errorMsg += "\nLiveness violation for " + liveness + " in terminal set:\n" + termset;
				}
			}
		}
		
		if (!errorMsg.equals(""))
		{
			throw new ScribbleException("\n" + init.toDot() + errorMsg);
		}

		this.getJobContext().addGlobalModel(gpd.getFullMemberName((Module) parent), init);
		
		return child;
	}

	 // ** Could subsume terminal state check, if terminal sets included size 1 with reflexive reachability (but maybe not good)
	private static void checkTerminalSet(WFState init, Set<WFState> termset, Set<Role> safety, Set<Role> liveness) throws ScribbleException
	{
		Iterator<WFState> i = termset.iterator();
		WFState s = i.next();
		Map<Role, EndpointState> ss = new HashMap<>(s.config.states);
		while (i.hasNext())
		{
			WFState next = i.next();
			Map<Role, EndpointState> tmp = next.config.states;
			for (Role r : tmp.keySet())
			{
				if (ss.get(r) != null)
				{
					/*if (!ss.get(r).equals(tmp.get(r)))
					{	
						ss.put(r, null);
					}
					else*/
					{
						for (GIOAction a : next.getActions())
						{
							if (a.containsRole(r))
							{
								ss.put(r, null);
								break;
							}
						}
					}
				}
			}
		}
		for (Role r : ss.keySet())
		{
			EndpointState tmp = ss.get(r);
			if (tmp != null)
			{
				if (!tmp.isTerminal())
				{
					//throw new ScribbleException(init.toDot() + "\nLiveness violation for " + r + " in terminal set: " + termset);
					if (s.config.buffs.get(r).values().stream().allMatch((v) -> v == null))
					{
						liveness.add(r);
					}
					else
					{
						safety.add(r);
					}
				}
			}
		}
	}
	
	private static void findTerminalSets(Map<WFState, Set<WFState>> reach, Set<Set<WFState>> termsets)
	{
		Set<Set<WFState>> checked = new HashSet<>();
		for (WFState s : reach.keySet())
		{
			Set<WFState> rs = reach.get(s);
			if (!checked.contains(rs) && rs.contains(s))
			{
				checked.add(rs);
				if (isTerminalSetMember(reach, s))
				{
					termsets.add(rs);
				}
			}
		}
	}

	private static boolean isTerminalSetMember(Map<WFState, Set<WFState>> reach, WFState s)
	{
		Set<WFState> rs = reach.get(s);
		Set<WFState> tmp = new HashSet<>(rs);
		tmp.remove(s);
		for (WFState r : tmp)
		{
			if (!reach.containsKey(r) || !reach.get(r).equals(rs))
			{
				return false;
			}
		}
		return true;
	}

	// Pre: reach.get(start).contains(end)
	private static List<GIOAction> getTrace(WFState start, WFState end, Map<WFState, Set<WFState>> reach)
	{
		List<WFState> seen = new LinkedList<WFState>();
		seen.add(start);
		return getTraceAux(new LinkedList<>(), seen, end, reach);
	}

	private static List<GIOAction> getTraceAux(List<GIOAction> trace, List<WFState> seen, WFState end, Map<WFState, Set<WFState>> reach)
	{
		WFState curr = seen.get(seen.size() - 1);
		Iterator<GIOAction> as = curr.getActions().iterator();
		Iterator<WFState> ss = curr.getSuccessors().iterator();
		while (as.hasNext())
		{
			GIOAction a = as.next();
			WFState s = ss.next();
			if (s.equals(end))
			{
				trace.add(a);
				return trace;
			}
			if (!seen.contains(s) && reach.containsKey(s) && reach.get(s).contains(end))
			{
				List<WFState> tmp1 = new LinkedList<WFState>(seen);
				tmp1.add(s);
				List<GIOAction> tmp2 = new LinkedList<>(trace);
				tmp2.add(a);
				// Recursive calling allows implicit backtracking, in case went into a loop (and can't get back out due to "seen")
				List<GIOAction> res = getTraceAux(tmp2, tmp1, end, reach);
				if (res != null)
				{
					return res;
				}
			}
		}
		return null;
	}
	
	private static Map<WFState, Set<WFState>> getReachability(Job job, Set<WFState> all)
	{
		Map<WFState, Integer> all1 = new HashMap<>();
		Map<Integer, WFState> all2 = new HashMap<>();
		int i = 0;
		for (WFState s : all)
		{
			all1.put(s, i);
			all2.put(i, s);
			i++;
		}
		return getReachabilityAux(job, all1, all2);
	}

	private static Map<WFState, Set<WFState>> getReachabilityAux(Job job, Map<WFState, Integer> all1, Map<Integer, WFState> all2)
	{
		int size = all1.keySet().size();
		boolean[][] reach = new boolean[size][size];
		for (WFState s1 : all1.keySet())
		{
			for (WFState s2 : s1.getSuccessors())
			{
				reach[all1.get(s1)][all1.get(s2)] = true;
			}
		}
		for (boolean again = true; again; )
		{
			again = false;
			for (int i = 0; i < size; i++)
			{
				for (int j = 0; j < size; j++)
				{
					if (reach[i][j])
					{
						for (int k = 0; k < size; k++)
						{
							if (reach[j][k] && !reach[i][k])
							{
								reach[i][k] = true;
								again = true;
							}
						}
					}
				}
			}
		}
		Map<WFState, Set<WFState>> res = new HashMap<>();
		for (int i = 0; i < size; i++)
		{
			for (int j = 0; j < size; j++)
			{
				if (reach[i][j])
				{
					Set<WFState> tmp = res.get(all2.get(i));
					if (tmp == null)
					{
						tmp = new HashSet<>();
						res.put(all2.get(i), tmp);
					}
					tmp.add(all2.get(j));
				}
			}
		}
		return res;
	}

	/*private static void getReachability(Job job, Set<WFState> all, Map<WFState, Set<WFState>> reach)
	{
		//long t1 = System.currentTimeMillis();
		int count = 0, step = 1;
		for (WFState s1 : all)
		{
			if (job.debug && count >= (0.1*step) * all.size())
			{
				job.debugPrintln("Computing reachability: " + (10*step) + "%");
				for (; count >= (0.1*(step)) * all.size(); step++);
			}
			for (WFState s2 : all)
			{
				//if (dfs(new LinkedList<>(), Arrays.asList(s1), s2) != null)
				//if (dfs(new LinkedList<>(), Arrays.asList(s1), s2, reach) != null)
				if (bfs(s1, s2, reach))
				{
					Set<WFState> tmp = reach.get(s1);
					if (tmp == null)
					{
						tmp = new HashSet<>();
						reach.put(s1, tmp);
					}
					tmp.add(s2);
				}
			}
			count++;
		}
		//System.out.println("Reachability computed in : " + (System.currentTimeMillis() - t1) + "ms");
	}
	
	private static boolean bfs(WFState start, WFState end, Map<WFState, Set<WFState>> reach)
	{
		List<WFState> seen = new LinkedList<>();
		LinkedHashSet<WFState> todo = new LinkedHashSet<>();
		todo.addAll(start.getSuccessors());  // Not just "start", check for explicit reflexive edge
		while (!todo.isEmpty())
		{
			Iterator<WFState> i = todo.iterator();
			WFState next = i.next();
			i.remove();
			if (next.equals(end))
			{
				return true;
			}
			if (!seen.contains(next))
			{
				Set<WFState> tmp = reach.get(next);
				if (tmp != null && tmp.contains(end))
				{
					return true;
				}
				seen.add(next);
				todo.addAll(next.getSuccessors());
			}
		}
		return false;
	}*/

	/*// trace = actions on path, seen = nodes on path, term = dest
	//private static List<GModelAction> dfs(List<GModelAction> trace, List<WFState> seen, WFState term)
	private static List<GIOAction> dfs(List<GIOAction> trace, List<WFState> seen, WFState term)
	{
		return dfs(trace, seen, term, Collections.emptyMap());
	}
	
	private static List<GIOAction> dfs(List<GIOAction> trace, List<WFState> seen, WFState end, Map<WFState, Set<WFState>> reach)
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
			if (s.equals(end))
			{
				trace.add(a);
				return trace;
			}
			if (!seen.contains(s))
			{
				Set<WFState> foo = reach.get(s);
				if (foo != null && foo.contains(end))
				{
					return Collections.emptyList();  // no trace returned on this shortcut
				}

				List<GIOAction> tmp1 = new LinkedList<>(trace);
				tmp1.add(a);
				List<WFState> tmp2 = new LinkedList<>(seen);
				tmp2.add(s);
				List<GIOAction> res = dfs(tmp1, tmp2, end);
				if (res != null)
				{
					return res;
				}
			}
		}
		return null;
	}
	//*/
	
	/*private static void getAllNodes(WFState curr, Set<WFState> all)
	{
		if (all.contains(curr))
		{
			return;
		}
		all.add(curr);
		for (WFState s : curr.getSuccessors())
		{
			getAllNodes(s, all);
		}
	}*/
	
	/*private static void findTerminalSets(Set<WFState> all, Map<WFState, Set<WFState>> reach, Set<Set<WFState>> termsets)
	{
		Map<Set<WFState>, Set<Set<WFState>>> sss = new HashMap<>();
		getSubsets(all, sss);
		for (Set<Set<WFState>> ss : sss.values())
		{
			for (Set<WFState> s : ss)
			{
				if (isTerminalSet(all, reach, s))
				{
					termsets.add(s);
				}
			}
		}
	}

	private static void getSubsets(Set<WFState> rem, Map<Set<WFState>, Set<Set<WFState>>> ss)
	{
		System.out.println("ccc: " + rem);
		Set<Set<WFState>> tmp = ss.get(rem);
		if (tmp == null)
		{
			tmp = new HashSet<>();
			ss.put(rem, tmp);
		}
		tmp.add(rem);
		if (rem.size() == 1)
		{
			return;
		}
		for (WFState s : rem)
		{
			Set<WFState> tmp2 = new HashSet<>(rem);
			tmp2.remove(s);
			if (!ss.containsKey(tmp2))
			{
				getSubsets(tmp2, ss);
			}
			/*Set<Set<WFState>> cached = ss.get(tmp2);
			for (Set<WFState> c : cached)
			{
				Set<WFState> tmp3 = new HashSet<>(c);
				tmp3.add(s);
				tmp.add(tmp3);
			}* /
		}
	}

	private static boolean isTerminalSet(Set<WFState> all, Map<WFState, Set<WFState>> reach, Set<WFState> curr)
	{
		for (WFState s1 : curr)
		{
			for (WFState s2 : curr)
			{
				Set<WFState> tmp = reach.get(s1);
				if (tmp == null || !tmp.contains(s2))
				{
					return false;
				}
			}
		}
		for (WFState r : all)
		{
			Set<WFState> tmp = reach.get(curr);
			if (!curr.contains(r) && tmp != null && tmp.contains(r))
			{
				return false;
			}
		}
		return true;
	}*/
	
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
