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
import java.util.Set;
import java.util.stream.Collectors;

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

public class SConfig
{
	protected final ModelFactory mf;
	
	public final Map<Role, EFsm> efsms;
	public final SQueues queues;
	
	//public WFConfig(Map<Role, EndpointState> state, Map<Role, Map<Role, Send>> queue)
	//public WFConfig(Map<Role, EndpointState> state, WFqueueers queues)
	public SConfig(ModelFactory mf, Map<Role, EFsm> state, SQueues queues)
	{
		this.mf = mf;
		
		this.efsms = Collections.unmodifiableMap(state);
		//this.queues = Collections.unmodifiableMap(queue.keySet().stream() .collect(Collectors.toMap((k) -> k, (k) -> Collections.unmodifiableMap(queue.get(k)))));
		//this.queues = Collections.unmodifiableMap(queue);
		this.queues = queues;
	}

	// FIXME: rename: not just termination, could be unconnected/uninitiated
	//public boolean isEnd()
	public boolean isSafeTermination()
	{
		//return this.states.values().stream().allMatch((s) -> s.isTerminal()) && this.queues.isEmpty();
		for (Role r : this.efsms.keySet())
		{
			if (!canSafelyTerminate(r))
			{
				return false;
			}
		}
		return true;
	}

	// Should work both with and without accept-correlation?
	public boolean canSafelyTerminate(Role r)
	{
		//EndpointState s = this.states.get(r);
		EFsm s = this.efsms.get(r);
		//return
		/*boolean cannotSafelyTerminate =  // FIXME: check and cleanup
				(s.isTerminal() && !this.queues.isEmpty(r))
					||
					(!s.isTerminal() &&
						//(!(s.getStateKind().equals(Kind.UNARYINPUT) && s.getTakeable().iterator().next().isAccept())  // Accept state now distinguished
						(
							!(s.getStateKind().equals(Kind.ACCEPT) && s.isInitial())  // FIXME: check stable
								// FIXME: needs initial state check -- although if there is an accept, there should a connect, and waitfor-errors checked via connects) -- this should be OK because connect/accept are sync -- but not fully sufficient by itself, see next
								// So could be blocked on unary accept part way through the protocol -- but also could be unfolded initial accept
							////|| this.states.keySet().stream().anyMatch((rr) -> !r.equals(rr) && this.queues.isConnected(r, rr))))
							//&& this.states.keySet().stream().anyMatch((rr) -> !r.equals(rr) && this.queues.isConnected(r, rr))
									// FIXME: isConnected is not symmetric, and could disconnect all part way through protocol -- but can't happen?
							// Above assumes initial is not terminal (holds for EFSMs), and doesn't check queueer is empty (i.e. for orphan messages)
						)
					)
					||
					(!s.isTerminal() && this.states.keySet().stream().anyMatch((rr) -> !r.equals(rr) && this.queues.isConnected(r, rr)))
					;*/
		//return !cannotSafelyTerminate;
		boolean canSafelyTerminate =
				(s.curr.isTerminal() && this.queues.isEmpty(r))
						|| (s.curr.getStateKind().equals(EStateKind.ACCEPT)
								&& s.isInitial())
					// FIXME: should be empty queues
				
				// FIXME: incorrectly allows stuck accepts?  if inactive not initial, should be clone of initial?
			//|| (s.getStateKind().equals(Kind.ACCEPT) && this.states.keySet().stream().noneMatch((rr) -> !r.equals(rr) && this.queues.isConnected(r, rr)))
			;
		return canSafelyTerminate;
	}
	
	// Pre: getFireable().get(self).contains(a)
	public List<SConfig> async(Role self, EAction a)
	{
		List<SConfig> res = new LinkedList<>();
		List<EFsm> succs = this.efsms.get(self).getSuccs(a);
		for (EFsm succ : succs)
		{
			Map<Role, EFsm> efsms = new HashMap<>(this.efsms);
			efsms.put(self, succ);
			SQueues queues = 
				  a.isSend()       ? this.queues.send(self, (ESend) a)
				: a.isReceive()    ? this.queues.receive(self, (ERecv) a)
				: a.isDisconnect() ? this.queues.disconnect(self, (EDisconnect) a)
				: null;
			if (queues == null)
			{
				throw new RuntimeException("Shouldn't get in here: " + a);
			}
			res.add(this.mf.newSConfig(efsms, queues));
		}
		return res;
	}

	// "Synchronous version" of fire
	// Pre: getFireable().get(r1).contains(a1) && getFireable().get(r2).contains(a2), where a1 and a2 are a "sync" pair
	public List<SConfig> sync(Role r1, EAction a1, Role r2, EAction a2)
	{
		List<SConfig> res = new LinkedList<>();
		List<EFsm> succs1 = this.efsms.get(r1).getSuccs(a1);
		List<EFsm> succs2 = this.efsms.get(r2).getSuccs(a2);
		for (EFsm succ1 : succs1)
		{
			for (EFsm succ2 : succs2)
			{
				Map<Role, EFsm> tmp1 = new HashMap<>(this.efsms);
				tmp1.put(r1, succ1);
				tmp1.put(r2, succ2);
				SQueues tmp2;
				if (((a1.isRequest() && a2.isAccept()) || (a1.isAccept() && a2.isRequest())))
				{
					tmp2 = this.queues.connect(r1, r2);
				}
				else if (((a1.isClientWrap() && a2.isServerWrap()) || (a1.isServerWrap() && a2.isClientWrap())))
				{
					// Doesn't affect queue state
					tmp2 = this.queues;  // OK, immutable?
				}
				else
				{
					throw new RuntimeException("Shouldn't get in here: " + a1 + ", " + a2);
				}
				res.add(this.mf.newSConfig(tmp1, tmp2));
			}
		}
		return res;
	}

	// Deadlock from non handle-able messages (reception errors)
	public Map<Role, ERecv> getStuckMessages()
	{
		Map<Role, ERecv> res = new HashMap<>();
		for (Role r : this.efsms.keySet())
		{
			//EndpointState s = this.states.get(r);
			EFsm s = this.efsms.get(r);
			EStateKind k = s.curr.getStateKind();
			if (k == EStateKind.UNARY_INPUT || k == EStateKind.POLY_INPUT)
			{
				/*Set<IOAction> duals = this.queues.get(r).entrySet().stream()
						.filter((e) -> e.getValue() != null)
						.map((e) -> e.getValue().toDual(e.getKey()))
						.collect(Collectors.toSet());
				if (duals.stream().anyMatch((a) -> s.isAcceptable(a)))
				{
					break;
				}*/
				Role peer = s.curr.getActions().iterator().next().peer;
				ESend send = this.queues.getQueue(r).get(peer);
				if (send != null)
				{
					ERecv recv = send.toDual(peer);
					if (!s.curr.hasAction(recv))
					//res.put(r, new IOError(peer));
					res.put(r, recv);
				}
			}
			/*else if (k == Kind.ACCEPT)  // FIXME: ..and connect
			{
				// FIXME: issue is, unlike regular input states, blocked connect/accept may become unblocked later, so queued messages may not be stuck
				// (if message is queued on the actual blocked connection, it should be orphan message)
				// so, message is stuck only if connect/accept is genuinely deadlocked, which will be detected as that
			}*/
		}
		return res;
	}
	
	// Doesn't include locally terminated (single term state does not induce a deadlock cycle) -- i.e. only "bad" deadlocks
	public Set<Set<Role>> getWaitForErrors()
	{
		Set<Set<Role>> res = new HashSet<>();
		List<Role> todo = new LinkedList<>(this.efsms.keySet());
		/*while (!todo.isEmpty())
		{
			Role r = todo.get(0);
			todo.remove(r);
			Set<Role> seen = new HashSet<>();
			while (true)
			{
				if (seen.contains(r))
				{
					res.add(seen);
					break;
				}
				seen.add(r);
				Role rr = isInputBlocked(r);
				if (rr == null)
				{
					break;
				}
				todo.remove(rr);
				if (this.states.get(rr).isTerminal())
				{
					seen.add(rr);
					res.add(seen);
					break;
				}
				r = rr;
			}
		}*/
		while (!todo.isEmpty())  // FIXME: maybe better to do directly on states, rather than via roles
		{
			Role r = todo.remove(0);
			//Set<Role> cycle = isCycle(new HashSet<>(), new HashSet<>(Arrays.asList(r)));
			if (!this.efsms.get(r).curr.isTerminal())
			{
				Set<Role> cycle = isWaitForChain(r);
				//if (!cycle.isEmpty())
				if (cycle != null)
				{
					todo.removeAll(cycle);
					res.add(cycle);
				}
			}
		}
		return res;
	}
	
	// Includes dependencies from input-blocking, termination and connect-blocking
	// FIXME: should also include connect?
	// NB: if this.states.get(orig).isTerminal() then orig is returned as "singleton deadlock"
	//public Set<Role> isCycle(Set<Role> candidate, Set<Role> todo)
	public Set<Role> isWaitForChain(Role orig)
	{
		/*if (todo.isEmpty())
		{
			return candidate;
		}*/
		/*Set<Role> tmp = new HashSet<Role>(todo);
		Role r = tmp.iterator().next();
		tmp.remove(r);
		candidate.add(r);*/
		Set<Role> candidate = new LinkedHashSet<>();
		Set<Role> todo = new LinkedHashSet<>(Arrays.asList(orig));
		while (!todo.isEmpty())
		{
			Role r = todo.iterator().next();
			todo.remove(r);
			candidate.add(r);
			
			//EndpointState s = this.states.get(r);
			EFsm s = this.efsms.get(r);
			
			if (s == null)
			{
				System.out.println("AAA: " + this.efsms + ", " + r);
			}
			
			if (s.curr.getStateKind() == EStateKind.OUTPUT && !s.curr.isRequestOrClientWrapOnly())  // FIXME: includes connect, could still be deadlock? -- no: doesn't include connect any more
			{
				// FIXME: move into isWaitingFor
				return null;
			}
			if (s.curr.isTerminal())
			{
				if (todo.isEmpty())
				{
					return candidate;
				}
				continue;
			}
			Set<Role> blocked = isWaitingFor(r);
			//if (blocked.isEmpty())
			if (blocked == null)
			{
				return null;
			}
			if (todo.isEmpty() && candidate.containsAll(blocked))
			{
				return candidate;
			}
			blocked.forEach((x) ->
			{
				if (!candidate.contains(x))
				{
					//candidate.add(x);
					todo.add(x);
				}
			});
		}
		return null;
	}
	
	// Generalised to include connect-blocked roles
	//private Role isInputBlocked(Role r)
	private Set<Role> isWaitingFor(Role r)
	{
		//EndpointState s = this.states.get(r);
		EFsm s = this.efsms.get(r);
		EStateKind k = s.curr.getStateKind();
		if (k == EStateKind.UNARY_INPUT || k == EStateKind.POLY_INPUT)
		{
			List<EAction> all = s.curr.getActions();
			EAction a = all.get(0);  // FIXME: assumes single choice subject (OK for current syntax, but should generalise)
			/*if (a.isAccept())  // Sound?
			{
				return null;
			}*/
			/*Role peer = a.peer;
			if (a.isReceive() && this.queues.get(r).get(peer) == null)
			{
				//return peer;
			}*/
			if (a.isReceive())
			{
				Set<Role> peers = all.stream().map((x) -> x.peer).collect(Collectors.toSet());
				if (peers.stream().noneMatch((p) -> this.queues.getQueue(r).get(p) != null))
				{
					return peers;
				}
				/*Set<Role> peers = new HashSet<>();  // Debugging AllTest/BadTest bad.efsm.grecursion.unfair.Test01; problem
				for (EAction ea : all)
				{
					peers.add(ea.peer);
				}
				boolean tmp = true;
				for (Role p : peers)
				{
					if (this.queues.get(r).get(p) != null)
					{
						tmp = false;
						break;
					}
				}
				if (tmp)
				{
					return peers;
				}*/
			}
		}
		else if (k == EStateKind.ACCEPT)
		{
			// FIXME TODO: if analysing ACCEPTs, check if s is initial (not "deadlock blocked" if initial) -- no: instead, analysing connects
			if (!s.isInitial())
			{
				List<EAction> all = s.curr.getActions();  // Should be singleton -- no: not any more
				/*Set<Role> rs = all.stream().map((x) -> x.peer).collect(Collectors.toSet());
				if (rs.stream().noneMatch((x) -> this.states.get(x).getAllTakeable().contains(new Connect(r))))  // cf. getTakeable
									//if (peera.equals(c.toDual(r)) && this.queues.canConnect(r, c))
				{
					return rs;
				}*/
				Set<Role> res = new HashSet<Role>();
				for (EAction a : all)  // Accept  // FIXME: WrapServer
				{
					if (this.efsms.get(a.peer).curr.getActions().contains(a.toDual(r)))
					{
						return null;
					}
					res.add(a.peer);
				}
				if (!res.isEmpty())
				{
					return res;
				}
			}
		}
		//else if (k == Kind.CONNECTION)
		else if (k == EStateKind.OUTPUT //|| k == Kind.ACCEPT  ..// FIXME: check connects if no available sends
				)
		{
			//List<IOAction> all = s.getAllAcceptable();
			if (s.curr.isRequestOrClientWrapOnly())
			{
				List<EAction> all = s.curr.getActions();
				/*Set<Role> peers = all.stream().map((x) -> x.peer).collect(Collectors.toSet());  // Should be singleton by enabling conditions
				if (peers.stream().noneMatch((p) -> this.states.get(p).getAllTakeable().contains(new Accept(r))))  // cf. getTakeable
				{
					return peers;
				}*/
				Set<Role> res = new HashSet<Role>();
				for (EAction a : all)  // Connect or WrapClient
				{
					if (this.efsms.get(a.peer).curr.getActions().contains(a.toDual(r)))
					{
						return null;
					}
					res.add(a.peer);
				}
				if (!res.isEmpty())
				{
					return res;
				}
			}
		}
		return null;
		//return Collections.emptySet();
	}

	// Generalised to include "unconnected" messages -- should unconnected messages be treated via stuck instead?
	public Map<Role, Set<ESend>> getOrphanMessages()
	{
		Map<Role, Set<ESend>> res = new HashMap<>();
		for (Role r : this.efsms.keySet())
		{
			//EndpointState s = this.states.get(r);
			EFsm s = this.efsms.get(r);
			if (s.curr.isTerminal())  // Local termination of r, i.e. not necessarily "full deadlock"
			{
				Set<ESend> orphs = this.queues.getQueue(r).values().stream().filter(v -> v != null).collect(Collectors.toSet());
				if (!orphs.isEmpty())
				{
					Set<ESend> tmp = res.get(r);
					if (tmp == null)
					{
						tmp = new HashSet<>();
						res.put(r, tmp);
					}
					tmp.addAll(orphs);
				}
			}
			else
			{
				this.efsms.keySet().forEach((rr) ->
				{
					if (!rr.equals(r))
					{
						// Connection direction doesn't matter? -- wrong: matters because of async. disconnect
						if (!this.queues.isConnected(r, rr))
						{
							ESend send = this.queues.getQueue(r).get(rr);
							if (send != null)
							{
								Set<ESend> tmp = res.get(r);
								if (tmp == null)
								{
									tmp = new HashSet<>();
									res.put(r, tmp);
								}
								tmp.add(send);
							}
						}
					}
				}); 
			}
		}
		return res;
	}
	
	// Not just "unfinished", but also "non-initiated" (accept guarded) -- though could be non-initiated after some previous completions
	// Maybe not needed -- previously not used (even without accept-correlation check)
	public Map<Role, EState> getUnfinishedRoles()
	{
		Map<Role, EState> res = new HashMap<>();
		if (getFireable().isEmpty() && !isSafeTermination())
		{
			for (Role r : this.efsms.keySet())
			{
				if (!canSafelyTerminate(r))
				{
					res.put(r, this.efsms.get(r).curr);
				}
			}
		}
		return res;
	}

	// Based on config semantics, not "static" graph edges (cf., super.getAllActions) -- used to build global model graph
	public Map<Role, List<EAction>> getFireable()
	{
		Map<Role, List<EAction>> res = new HashMap<>();
		for (Role self : this.efsms.keySet())
		{
			EFsm fsm = this.efsms.get(self);
			List<EAction> as = new LinkedList<>();
			switch (fsm.curr.getStateKind())  // Choice subject enabling needed for non-mixed states (mixed states would be needed for async. permutations though)
			{
				// Currently not convenient to delegate to states, as state types not distinguished
				case TERMINAL: break;
				case OUTPUT: as.addAll(getOutputFireable(self, fsm)); break;
				case UNARY_INPUT:
				case POLY_INPUT: 
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

	// fsm is self's Efsm
	private List<EAction> getOutputFireable(Role self, EFsm fsm)  // "output" and "client" actions
	{
		List<EAction> res = new LinkedList<>();
		for (EAction a : fsm.curr.getActions())  // Actions may be a mixture of the following
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

	// Unary or poly receive
	private List<ERecv> getReceiveFireable(Role self, EFsm fsm)
	{
		return fsm.curr.getActions().stream().map(x -> (ERecv) x)
				.filter(x -> this.queues.canReceive(self, x))
				.collect(Collectors.toList());
	}

	private List<EAcc> getAcceptFireable(Role self, EFsm fsm)
	{
		List<EAction> as = fsm.curr.getActions();
		Role peer = as.get(0).peer;  // All peer's the same
		List<EAction> peeras = this.efsms.get(peer).curr.getActions();
		return fsm.curr.getActions().stream().map(x -> (EAcc) x)
				.filter(x -> this.queues.canAccept(self, x)
						&& peeras.stream().anyMatch(y -> y.toDual(peer).equals(x)))
				.collect(Collectors.toList());
	}

	// Duplicated from getAcceptFireable
	private List<EServerWrap> getServerWrapFireable(Role self, EFsm fsm)
	{
		List<EAction> as = fsm.curr.getActions();  // Actually for ServerWrap, size() == 1
		Role peer = as.get(0).peer;  // All peer's the same
		List<EAction> peeras = this.efsms.get(peer).curr.getActions();
		return fsm.curr.getActions().stream().map(x -> (EServerWrap) x)
				.filter(x -> this.queues.canServerWrap(self, x)
						&& peeras.stream().anyMatch(y -> y.toDual(peer).equals(x)))
				.collect(Collectors.toList());
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
	
	@Override
	public String toString()
	{
		return "(" + this.efsms + ", " + this.queues + ")";
	}
}
