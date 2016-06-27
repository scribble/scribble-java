package org.scribble.visit;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.scribble.ast.Module;
import org.scribble.ast.ProtocolDecl;
import org.scribble.ast.ScribNode;
import org.scribble.ast.global.GProtocolDecl;
import org.scribble.main.ScribbleException;
import org.scribble.model.global.GIOAction;
import org.scribble.model.local.EndpointFSM;
import org.scribble.model.local.EndpointGraph;
import org.scribble.model.local.EndpointState.Kind;
import org.scribble.model.local.IOAction;
import org.scribble.model.local.Send;
import org.scribble.model.wf.WFBuffers;
import org.scribble.model.wf.WFConfig;
import org.scribble.model.wf.WFState;
import org.scribble.model.wf.WFStateErrors;
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
				GProtocolDecl gpd = (GProtocolDecl) child;
				return (gpd.isAuxModifier()) ? gpd : visitOverrideForGProtocolDecl((Module) parent, gpd);
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
		
		//Map<Role, EndpointState> egraphs = getEndpointGraphs(fullname, gpd);
		Map<Role, EndpointFSM> egraphs = getEndpointFSMs(fullname, gpd);
			
		//Set<WFState> seen = buildGlobalModel(job, fullname, init);  // Returns set of all states
		//Set<WFState> seen = new HashSet<>();
		Map<Integer, WFState> seen = new HashMap<>();
		WFState init = buildGlobalModel(fullname, gpd, egraphs, seen);  // Post: seen contains all states
		this.getJobContext().addGlobalModel(fullname, init);

		/*Set<WFState> all = new HashSet<>();
		getAllNodes(init, all);*/
		checkGlobalModel(fullname, init, seen);

		return child;
	}

	//private void checkGlobalModel(GProtocolName fullname, WFState init, Set<WFState> all) throws ScribbleException
	private void checkGlobalModel(GProtocolName fullname, WFState init, Map<Integer, WFState> all) throws ScribbleException
	{
		Job job = getJob();
		String errorMsg = "";

		//Map<WFState, Set<WFState>> reach = getReachability(job, all);
		Map<Integer, Set<Integer>> reach = getReachability(job, all);

		/*Set<WFState> terms = init.findTerminalStates();
		Set<WFState> errors = terms.stream().filter((s) -> s.isError()).collect(Collectors.toSet());*/
		/*
		Set<WFState> errors = all.stream().filter((s) -> s.isError()).collect(Collectors.toSet());
		if (!errors.isEmpty())
		{
			for (WFState error : errors)
			{
				//List<GModelAction> trace = dfs(new LinkedList<>(), Arrays.asList(init), error);
				//List <GIOAction> trace = dfs(new LinkedList<>(), Arrays.asList(init), error);
				List<GIOAction> trace = getTrace(init, error, reach);
				errorMsg += "\nSafety violation at " + error.toString() + ":\n  trace=" + trace;
			}
		//throw new ScribbleException(init.toDot() + "\nSafety violations:" + e);
		//e = "\nSafety violations:" + e;
		}
		/*/
		int count = 0;
		for (WFState s : all.values())
		{
			if (job.debug)
			{
				count++;
				if (count % 50 == 0)
				{
					job.debugPrintln("(" + fullname + ") Checking global states: " + count);
				}
			}
			WFStateErrors errors = s.getErrors();
			if (!errors.isEmpty())
			{
				// FIXME: getTrace can get stuck when local choice subjects are disabled
				//List<GIOAction> trace = getTrace(init, s, reach);  // FIXME: getTrace broken on non-det self loops?
				List<GIOAction> trace = getTrace(all, init, s, reach);  // FIXME: getTrace broken on non-det self loops?
				errorMsg += "\nSafety violation(s) at " + s.toString() + ":\n    Trace=" + trace;
			}
			if (!errors.stuck.isEmpty())
			{
				errorMsg += "\n    Stuck messages: " + errors.stuck;  // Deadlock from reception error
			}
			if (!errors.waitFor.isEmpty())
			{
				errorMsg += "\n    Wait-for errors: " + errors.waitFor;  // Deadlock from input-blocked cycles, terminated dependencies, etc
			}
			if (!errors.orphans.isEmpty())
			{
				errorMsg += "\n    Orphan messages: " + errors.orphans;  // FIXME: add sender of orphan to error message 
			}
		}
		job.debugPrintln("(" + fullname + ") Checked all states: " + count);
		//*/
		
		if (!job.noLiveness)
		{
			/*Map<WFState, Set<WFState>> reach = new HashMap<>();
			//getReachability(getJob(), all, reach);
			reach = getReachability(job, all);*/
			//Set<Set<WFState>> termsets = new HashSet<>();
			Set<Set<Integer>> termsets = new HashSet<>();
			findTerminalSets(all, reach, termsets);

			//System.out.println("Terminal sets: " + termsets.stream().map((s) -> s.toString()).collect(Collectors.joining("\n")));

			//for (Set<WFState> termset : termsets)
			for (Set<Integer> termset : termsets)
			{
				Set<Role> safety = new HashSet<>();
				Set<Role> roleLiveness = new HashSet<>();
				//checkTerminalSet(init, termset, safety, roleLiveness);
				checkTerminalSet(all, init, termset, safety, roleLiveness);
				if (!safety.isEmpty())
				{
					// Redundant
					errorMsg += "\nSafety violation for " + safety + " in terminal set:\n    " + termset.stream().map((i) -> all.get(i).toString()).collect(Collectors.joining(","));
				}
				if (!roleLiveness.isEmpty())
				{
					errorMsg += "\nRole progress violation for " + roleLiveness + " in terminal set:\n    " + termset.stream().map((i) -> all.get(i).toString()).collect(Collectors.joining(","));
				}
				Map<Role, Set<Send>> msgLiveness = checkMessageLiveness(all, init, termset);
				if (!msgLiveness.isEmpty())
				{
					errorMsg += "\nMessage liveness violation for " + msgLiveness + " in terminal set:\n    " + termset.stream().map((i) -> all.get(i).toString()).collect(Collectors.joining(","));
				}
			}
		}
		
		if (!errorMsg.equals(""))
		{
			//throw new ScribbleException("\n" + init.toDot() + errorMsg);
			throw new ScribbleException(errorMsg);
		}
	}

	//private Set<WFState> buildGlobalModel(Job job, GProtocolName fullname, WFState init) throws ScribbleException
	//private WFState buildGlobalModel(GProtocolName fullname, GProtocolDecl gpd, Map<Role, EndpointState> egraphs, Set<WFState> seen) throws ScribbleException
	//private WFState buildGlobalModel(GProtocolName fullname, GProtocolDecl gpd, Map<Role, EndpointFSM> egraphs, Set<WFState> seen) throws ScribbleException
	private WFState buildGlobalModel(GProtocolName fullname, GProtocolDecl gpd, Map<Role, EndpointFSM> egraphs, Map<Integer, WFState> seen) throws ScribbleException
	{
		Job job = getJob();
		//JobContext jc = job.getContext();
		
		/*//if (false)
		if (!job.fair && !job.noLiveness)
		{
			for (Entry<Role, EndpointFSM> e : egraphs.entrySet())
			{
				Role r = e.getKey();
				EndpointFSM nonfair = e.getValue().init.unfairTransform().toGraph().toFsm();
				egraphs.put(r, nonfair);

				job.debugPrintln("(" + fullname + ") Non-fair EFSM for " + r + ":\n" + nonfair.init.toDot());
			}
		}*/

		WFBuffers b0 = new WFBuffers(egraphs.keySet(), !gpd.modifiers.contains(GProtocolDecl.Modifiers.EXPLICIT));
		//WFConfig c0 = new WFConfig(egraphs, b0);
		WFConfig c0 = new WFConfig(egraphs, b0);
		WFState init = new WFState(c0);

		//Set<WFState> seen = new HashSet<>();
		LinkedHashSet<WFState> todo = new LinkedHashSet<>();
		todo.add(init);

		// FIXME: factor out model building and integrate with getAllNodes (seen == all)
		int count = 0;
		while (!todo.isEmpty())
		{
			Iterator<WFState> i = todo.iterator();
			WFState curr = i.next();
			i.remove();
			//seen.add(curr);
			seen.put(curr.id, curr);

			if (job.debug)
			{
				count++;
				if (count % 50 == 0)
				{
					job.debugPrintln("(" + fullname + ") Building global states: " + count);
				}
			}
			
			Map<Role, List<IOAction>> takeable = curr.getTakeable();

			//job.debugPrintln("Acceptable at (" + curr.id + "): " + acceptable);

			for (Role r : takeable.keySet())
			{
				List<IOAction> acceptable_r = takeable.get(r);
				
				// Hacky?  // FIXME: factor out and make more robust (e.g. for new state kinds) -- e.g. "hasPayload" in IOAction
				//EndpointState currstate = curr.config.states.get(r);
				EndpointFSM currfsm = curr.config.states.get(r);
				Kind k = currfsm.getStateKind();
				if (k == Kind.OUTPUT)
				{
					for (IOAction a : acceptable_r)  // Connect implicitly has no payload (also accept, so skip)
					{
						if (acceptable_r.stream().anyMatch((x) ->
								!a.equals(x) && a.peer.equals(x.peer) && a.mid.equals(x.mid) && !a.payload.equals(x.payload)))
						{
							throw new ScribbleException("Bad non-deterministic action payloads: " + acceptable_r);
						}
					}
				}
				else if (k == Kind.UNARY_INPUT || k == Kind.POLY_INPUT || k == Kind.ACCEPT)
				{
					for (IOAction a : acceptable_r)
					{
						if (currfsm.getAllTakeable().stream().anyMatch((x) ->
								!a.equals(x) && a.peer.equals(x.peer) && a.mid.equals(x.mid) && !a.payload.equals(x.payload)))
						{
							throw new ScribbleException("Bad non-deterministic action payloads: " + currfsm.getAllTakeable());
						}
					}
				}
			}  // Need to do all action payload checking before next building step, because doing sync actions will also remove peer's actions from takeable set

			for (Role r : takeable.keySet())
			{
				List<IOAction> acceptable_r = takeable.get(r);
				
				for (IOAction a : acceptable_r)
				{
					if (a.isSend() || a.isReceive() || a.isDisconnect())
					{
						getNextStates(todo, seen, curr, a.toGlobal(r), curr.take(r, a));
					}
					else if (a.isAccept() || a.isConnect())
					{	
						List<IOAction> as = takeable.get(a.peer);
						IOAction d = a.toDual(r);
						if (as != null && as.contains(d))
						{
							as.remove(d);  // Removes one occurrence
							//getNextStates(seen, todo, curr.sync(r, a, a.peer, d));
							GIOAction g = (a.isConnect()) ? a.toGlobal(r) : d.toGlobal(a.peer);
							getNextStates(todo, seen, curr, g, curr.sync(r, a, a.peer, d));
						}
					}
					else if (a.isWrapClient() || a.isWrapServer())
					{
						List<IOAction> as = takeable.get(a.peer);
						IOAction w = a.toDual(r);
						if (as != null && as.contains(w))
						{
							as.remove(w);  // Removes one occurrence
							GIOAction g = (a.isConnect()) ? a.toGlobal(r) : w.toGlobal(a.peer);
							getNextStates(todo, seen, curr, g, curr.sync(r, a, a.peer, w));
						}
					}
					else
					{
						throw new RuntimeException("Shouldn't get in here: " + a);
					}
				}
			}
		}

		job.debugPrintln("(" + fullname + ") Building global model..\n" + init.toDot() + "\n(" + fullname + ") Built global model (" + count + " states)");

		//return seen;
		return init;
	}

	//private Map<Role, EndpointState> getEndpointGraphs(GProtocolName fullname, GProtocolDecl gpd) throws ScribbleException
	private Map<Role, EndpointFSM> getEndpointFSMs(GProtocolName fullname, GProtocolDecl gpd) throws ScribbleException
	{
		Job job = getJob();

		//Map<Role, EndpointState> egraphs = new HashMap<>();
		Map<Role, EndpointFSM> egraphs = new HashMap<>();
		
		for (Role self : gpd.header.roledecls.getRoles())
		{
			/*/*GProtocolBlock gpb = gpd.getDef().getBlock();
			LProtocolBlock proj = ((GProtocolBlockDel) gpb.del()).project(gpb, self);* /
			LProtocolBlock proj = ((LProtocolDefDel) this.getJobContext().getProjection(fullname, self).getLocalProtocolDecls().get(0).def.del()) .getInlinedProtocolDef().getBlock();
			
			EndpointGraphBuilder graph = new EndpointGraphBuilder(getJob());
			graph.builder.reset();
			proj.accept(graph);  // Don't do on root decl, side effects job context
			//EndpointGraph fsm = new EndpointGraph(graph.builder.getEntry(), graph.builder.getExit());
			EndpointGraph fsm = graph.builder.finalise();
			//*/

			/*EndpointGraph graph = job.getContext().getEndpointGraph(fullname, self);
			if (graph == null)
			{
				//LProtocolDecl lpd = this.getJobContext().getProjection(fullname, self).getLocalProtocolDecls().get(0);
				Module proj = this.getJobContext().getProjection(fullname, self);  // Projected module contains a single protocol
				EndpointGraphBuilder builder = new EndpointGraphBuilder(getJob());
				proj.accept(builder);  // Side effects job context (caches graph)
				graph = job.getContext().getEndpointGraph(fullname, self);
			}*/

			EndpointGraph graph = job.getContext().getEndpointGraph(fullname, self);

			job.debugPrintln("(" + fullname + ") EFSM for " + self + ":\n" + graph);

			if (!job.fair && !job.noLiveness)
			{
				graph = job.getContext().getUnfairEndpointGraph(fullname, self);

				job.debugPrintln("(" + fullname + ") Non-fair EFSM for " + self + ":\n" + graph.init.toDot());
			}
			
			//egraphs.put(self, fsm.init);
			egraphs.put(self, graph.toFsm());
		}
		return egraphs;
	}

	//private void foo(Set<WFState> seen, LinkedHashSet<WFState> todo, WFState curr, Role r, IOAction a)
	//private void getNextStates(LinkedHashSet<WFState> todo, Set<WFState> seen, WFState curr, GIOAction a, List<WFConfig> nexts)
	private void getNextStates(LinkedHashSet<WFState> todo, Map<Integer, WFState> seen, WFState curr, GIOAction a, List<WFConfig> nexts)
	{
		for (WFConfig next : nexts)
		{
			WFState news = new WFState(next);
			WFState succ = null; 
			//if (seen.contains(succ))  // FIXME: make a WFModel builder
			/*if (seen.containsValue(succ))
			{
				for (WFState tmp : seen)
				{
					if (tmp.equals(succ))
					{
						succ = tmp;
					}
				}
			}*/
			for (WFState tmp : seen.values())  // Key point: checking "semantically" if model state already created
			{
				if (tmp.equals(news))
				{
					succ = tmp;
				}
			}
			if (succ == null)
			{
				for (WFState tmp : todo)  // If state created but not "seen" yet, then it will be "todo"
				{
					if (tmp.equals(news))
					{
						succ = tmp;
					}
				}
			}
			if (succ == null)
			{
				succ = news;
				todo.add(succ);
			}
			//curr.addEdge(a.toGlobal(r), succ);
			curr.addEdge(a, succ);
			//if (!seen.contains(succ) && !todo.contains(succ))
			/*if (!seen.containsKey(succ.id) && !todo.contains(succ))
			{
				todo.add(succ);
			}*/
		}
	}

	// FIXME: this is now just "role liveness"
	// ** Could subsume terminal state check, if terminal sets included size 1 with reflexive reachability (but probably not good)
	//private static void checkTerminalSet(WFState init, Set<WFState> termset, Set<Role> safety, Set<Role> liveness) throws ScribbleException
	private static void checkTerminalSet(Map<Integer, WFState> all, WFState init, Set<Integer> termset, Set<Role> safety, Set<Role> liveness) throws ScribbleException
	{
		/*Iterator<WFState> i = termset.iterator();
		WFState s = i.next();*/
		Iterator<Integer> i = termset.iterator();
		WFState s = all.get(i.next());
		//Map<Role, EndpointState> ss = new HashMap<>(s.config.states);
		Map<Role, WFState> ss = new HashMap<>();
		s.config.states.keySet().forEach((r) -> ss.put(r, s));
		while (i.hasNext())
		{
			//WFState next = i.next();
			WFState next = all.get(i.next());
			//Map<Role, EndpointState> tmp = next.config.states;
			Map<Role, EndpointFSM> tmp = next.config.states;
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
			WFState foo = ss.get(r);
			if (foo != null)
			{
				//EndpointState tmp = foo.config.states.get(r);
				EndpointFSM tmp = foo.config.states.get(r);
				if (tmp != null)
				{
					//if (!tmp.isTerminal())
					if (!foo.config.canSafelyTerminate(r))
					{
						//throw new ScribbleException(init.toDot() + "\nLiveness violation for " + r + " in terminal set: " + termset);
						if (s.config.buffs.get(r).values().stream().allMatch((v) -> v == null))
						{
							liveness.add(r);
						}
						/*
						// Should be redundant given explicit reception error etc checking
						else
						{
							safety.add(r);
						}*/
					}
				}
			}
		}
	}

	// "message liveness"
	//private static Set<Send> checkMessageLiveness(WFState init, Set<WFState> termset) throws ScribbleException
	//private static Map<Role, Set<Send>> checkMessageLiveness(WFState init, Set<WFState> termset) throws ScribbleException
	private static Map<Role, Set<Send>> checkMessageLiveness(Map<Integer, WFState> all, WFState init, Set<Integer> termset) throws ScribbleException
	{
		//Set<Send> res = new HashSet<>();
		//Set<Role> roles = termset.iterator().next().config.states.keySet();
		Set<Role> roles = all.get(termset.iterator().next()).config.states.keySet();

		/*Iterator<WFState> i = termset.iterator();
		Map<Role, Map<Role, Send>> b0 = i.next().config.buffs.getBuffers();*/
		Iterator<Integer> i = termset.iterator();
		Map<Role, Map<Role, Send>> b0 = all.get(i.next()).config.buffs.getBuffers();
		while (i.hasNext())
		{
			//WFState s = i.next();
			WFState s = all.get(i.next());
			WFBuffers b = s.config.buffs;
			for (Role r1 : roles)
			{
				for (Role r2 : roles)
				{
					Send s0 = b0.get(r1).get(r2);
					if (s0 != null)
					{
						Send tmp = b.get(r1).get(r2);
						if (tmp == null)
						{
							b0.get(r1).put(r2, null);
						}
					}
				}
			}
		}
	
		Map<Role, Set<Send>> res = new HashMap<>();
		for (Role r1 : roles)
		{
			for (Role r2 : roles)
			{
				Send m = b0.get(r1).get(r2);
				if (m != null)
				{
					Set<Send> tmp = res.get(r2);
					if (tmp == null)
					{
						tmp = new HashSet<>();
						res.put(r2, tmp);
					}
					tmp.add(m);
				}
			}
		}
		return res;
	}
	
	//private static void findTerminalSets(Map<WFState, Set<WFState>> reach, Set<Set<WFState>> termsets)
	private static void findTerminalSets(Map<Integer, WFState> all, Map<Integer, Set<Integer>> reach, Set<Set<Integer>> termsets)
	{
		//Set<Set<WFState>> checked = new HashSet<>();
		Set<Set<Integer>> checked = new HashSet<>();
		//for (WFState s : reach.keySet())
		for (Integer i : reach.keySet())
		{
			WFState s = all.get(i);
			/*Set<WFState> rs = reach.get(s);
			if (!checked.contains(rs) && rs.contains(s))*/
			Set<Integer> rs = reach.get(s.id);
			if (!checked.contains(rs) && rs.contains(s.id))
			{
				checked.add(rs);
				if (isTerminalSetMember(all, reach, s))
				{
					termsets.add(rs);
				}
			}
		}
	}

	//private static boolean isTerminalSetMember(Map<WFState, Set<WFState>> reach, WFState s)
	private static boolean isTerminalSetMember(Map<Integer, WFState> all, Map<Integer, Set<Integer>> reach, WFState s)
	{
		/*Set<WFState> rs = reach.get(s);
		Set<WFState> tmp = new HashSet<>(rs);
		tmp.remove(s);
		for (WFState r : tmp)*/
		Set<Integer> rs = reach.get(s.id);
		Set<Integer> tmp = new HashSet<>(rs);
		tmp.remove(s.id);
		for (Integer r : tmp)
		{
			if (!reach.containsKey(r) || !reach.get(r).equals(rs))
			{
				return false;
			}
		}
		return true;
	}

	// Pre: reach.get(start).contains(end)  // FIXME: will return null if initial state is error
	//private static List<GIOAction> getTrace(WFState start, WFState end, Map<WFState, Set<WFState>> reach)
	private static List<GIOAction> getTrace(Map<Integer, WFState> all, WFState start, WFState end, Map<Integer, Set<Integer>> reach)
	{
		/*List<WFState> seen = new LinkedList<WFState>();
		seen.add(start);*/
		/*Set<List<Integer>> seen = new HashSet<>();
		List<Integer> tmp = new LinkedList<>();
		tmp.add(start.id);
		seen.add(tmp);*/
		//return getTraceAux(new LinkedList<>(), seen, start, end, reach);
		//return getTraceAux(new LinkedList<>(), seen, tmp, start, end, reach);
		//return getTraceAux(all, start, end, reach);

		SortedMap<Integer, Set<Integer>> candidates = new TreeMap<>();
		Set<Integer> dis0 = new HashSet<Integer>();
		dis0.add(start.id);
		candidates.put(0, dis0);

		Set<Integer> seen = new HashSet<>();
		seen.add(start.id);
		
		return getTraceAux(new LinkedList<>(), all, seen, candidates, end, reach);
	}

	// Djikstra's
	private static List<GIOAction> getTraceAux(List<GIOAction> trace, Map<Integer, WFState> all, Set<Integer> seen, SortedMap<Integer, Set<Integer>> candidates, WFState end, Map<Integer, Set<Integer>> reach)
	{
		Integer dis = candidates.keySet().iterator().next();
		Set<Integer> cs = candidates.get(dis);
		Iterator<Integer> it = cs.iterator();
		Integer currid = it.next();
		it.remove();
		if (cs.isEmpty())
		{
			candidates.remove(dis);
		}

		WFState curr = all.get(currid);
		Iterator<GIOAction> as = curr.getActions().iterator();
		Iterator<WFState> ss = curr.getSuccessors().iterator();
		while (as.hasNext())
		{
			GIOAction a = as.next();
			WFState s = ss.next();
			if (s.id == end.id)
			{
				trace.add(a);
				return trace;
			}

			if (!seen.contains(s.id) && reach.containsKey(s.id) && reach.get(s.id).contains(end.id))
			{
				seen.add(s.id);
				Set<Integer> tmp1 = candidates.get(dis+1);
				if (tmp1 == null)
				{
					tmp1 = new HashSet<>();
					candidates.put(dis+1, tmp1);
				}
				tmp1.add(s.id);
				List<GIOAction> tmp2 = new LinkedList<>(trace);
				tmp2.add(a);
				List<GIOAction> res = getTraceAux(tmp2, all, seen, candidates, end, reach);
				if (res != null)
				{
					return res;
				}
			}
		}
		return null;
	}

	/*
	private static List<GIOAction> getTraceAux(Map<Integer, WFState> all, WFState start, WFState end, Map<Integer, Set<Integer>> reach)
	{
		//LinkedHashMap<Integer, Integer> candidates = new LinkedHashMap<>();
		Set<Integer> seen = new HashSet<>();
		SortedMap<Integer, Set<Integer>> candidates = new TreeMap<>();
		
		Set<Integer> foo0 = new HashSet<Integer>();
		foo0.add(start.id);
		candidates.put(0, foo0);
		seen.add(start.id);
		
		List<GIOAction> trace = new LinkedList<>();
		
		while (!candidates.isEmpty())
		{
			Integer w = candidates.keySet().iterator().next();
			Set<Integer> tmp = candidates.get(w);
			Iterator<Integer> it = tmp.iterator();
			Integer currid = it.next();
			it.remove();
			if (tmp.isEmpty())
			{
				candidates.remove(w);
			}
			WFState curr = all.get(currid);
			Iterator<GIOAction> as = curr.getActions().iterator();
			Iterator<WFState> ss = curr.getSuccessors().iterator();
			while (as.hasNext())
			{
				GIOAction a = as.next();
				WFState s = ss.next();
				if (s.id == end.id)
				{
					trace.add(a);
					return trace;
				}

				if (!seen.contains(s.id) && reach.containsKey(s.id) && reach.get(s.id).contains(end.id))
				{
					seen.add(s.id);
					Set<Integer> tmp2 = candidates.get(w+1);
					if (tmp2 == null)
					{
						tmp2 = new HashSet<>();
						candidates.put(w+1, tmp2);
					}
					tmp2.add(s.id);
				}
			}
		}
		throw new RuntimeException("Shouldn't get in here: " + start + ", " + end);
	}
	//*/

	/*//private static List<GIOAction> getTraceAux(List<GIOAction> trace, List<WFState> seen, WFState end, Map<WFState, Set<WFState>> reach)
	//private static List<GIOAction> getTraceAux(List<GIOAction> trace, List<Integer> seen, WFState curr, WFState end, Map<WFState, Set<WFState>> reach)
	//private static List<GIOAction> getTraceAux(List<GIOAction> trace, List<Integer> seen, WFState curr, WFState end, Map<Integer, Set<Integer>> reach)
	private static List<GIOAction> getTraceAux(List<GIOAction> trace, Set<List<Integer>> seen, List<Integer> foo, WFState curr, WFState end, Map<Integer, Set<Integer>> reach)
	{
		//WFState curr = seen.get(seen.size() - 1);
		Iterator<GIOAction> as = curr.getActions().iterator();
		Iterator<WFState> ss = curr.getSuccessors().iterator();
		while (as.hasNext())
		{
			GIOAction a = as.next();
			WFState s = ss.next();
			//if (s.equals(end))
			if (s.id == end.id)
			{
				trace.add(a);
				return trace;
			}
			if (!foo.contains(s.id))
			{
				List<Integer> tmp1 = new LinkedList<>(foo);
				tmp1.add(s.id);
				//if (!seen.contains(s) && reach.containsKey(s) && reach.get(s).contains(end))
				//if (!seen.contains(s.id) && reach.containsKey(s.id) && reach.get(s.id).contains(end.id))
				if (!seen.contains(tmp1) && reach.containsKey(s.id) && reach.get(s.id).contains(end.id))
				{
					seen.add(tmp1);
					/*List<WFState> tmp1 = new LinkedList<>(seen);
					tmp1.add(s);*/
					/*List<Integer> tmp1 = new LinkedList<>(seen);
					tmp1.add(s.id);* /
					List<GIOAction> tmp2 = new LinkedList<>(trace);
					tmp2.add(a);
					// Recursive calling allows implicit backtracking, in case went into a loop (and can't get back out due to "seen")
					//List<GIOAction> res = getTraceAux(tmp2, tmp1, end, reach);
					//List<GIOAction> res = getTraceAux(tmp2, tmp1, s, end, reach);
					List<GIOAction> res = getTraceAux(tmp2, seen, tmp1, s, end, reach);
					if (res != null)
					{
						return res;
					}
				}
			}
		}
		return null;
	}*/
	
	//private static Map<WFState, Set<WFState>> getReachability(Job job, Set<WFState> all)
	//private static Map<Integer, Set<Integer>> getReachability(Job job, Set<WFState> all)
	private static Map<Integer, Set<Integer>> getReachability(Job job, Map<Integer, WFState> all)
	{
		/*Map<WFState, Integer> all1 = new HashMap<>();  // FIXME: use Integers
		Map<Integer, WFState> all2 = new HashMap<>();*/
		Map<Integer, Integer> all1 = new HashMap<>();  // Map state ids to array indices and vice versa
		Map<Integer, Integer> all2 = new HashMap<>();
		int i = 0;
		for (WFState s : all.values())
		{
			/*all1.put(s, i);
			all2.put(i, s);*/
			all1.put(s.id, i);
			all2.put(i, s.id);
			i++;
		}
		return getReachabilityAux(job, all, all1, all2);
	}

	//private static Map<WFState, Set<WFState>> getReachabilityAux(Job job, Map<WFState, Integer> all1, Map<Integer, WFState> all2)
	//private static Map<Integer, Set<Integer>> getReachabilityAux(Job job, Map<WFState, Integer> all1, Map<Integer, WFState> all2)
	private static Map<Integer, Set<Integer>> getReachabilityAux(Job job, Map<Integer, WFState> all, Map<Integer, Integer> all1, Map<Integer, Integer> all2)
	{
		int size = all1.keySet().size();
		boolean[][] reach = new boolean[size][size];
		//for (WFState s1 : all1.keySet())
		for (Integer s1id : all1.keySet())
		{
			//for (WFState s2 : s1.getSuccessors())
			for (WFState s2 : all.get(s1id).getSuccessors())
			{
				//reach[all1.get(s1)][all1.get(s2)] = true;
				reach[all1.get(s1id)][all1.get(s2.id)] = true;
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
		//Map<WFState, Set<WFState>> res = new HashMap<>();
		Map<Integer, Set<Integer>> res = new HashMap<>();
		for (int i = 0; i < size; i++)
		{
			Set<Integer> tmp = res.get(all2.get(i));
			for (int j = 0; j < size; j++)
			{
				if (reach[i][j])
				{
					//Set<WFState> tmp = res.get(all2.get(i));
					//Set<Integer> tmp = res.get(all2.get(i).id);
					if (tmp == null)
					{
						tmp = new HashSet<>();
						res.put(all2.get(i), tmp);
						//res.put(all2.get(i).id, tmp);
					}
					tmp.add(all2.get(j));
					//tmp.add(all2.get(j).id);
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
