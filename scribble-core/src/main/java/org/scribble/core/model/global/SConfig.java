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

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.scribble.core.model.ModelFactory;
import org.scribble.core.model.endpoint.EFsm;
import org.scribble.core.model.endpoint.EState;
import org.scribble.core.model.endpoint.EStateKind;
import org.scribble.core.model.endpoint.actions.EAcc;
import org.scribble.core.model.endpoint.actions.EAction;
import org.scribble.core.model.endpoint.actions.EDisconnect;
import org.scribble.core.model.endpoint.actions.ERecv;
import org.scribble.core.model.endpoint.actions.EReq;
import org.scribble.core.model.endpoint.actions.ESend;
import org.scribble.core.model.endpoint.actions.EClientWrap;
import org.scribble.core.model.endpoint.actions.EServerWrap;
import org.scribble.core.type.name.Role;

// Immutable -- async/sync (i.e, "fire") return updated copies (in the general case, they must return List anyway due to non-det)
public class SConfig
{
	protected final ModelFactory mf;
	
	public final Map<Role, EFsm> efsms;
	public final SingleBuffers queues;  // N.B. currently hardcoded to capacity one
	
	protected SConfig(ModelFactory mf, Map<Role, EFsm> state, SingleBuffers queues)
	{
		this.mf = mf;
		this.efsms = Collections.unmodifiableMap(state);
		this.queues = queues;
	}

	// N.B. Set<EAction> (not List<EAction), at global level only need to know the set of possible actions...
	// ...non-det (i.e., multiple possible outcomes from firing one action) handled by return of async/sync
	// Based on config semantics, not "static" graph edges (cf., super.getAllActions) -- used to build global model graph
	public Map<Role, Set<EAction>> getFireable()
	{
		Map<Role, Set<EAction>> res = new HashMap<>();
		for (Role self : this.efsms.keySet())
		{
			EFsm fsm = this.efsms.get(self);
			Set<EAction> as = new LinkedHashSet<>();
			switch (fsm.curr.getStateKind())  // Choice subject enabling needed for non-mixed states (mixed states would be needed for async. permutations though)
			{
				// Currently not convenient to delegate to states, as state types not distinguished
				case TERMINAL: break;
				case OUTPUT: as.addAll(getOutputFireable(self, fsm)); break;
				case UNARY_RECEIVE:
				case POLY_RECIEVE: 
					as.addAll(getReceiveFireable(self, fsm));  // addAll for generic typing
					break;
				case ACCEPT: as.addAll(getAcceptFireable(self, fsm)); break;
				case SERVER_WRAP: as.addAll(getServerWrapFireable(self, fsm)); break;
				default: throw new RuntimeException("Unknown state kind: " + fsm);
			}
			if (!as.isEmpty())  // Guards against res.put for empty "as", but perhaps unnecessary?
			{
				res.put(self, as);
			}
		}
		return res;
	}

	// Pre: fsm.curr.getStateKind() == EStateKind.OUTPUT
	// fsm is self's Efsm
	// (N.B. return Set, not List -- see getFireable)
	protected Set<EAction> getOutputFireable(Role self, EFsm fsm)  // "output" and "client" actions
	{
		Set<EAction> res = new LinkedHashSet<>();
		for (EAction a : fsm.curr.getActions())  // Actions may be a mixture of the following cases
		{
			// Async
			if (a.isSend() || a.isDisconnect())  // For disconnect, only one "a"
			{
				if ((a.isSend() && this.queues.canSend(self, (ESend) a))
						|| (a.isDisconnect()
								&& this.queues.canDisconnect(self, (EDisconnect) a)))
				{
					res.add(a);
				}
			}
			// Sync
			else if (a.isRequest() || a.isClientWrap())
			{
				if ((a.isRequest() && this.queues.canRequest(self, (EReq) a))
						|| (a.isClientWrap()
								&& this.queues.canClientWrap(self, (EClientWrap) a)))
				{
					if (this.efsms.get(a.peer).curr.getActions().stream()
							.anyMatch(x -> a.toDual(self).equals(x)))  // anyMatch (i.e., add "a" at most once), cf. getAcceptable
					{
						res.add(a);
					}
				}
			}
			else
			{
				throw new RuntimeException("Unknown output action kind: " + a);
			}
		}
		return res;
	}

	// Pre: fsm.curr.getStateKind() == EStateKind.UNARY_RECEIVE or POLY_RECIEVE
	// Unary or poly receive
	// (N.B. return Set, not List -- see getFireable)
	protected Set<ERecv> getReceiveFireable(Role self, EFsm fsm)
	{
		return fsm.curr.getActions().stream().map(x -> (ERecv) x)
				.filter(x -> this.queues.canReceive(self, x))
				.collect(Collectors.toSet());
	}

	// Pre: fsm.curr.getStateKind() == EStateKind.ACCEPT
	// (N.B. return Set, not List -- see getFireable)
	protected Set<EAcc> getAcceptFireable(Role self, EFsm fsm)
	{
		List<EAction> as = fsm.curr.getActions();
		Role peer = as.get(0).peer;  // All peer's the same
		List<EAction> peeras = this.efsms.get(peer).curr.getActions();
		return fsm.curr.getActions().stream().map(x -> (EAcc) x)
				.filter(x -> this.queues.canAccept(self, x)
						&& peeras.stream().anyMatch(y -> y.toDual(peer).equals(x)))
				.collect(Collectors.toSet());
	}

	// Pre: fsm.curr.getStateKind() == EStateKind.SERVER_WRAP
	// (N.B. return Set, not List -- see getFireable)
	// Duplicated from getAcceptFireable
	protected Set<EServerWrap> getServerWrapFireable(Role self, EFsm fsm)
	{
		List<EAction> as = fsm.curr.getActions();  // Actually for ServerWrap, size() == 1
		Role peer = as.get(0).peer;  // All peer's the same
		List<EAction> peeras = this.efsms.get(peer).curr.getActions();
		return fsm.curr.getActions().stream().map(x -> (EServerWrap) x)
				.filter(x -> this.queues.canServerWrap(self, x)
						&& peeras.stream().anyMatch(y -> y.toDual(peer).equals(x)))
				.collect(Collectors.toSet());
	}
	
	// Pre: getFireable().get(self).contains(a)
  // Here, produce a List that distinguishes non-det to identical configs -- CHECKME: shouldn't? (Set would be sufficient for non-det to different configs) 
	// (Actual global model building (e.g., SGraphBuilderUtil.getSuccs) will collapse identical configs)
	public List<SConfig> async(Role self, EAction a)
	{
		List<SConfig> res = new LinkedList<>();
		List<EFsm> succs = this.efsms.get(self).getSuccs(a);
		for (EFsm succ : succs)
		{
			Map<Role, EFsm> efsms = new HashMap<>(this.efsms);
			efsms.put(self, succ);
			SingleBuffers queues =  // N.B. queue updates are insensitive to non-det "a"
				  a.isSend()       ? this.queues.send(self, (ESend) a)
				: a.isReceive()    ? this.queues.receive(self, (ERecv) a)
				: a.isDisconnect() ? this.queues.disconnect(self, (EDisconnect) a)
				: null;
			if (queues == null)
			{
				throw new RuntimeException("Shouldn't get in here: " + a);
			}
			res.add(this.mf.global.SConfig(efsms, queues));
		}
		return res;
	}

	// "Synchronous version" of fire
	// Pre: getFireable().get(r1).contains(a1) && getFireable().get(r2).contains(a2), where a1 and a2 are a "sync" pair (e.g., matching message)
  // Here, produce a List that distinguishes non-det to identical configs -- CHECKME: shouldn't? (Set would be sufficient for non-det to different configs) 
	// (Actual global model building (e.g., SGraphBuilderUtil.getSuccs) will collapse identical configs)
	public List<SConfig> sync(Role r1, EAction a1, Role r2, EAction a2)
	{
		List<SConfig> res = new LinkedList<>();
		List<EFsm> succs1 = this.efsms.get(r1).getSuccs(a1);
		List<EFsm> succs2 = this.efsms.get(r2).getSuccs(a2);
		for (EFsm succ1 : succs1)
		{
			for (EFsm succ2 : succs2)
			{
				Map<Role, EFsm> efsms = new HashMap<>(this.efsms);
				// a1 and a2 are a "sync" pair, add all combinations of succ1 and succ2 that may arise
				efsms.put(r1, succ1);  // Overwrite existing r1/r2 entries
				efsms.put(r2, succ2);
				SingleBuffers queues;
				// a1 and a2 definitely "sync", now just determine whether it is a connect or wrap
				if (((a1.isRequest() && a2.isAccept())
						|| (a1.isAccept() && a2.isRequest())))
				{
					queues = this.queues.connect(r1, r2);  // N.B. queue updates are insensitive to non-det "a"
				}
				else if (((a1.isClientWrap() && a2.isServerWrap())
						|| (a1.isServerWrap() && a2.isClientWrap())))
				{
					// Doesn't affect queue state
					queues = this.queues;  // OK, immutable?
				}
				else
				{
					throw new RuntimeException("Shouldn't get in here: " + a1 + ", " + a2);
				}
				res.add(this.mf.global.SConfig(efsms, queues));
			}
		}
		return res;
	}

	/*// CHECKME: rename -- not just termination, could be unconnected/uninitiated
	public boolean isSafeTermination()
	{
		return this.efsms.keySet().stream().allMatch(x -> canSafelyTerminate(x));
	}*/

	// Should work both with and without accept-correlation?
	public boolean canSafelyTerminate(Role r)
	{
		EFsm fsm = this.efsms.get(r);
		boolean safe = this.queues.isEmpty(r) && (fsm.curr.isTerminal() || 
				(fsm.curr.getStateKind().equals(EStateKind.ACCEPT) && fsm.isInitial())); // Specifically the initial state
				
			// CHECKME: incorrectly allows stuck accepts?  if inactive not initial, should be clone of initial?
			//|| (s.getStateKind().equals(Kind.ACCEPT) && this.states.keySet().stream().noneMatch((rr) -> !r.equals(rr) && this.queues.isConnected(r, rr)))
		return safe;
	}

	// Stuck due to non-consumable messages (reception errors)
	public Map<Role, ERecv> getStuckMessages()
	{
		Map<Role, ERecv> res = new HashMap<>();
		for (Role self : this.efsms.keySet())
		{
			EFsm s = this.efsms.get(self);
			EStateKind k = s.curr.getStateKind();
			if (k == EStateKind.UNARY_RECEIVE || k == EStateKind.POLY_RECIEVE)
			{
				Role peer = s.curr.getActions().get(0).peer;  // Pre: consistent ext choice subj
				ESend send = this.queues.getQueue(self).get(peer);
				if (send != null)
				{
					ERecv recv = send.toDual(peer);
					if (!s.curr.hasAction(recv))
					{
						res.put(self, recv);
					}
				}
			}
			/*else if (k == Kind.ACCEPT)  // CHECKME: ..and request (Client/ServerWrap? -- factor out "blocking"? async. blocking vs. sync? n.b. outputs blocking on queue space)
			{
				// CHECKME: issue is, unlike regular input states, blocked connect/accept may become unblocked later, so queued messages may not actually be stuck
				// (if message is queued on the actual blocked connection, it should be orphan message)
				// so, message is stuck only if connect/accept is genuinely deadlocked, which will be detected as that
			}*/
		}
		return res;
	}
	
	// Doesn't include locally terminated (single term state does not induce a deadlock cycle)
	public Set<Set<Role>> getWaitForCycles()
	{
		Set<Set<Role>> res = new HashSet<>();
		List<Role> todo = new LinkedList<>(this.efsms.keySet());
		while (!todo.isEmpty())  // CHECKME: maybe better to do directly on states, rather than via roles -- ?
		{
			Role r = todo.remove(0);
			if (this.efsms.get(r).curr.isTerminal())
			{
				continue;
			}
			Set<Role> cycle = isWaitForCycle(r);
			if (cycle != null)
			{
				res.add(cycle);
				todo.removeAll(cycle);
			}
		}
		return res;
	}
	
	// Return: null if no cycle -- terminated peer means no cycle (cf. getUnfinishedRoles)
	// Includes connect dependencies
	// N.B. if this.states.get(init).isTerminal() then orig is returned as "singleton deadlock" -- CHECKME
	protected Set<Role> isWaitForCycle(Role init)
	{
		Set<Role> cycle = new LinkedHashSet<>();
		Set<Role> todo = new LinkedHashSet<>(Arrays.asList(init));
		while (!todo.isEmpty())
		{
			Role r = todo.iterator().next();
			todo.remove(r);
			cycle.add(r);
			Set<Role> waitingFor = getWaitingFor(r);
			if (waitingFor == null)  // "r" is not necessarily waiting for anyone, so report no cycle
			{
				return null;
			}
			waitingFor.stream().filter(x -> !cycle.contains(x))  // Only more than one when client-sync cases
					.forEachOrdered(x -> todo.add(x));  // todo is a Set, so re-add existing doesn't matter
		}
		return cycle;
	}
	
	// Return: set of roles that "r" *must* wait for one of them in order to proceed;.. 
	// ..or null if "r" is not *necessarily* blocked waiting for *another role*
	// Wait-for blocking includes sync-client (request, clientWrap) roles
	protected Set<Role> getWaitingFor(Role r)
	{
		EFsm fsm = this.efsms.get(r);
		EStateKind k = fsm.curr.getStateKind();
		if (k == EStateKind.TERMINAL) 
		{
			return null;
		}

		if (k == EStateKind.UNARY_RECEIVE || k == EStateKind.POLY_RECIEVE)
		{
			ERecv a = (ERecv) fsm.curr.getActions().get(0);  // Pre: consistent ext choice subject -- CHECKME: generalise?
			if (this.queues.getQueue(r).get(a.peer) != null)  // Here, only looking for any message (not a.toDual, nor dual of any action, cf. stuck error)
			{
				return null;
			}
			return Stream.of(a.peer).collect(Collectors.toSet());
		}
		else if (k == EStateKind.ACCEPT)
		{
			if (fsm.isInitial())  // TODO CHECKME: if analysing ACCEPTs, check if "s" is initial (not "deadlock blocked" if initial) -- no: instead, analysing requests
			{
				return null;
			}
			List<EAction> as = fsm.curr.getActions();  // All accept
			Role peer = as.get(0).peer;  // Pre: consistent ext choice subject -- CHECKME: generalise?
			if (this.efsms.get(peer).curr.getActions().stream()
					.anyMatch(x -> as.contains(x.toDual(peer))))
			{
				return null;
			}
			return Stream.of(peer).collect(Collectors.toSet());
		}
		else if (k == EStateKind.OUTPUT) //|| k == Kind.ACCEPT .. // CHECKME: check requests if no available sends -- ?
		{
			if (!fsm.curr.isSyncClientOnly())  // Request, ClientWrap
			{
				return null;
			}
			List<EAction> as = fsm.curr.getActions();
			Set<Role> res = new HashSet<Role>();
			for (EAction a : as)
			{
				if (this.efsms.get(a.peer).curr.getActions().contains(a.toDual(r)))
				{
					return null;
				}
				res.add(a.peer);
			}
			return res;  // Non-empty, because as.size() > 0 and didn't already return
		}
		// TODO FIXME: ServerWrap
		throw new RuntimeException("[TODO] " + k);
	}

	// Includes "unconnected" messages -- CHECKME: should unconnected messages be considered as "stuck" instead?
	public Map<Role, Set<ESend>> getOrphanMessages()
	{
		Map<Role, Set<ESend>> res = new HashMap<>();
		for (Role r : this.efsms.keySet())
		{
			Set<ESend> orphs = new HashSet<>();
			EFsm fsm = this.efsms.get(r);
			if (fsm.curr.isTerminal())  // Local termination of r, i.e. not necessarily "full deadlock cycle"
			{
				orphs.addAll(this.queues.getQueue(r).values().stream()
						.filter(v -> v != null).collect(Collectors.toSet()));
			}
			else
			{
				this.efsms.keySet().stream()
						.filter(x -> !r.equals(x) && !this.queues.isConnected(r, x))  // !isConnected(r, x), means r considers its side closed
						.map(x -> this.queues.getQueue(r).get(x)).filter(x -> x != null)  // r's side is closed, but remaining message(s) in r's buff
						.forEachOrdered(x -> orphs.add(x));
			}
			if (!orphs.isEmpty())
			{
				res.put(r, orphs);
			}
		}
		return res;
	}
	
	// Not just "unfinished", but also "non-initiated" (accept guarded) -- though could be non-initiated after some previous completions
	// Maybe not needed? previously not used (even without accept-correlation check)
	public Map<Role, EState> getUnfinishedRoles()
	{
		if (!getFireable().isEmpty())  
				// Once no fireable, then finished (no further fireable will be produced)
				// N.B. must check getFireable -- cf. terminal state kind is only when no actions, does not include when actions are stuck
		{
			return Collections.emptyMap();
		}
		return this.efsms.entrySet().stream()
				.filter(x -> !canSafelyTerminate(x.getKey()))
				.collect(Collectors.toMap(Entry::getKey, x -> x.getValue().curr));
	}
	
	@Override
	public String toString()
	{
		return "(" + this.efsms + ", " + this.queues + ")";
	}

	@Override
	public int hashCode()
	{
		int hash = 71;
		hash = 31 * hash + this.efsms.hashCode();
		hash = 31 * hash + this.queues.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof SConfig))
		{
			return false;
		}
		SConfig c = (SConfig) o;
		return this.efsms.equals(c.efsms) && this.queues.equals(c.queues);
	}
}
