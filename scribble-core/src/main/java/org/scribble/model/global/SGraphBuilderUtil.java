/**
 * Copyright 2008 The Scribble Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.scribble.model.global;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.scribble.main.Job;
import org.scribble.main.ScribbleException;
import org.scribble.model.GraphBuilderUtil;
import org.scribble.model.endpoint.EFSM;
import org.scribble.model.endpoint.EGraph;
import org.scribble.model.endpoint.EModelFactory;
import org.scribble.model.endpoint.EStateKind;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.model.global.actions.SAction;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;

public class SGraphBuilderUtil extends GraphBuilderUtil<Void, SAction, SState, Global>
{
	public final SModelFactory sf;

	protected SGraphBuilderUtil(SModelFactory sf)
	{
		this.sf = sf;
		reset();
	}
	
	public void reset()
	{
		init(this.sf.newSState(null), this.sf.newSState(null));  // configs
	}

	// Do as an initial state rather than config?
	protected SConfig createInitialSConfig(EModelFactory ef, Map<Role, EGraph> egraphs, boolean explicit)  // FIXME: ef
	{
		Map<Role, EFSM> efsms = egraphs.entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> e.getValue().toFsm()));
		SBuffers b0 = new SBuffers(ef, efsms.keySet(), !explicit);
		//return job.sf.newSConfig(efsms, b0);
		return this.sf.newSConfig(efsms, b0);
	}
	
	// Factory method: not fully integrated with SGraph constructor because of Job arg (debug printing)
	// Also checks for non-deterministic payloads
	// Maybe refactor into an SGraph builder util; cf., EGraphBuilderUtil -- but not Visitor-based building (cf. EndpointGraphBuilder), this isn't an AST algorithm
	//public SGraph buildSGraph(Job job, GProtocolName fullname, SConfig c0) throws ScribbleException
	public SGraph buildSGraph(Job job, GProtocolName fullname, Map<Role, EGraph> egraphs, boolean explicit) throws ScribbleException
	{
		/*Map<Role, EFSM> efsms = egraphs.entrySet().stream().collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().toFsm()));

		SBuffers b0 = new SBuffers(job.ef, efsms.keySet(), !explicit);*/

		//SConfig c0 = job.sf.newSConfig(efsms, b0);
		SConfig c0 = createInitialSConfig(job.ef, egraphs, explicit);

		SState init = job.sf.newSState(c0);

		Map<Integer, SState> seen = new HashMap<>();
		LinkedHashSet<SState> todo = new LinkedHashSet<>();
		todo.add(init);

		// FIXME: factor out model building and integrate with getAllNodes (seen == all)
		int count = 0;
		while (!todo.isEmpty())
		{
			Iterator<SState> i = todo.iterator();
			SState curr = i.next();
			i.remove();
			seen.put(curr.id, curr);

			if (job.debug)
			{
				count++;
				if (count % 50 == 0)
				{
					job.debugPrintln("(" + fullname + ") Building global states: " + count);
				}
			}
			
			Map<Role, List<EAction>> fireable = curr.getFireable();

			//job.debugPrintln("Acceptable at (" + curr.id + "): " + acceptable);

			for (Role r : fireable.keySet())
			{
				List<EAction> fireable_r = fireable.get(r);
				
				// Hacky?  // FIXME: factor out and make more robust (e.g. for new state kinds) -- e.g. "hasPayload" in IOAction
				//EndpointState currstate = curr.config.states.get(r);
				EFSM currfsm = curr.config.efsms.get(r);
				EStateKind k = currfsm.getStateKind();
				if (k == EStateKind.OUTPUT)
				{
					for (EAction a : fireable_r)  // Connect implicitly has no payload (also accept, so skip)
					{
						if (fireable_r.stream().anyMatch((x) ->
								!a.equals(x) && a.peer.equals(x.peer) && a.mid.equals(x.mid) && !a.payload.equals(x.payload)))
						{
							throw new ScribbleException("Bad non-deterministic action payloads: " + fireable_r);
						}
					}
				}
				else if (k == EStateKind.UNARY_INPUT || k == EStateKind.POLY_INPUT || k == EStateKind.ACCEPT)
				{
					for (EAction a : fireable_r)
					{
						if (currfsm.getAllFireable().stream().anyMatch((x) ->
								!a.equals(x) && a.peer.equals(x.peer) && a.mid.equals(x.mid) && !a.payload.equals(x.payload)))
						{
							throw new ScribbleException("Bad non-deterministic action payloads: " + currfsm.getAllFireable());
						}
					}
				}
			}  // Need to do all action payload checking before next building step, because doing sync actions will also remove peer's actions from takeable set

			for (Role r : fireable.keySet())
			{
				List<EAction> fireable_r = fireable.get(r);
				
				for (EAction a : fireable_r)
				{
					if (a.isSend() || a.isReceive() || a.isDisconnect())
					{
						getNextStates(job.sf, todo, seen, curr, a.toGlobal(job.sf, r), curr.fire(r, a));
					}
					else if (a.isAccept() || a.isConnect())
					{	
						List<EAction> as = fireable.get(a.peer);
						EAction d = a.toDual(r);
						if (as != null && as.contains(d))
						{
							as.remove(d);  // Removes one occurrence
							//getNextStates(seen, todo, curr.sync(r, a, a.peer, d));
							SAction g = (a.isConnect()) ? a.toGlobal(job.sf, r) : d.toGlobal(job.sf, a.peer);  // Edge will be drawn as the connect, but should be read as the sync. of both -- something like "r1, r2: sync" may be more consistent (or take a set of actions as the edge label)
							getNextStates(job.sf, todo, seen, curr, g, curr.sync(r, a, a.peer, d));
						}
					}
					else if (a.isWrapClient() || a.isWrapServer())
					{
						List<EAction> as = fireable.get(a.peer);
						EAction w = a.toDual(r);
						if (as != null && as.contains(w))
						{
							as.remove(w);  // Removes one occurrence
							SAction g = (a.isConnect()) ? a.toGlobal(job.sf, r) : w.toGlobal(job.sf, a.peer);
							getNextStates(job.sf, todo, seen, curr, g, curr.sync(r, a, a.peer, w));
						}
					}
					else
					{
						throw new RuntimeException("Shouldn't get in here: " + a);
					}
				}
			}
		}

		SGraph graph = job.sf.newSGraph(fullname, seen, init);

		job.debugPrintln("(" + fullname + ") Built global model..\n" + graph.init.toDot() + "\n(" + fullname + ") .." + graph.states.size() + " states");

		return graph;
	}

	private void getNextStates(SModelFactory sf, LinkedHashSet<SState> todo,
			Map<Integer, SState> seen, SState curr, SAction a, List<SConfig> nexts)
	{
		for (SConfig next : nexts)
		{
			SState news = sf.newSState(next);
			SState succ = null; 
			//if (seen.contains(succ))  // FIXME: make a SGraph builder
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
			for (SState tmp : seen.values())  // Key point: checking "semantically" if model state already created
			{
				if (tmp.equals(news))
				{
					succ = tmp;
				}
			}
			if (succ == null)
			{
				for (SState tmp : todo)  // If state created but not "seen" yet, then it will be "todo"
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
			curr.addEdge(a, succ);  // FIXME: make a Builder util, cf. EGraphBuilderUtil
			//if (!seen.contains(succ) && !todo.contains(succ))
			/*if (!seen.containsKey(succ.id) && !todo.contains(succ))
			{
				todo.add(succ);
			}*/
		}
	}
}
