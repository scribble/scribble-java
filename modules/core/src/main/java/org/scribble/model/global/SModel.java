package org.scribble.model.global;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.model.endpoint.EFSM;
import org.scribble.model.endpoint.actions.ESend;
import org.scribble.model.global.actions.SAction;
import org.scribble.sesstype.name.Role;

public class SModel
{
	public final SGraph graph;

	public SModel(SGraph graph)
	{
		this.graph = graph;
	}

	public void validate(Job job) throws ScribbleException
	{
		SState init = this.graph.init;
		Map<Integer, SState> all = this.graph.states;

		String errorMsg = "";

		int count = 0;
		for (SState s : all.values())
		{
			if (job.debug)
			{
				count++;
				if (count % 50 == 0)
				{
					job.debugPrintln("(" + this.graph.proto + ") Checking global states: " + count);
				}
			}
			SStateErrors errors = s.getErrors();
			if (!errors.isEmpty())
			{
				// FIXME: getTrace can get stuck when local choice subjects are disabled
				List<SAction> trace = this.graph.getTrace(init, s);  // FIXME: getTrace broken on non-det self loops?
				//errorMsg += "\nSafety violation(s) at " + s.toString() + ":\n    Trace=" + trace;
				errorMsg += "\nSafety violation(s) at " + s.id + ":\n    Trace=" + trace;
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
			if (!errors.unfinished.isEmpty())
			{
				errorMsg += "\n    Unfinished roles: " + errors.unfinished;
			}
		}
		job.debugPrintln("(" + this.graph.proto + ") Checked all states: " + count);
		//*/
		
		if (!job.noLiveness)
		{
			Set<Set<Integer>> termsets = this.graph.getTerminalSets();
			//findTerminalSets(all, reach, termsets);

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
					errorMsg += "\nSafety violation for " + safety + " in terminal set:\n    " + termSetToString(job, termset, all);
				}
				if (!roleLiveness.isEmpty())
				{
					errorMsg += "\nRole progress violation for " + roleLiveness + " in terminal set:\n    " + termSetToString(job, termset, all);
				}
				Map<Role, Set<ESend>> msgLiveness = checkMessageLiveness(all, init, termset);
				if (!msgLiveness.isEmpty())
				{
					errorMsg += "\nMessage liveness violation for " + msgLiveness + " in terminal set:\n    " + termSetToString(job, termset, all);
				}
			}
		}
		
		if (!errorMsg.equals(""))
		{
			//throw new ScribbleException("\n" + init.toDot() + errorMsg);
			throw new ScribbleException(errorMsg);
		}
	}
	
	private String termSetToString(Job job, Set<Integer> termset, Map<Integer, SState> all)
	{
		return job.debug
				? termset.stream().map((i) -> all.get(i).toString()).collect(Collectors.joining(","))
				: termset.stream().map((i) -> new Integer(all.get(i).id).toString()).collect(Collectors.joining(","));
	}

	// FIXME: this is now just "role liveness"
	// ** Could subsume terminal state check, if terminal sets included size 1 with reflexive reachability (but probably not good)
	//private static void checkTerminalSet(WFState init, Set<WFState> termset, Set<Role> safety, Set<Role> liveness) throws ScribbleException
	private static void checkTerminalSet(Map<Integer, SState> all, SState init, Set<Integer> termset, Set<Role> safety, Set<Role> liveness) throws ScribbleException
	{
		Iterator<Integer> i = termset.iterator();
		SState s = all.get(i.next());
		Map<Role, SState> ss = new HashMap<>();
		s.config.states.keySet().forEach((r) -> ss.put(r, s));
		while (i.hasNext())
		{
			SState next = all.get(i.next());
			Map<Role, EFSM> tmp = next.config.states;
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
						for (SAction a : next.getAllActions())
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
			SState foo = ss.get(r);
			if (foo != null)
			{
				EFSM tmp = foo.config.states.get(r);
				if (tmp != null)
				{
					if (!foo.config.canSafelyTerminate(r))
					{
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
	private static Map<Role, Set<ESend>> checkMessageLiveness(Map<Integer, SState> all, SState init, Set<Integer> termset) throws ScribbleException
	{
		Set<Role> roles = all.get(termset.iterator().next()).config.states.keySet();

		Iterator<Integer> i = termset.iterator();
		Map<Role, Map<Role, ESend>> b0 = all.get(i.next()).config.buffs.getBuffers();
		while (i.hasNext())
		{
			SState s = all.get(i.next());
			SBuffers b = s.config.buffs;
			for (Role r1 : roles)
			{
				for (Role r2 : roles)
				{
					ESend s0 = b0.get(r1).get(r2);
					if (s0 != null)
					{
						ESend tmp = b.get(r1).get(r2);
						if (tmp == null)
						{
							b0.get(r1).put(r2, null);
						}
					}
				}
			}
		}
	
		Map<Role, Set<ESend>> res = new HashMap<>();
		for (Role r1 : roles)
		{
			for (Role r2 : roles)
			{
				ESend m = b0.get(r1).get(r2);
				if (m != null)
				{
					Set<ESend> tmp = res.get(r2);
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
	
}
