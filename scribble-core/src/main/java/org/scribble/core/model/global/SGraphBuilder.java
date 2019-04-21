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
package org.scribble.core.model.global;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.core.job.Core;
import org.scribble.core.job.CoreArgs;
import org.scribble.core.model.endpoint.EFsm;
import org.scribble.core.model.endpoint.EGraph;
import org.scribble.core.model.endpoint.EStateKind;
import org.scribble.core.model.endpoint.actions.EAction;
import org.scribble.core.model.global.actions.SAction;
import org.scribble.core.type.name.GProtoName;
import org.scribble.core.type.name.Role;
import org.scribble.util.ScribException;

public class SGraphBuilder
{
	public final Core core;
	
	private final SGraphBuilderUtil util;
	
	public SGraphBuilder(Core core)
	{
		this.core = core;
		this.util = this.core.config.mf.newSGraphBuilderUtil();
	}

	// Do as an initial state rather than config?
	protected SConfig createInitialSConfig(Map<Role, EGraph> egraphs,
			boolean explicit)
	{
		Map<Role, EFsm> efsms = egraphs.entrySet().stream()
				.collect(Collectors.toMap(Entry::getKey, e -> e.getValue().toFsm()));
		SBuffers b0 = new SBuffers(this.core.config.mf, efsms.keySet(), !explicit);
		return this.core.config.mf.newSConfig(efsms, b0);
	}
	
	// Factory method: not fully integrated with SGraph constructor because of Job arg (debug printing)
	// Also checks for non-deterministic payloads
	// Maybe refactor into an SGraph builder util; cf., EGraphBuilderUtil -- but not Visitor-based building (cf. EndpointGraphBuilder), this isn't an AST algorithm
	//public SGraph buildSGraph(Job job, GProtocolName fullname, SConfig c0) throws ScribbleException
	public SGraph buildSGraph(GProtoName fullname,
			Map<Role, EGraph> egraphs, boolean explicit) throws ScribException
	{
		SConfig c0 = createInitialSConfig(egraphs, explicit);

		SState init = this.util.newState(c0);

		Set<SState> todo = new LinkedHashSet<>();
		todo.add(init);

		// FIXME: factor out model building and integrate with getAllNodes (seen == all)
		int count = 1;
		while (!todo.isEmpty())
		{
			Iterator<SState> i = todo.iterator();
			SState curr = i.next();
			i.remove();

			if (this.core.config.args.get(CoreArgs.VERBOSE))
			{
				if (count++ % 50 == 0)
				{
					this.core.verbosePrintln("(" + fullname + ") Building global states: " + count);
				}
			}
			
			Map<Role, List<EAction>> fireable = curr.getFireable();
			for (Role r : fireable.keySet())
			{
				List<EAction> fireable_r = fireable.get(r);
				
				// Hacky?  // FIXME: factor out and make more robust (e.g. for new state kinds) -- e.g. "hasPayload" in IOAction
				EFsm currfsm = curr.config.efsms.get(r);
				EStateKind k = currfsm.getStateKind();
				if (k == EStateKind.OUTPUT)
				{
					for (EAction a : fireable_r)  // Connect implicitly has no payload (also accept, so skip)
					{
						if (fireable_r.stream().anyMatch(x ->
								!a.equals(x) && a.peer.equals(x.peer) && a.mid.equals(x.mid) && !a.payload.equals(x.payload)))
						{
							throw new ScribException("Bad non-deterministic action payloads: " + fireable_r);
						}
					}
				}
				else if (k == EStateKind.UNARY_INPUT || k == EStateKind.POLY_INPUT || k == EStateKind.ACCEPT)
				{
					for (EAction a : fireable_r)
					{
						// curr.getFireable vs currfsm.getAllFireable ?
						if (currfsm.getActions().stream().anyMatch(x ->
								!a.equals(x) && a.peer.equals(x.peer) && a.mid.equals(x.mid) && !a.payload.equals(x.payload)))
						{
							throw new ScribException("Bad non-deterministic action payloads: " + currfsm.getActions());
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
						//getNextStates(todo, seen, curr, a.toGlobal(r), curr.fire(r, a));
						todo.addAll(
								this.util.getSuccs(curr, a.toGlobal(r), curr.fire(r, a)));
					}
					else if (a.isAccept() || a.isRequest())
					{	
						List<EAction> as = fireable.get(a.peer);
						EAction d = a.toDual(r);
						if (as != null && as.contains(d))
						{
							as.remove(d);  // Removes one occurrence
							//getNextStates(seen, todo, curr.sync(r, a, a.peer, d));
							SAction g = (a.isRequest()) 
									? a.toGlobal(r)
									: d.toGlobal(a.peer);
									// Edge will be drawn as the connect, but should be read as the sync. of both -- something like "r1, r2: sync" may be more consistent (or take a set of actions as the edge label)
							//getNextStates(todo, seen, curr, g, curr.sync(r, a, a.peer, d));
							todo.addAll(
									this.util.getSuccs(curr, g, curr.sync(r, a, a.peer, d)));
						}
					}
					else if (a.isClientWrap() || a.isServerWrap())
					{
						List<EAction> as = fireable.get(a.peer);
						EAction w = a.toDual(r);
						if (as != null && as.contains(w))
						{
							as.remove(w);  // Removes one occurrence
							SAction g = (a.isRequest()) 
									? a.toGlobal(r)
									: w.toGlobal(a.peer);
							//getNextStates(todo, seen, curr, g, curr.sync(r, a, a.peer, w));
							todo.addAll(
									this.util.getSuccs(curr, g, curr.sync(r, a, a.peer, w)));
						}
					}
					else
					{
						throw new RuntimeException("Shouldn't get in here: " + a);
					}
				}
			}
		}

		SGraph graph = this.core.config.mf.newSGraph(fullname,
				this.util.getStates(), init);

		this.core.verbosePrintln(
				"(" + fullname + ") Built global model...\n" + graph.init.toDot() + "\n("
						+ fullname + ") ..." + graph.states.size() + " states");

		return graph;
	}
}
