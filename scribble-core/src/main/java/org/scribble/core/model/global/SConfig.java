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
	public final SBuffers buffs;
	
	//public WFConfig(Map<Role, EndpointState> state, Map<Role, Map<Role, Send>> buff)
	//public WFConfig(Map<Role, EndpointState> state, WFBuffers buffs)
	public SConfig(ModelFactory mf, Map<Role, EFsm> state, SBuffers buffs)
	{
		this.mf = mf;
		
		this.efsms = Collections.unmodifiableMap(state);
		//this.buffs = Collections.unmodifiableMap(buff.keySet().stream() .collect(Collectors.toMap((k) -> k, (k) -> Collections.unmodifiableMap(buff.get(k)))));
		//this.buffs = Collections.unmodifiableMap(buff);
		this.buffs = buffs;
	}

	// FIXME: rename: not just termination, could be unconnected/uninitiated
	//public boolean isEnd()
	public boolean isSafeTermination()
	{
		//return this.states.values().stream().allMatch((s) -> s.isTerminal()) && this.buffs.isEmpty();
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
				(s.isTerminal() && !this.buffs.isEmpty(r))
					||
					(!s.isTerminal() &&
						//(!(s.getStateKind().equals(Kind.UNARYINPUT) && s.getTakeable().iterator().next().isAccept())  // Accept state now distinguished
						(
							!(s.getStateKind().equals(Kind.ACCEPT) && s.isInitial())  // FIXME: check stable
								// FIXME: needs initial state check -- although if there is an accept, there should a connect, and waitfor-errors checked via connects) -- this should be OK because connect/accept are sync -- but not fully sufficient by itself, see next
								// So could be blocked on unary accept part way through the protocol -- but also could be unfolded initial accept
							////|| this.states.keySet().stream().anyMatch((rr) -> !r.equals(rr) && this.buffs.isConnected(r, rr))))
							//&& this.states.keySet().stream().anyMatch((rr) -> !r.equals(rr) && this.buffs.isConnected(r, rr))
									// FIXME: isConnected is not symmetric, and could disconnect all part way through protocol -- but can't happen?
							// Above assumes initial is not terminal (holds for EFSMs), and doesn't check buffer is empty (i.e. for orphan messages)
						)
					)
					||
					(!s.isTerminal() && this.states.keySet().stream().anyMatch((rr) -> !r.equals(rr) && this.buffs.isConnected(r, rr)))
					;*/
		//return !cannotSafelyTerminate;
		boolean canSafelyTerminate =
				(s.curr.isTerminal() && this.buffs.isEmpty(r))
						|| (s.curr.getStateKind().equals(EStateKind.ACCEPT)
								&& s.isInitial())
					// FIXME: should be empty buffs
				
				// FIXME: incorrectly allows stuck accepts?  if inactive not initial, should be clone of initial?
			//|| (s.getStateKind().equals(Kind.ACCEPT) && this.states.keySet().stream().noneMatch((rr) -> !r.equals(rr) && this.buffs.isConnected(r, rr)))
			;
		return canSafelyTerminate;
	}
	
	public List<SConfig> async(Role r, EAction a)
	{
		List<SConfig> res = new LinkedList<>();
		
		//List<EndpointState> succs = this.states.get(r).takeAll(a);
		List<EFsm> succs = this.efsms.get(r).fireAll(a);
		//for (EndpointState succ : succs)
		for (EFsm succ : succs)
		{
			//Map<Role, EndpointState> tmp1 = new HashMap<>(this.states);
			Map<Role, EFsm> tmp1 = new HashMap<>(this.efsms);
			//Map<Role, Map<Role, Send>> tmp2 = new HashMap<>(this.buffs);
		
			tmp1.put(r, succ);

			/*Map<Role, Send> tmp3 = new HashMap<>(tmp2.get(a.peer));
			tmp2.put(a.peer, tmp3);* /
			Map<Role, Send> tmp3 = tmp2.get(a.peer);
			if (a.isSend())
			{
				tmp3.put(r, (Send) a);
			}
			else
			{
				tmp3.put(r, null);
			}*/
			SBuffers tmp2 = 
					a.isSend()       ? this.buffs.send(r, (ESend) a)
				: a.isReceive()    ? this.buffs.receive(r, (ERecv) a)
				: a.isDisconnect() ? this.buffs.disconnect(r, (EDisconnect) a)
				: null;
			if (tmp2 == null)
			{
				throw new RuntimeException("Shouldn't get in here: " + a);
			}
			res.add(this.mf.newSConfig(tmp1, tmp2));
		}

		return res;
	}

	// "Synchronous version" of fire
	public List<SConfig> sync(Role r1, EAction a1, Role r2, EAction a2)
	{
		List<SConfig> res = new LinkedList<>();
		
		/*List<EndpointState> succs1 = this.states.get(r1).takeAll(a1);
		List<EndpointState> succs2 = this.states.get(r2).takeAll(a2);
		for (EndpointState succ1 : succs1)*/
		List<EFsm> succs1 = this.efsms.get(r1).fireAll(a1);
		List<EFsm> succs2 = this.efsms.get(r2).fireAll(a2);
		for (EFsm succ1 : succs1)
		{
			//for (EndpointState succ2 : succs2)
			for (EFsm succ2 : succs2)
			{
				//Map<Role, EndpointState> tmp1 = new HashMap<>(this.states);
				Map<Role, EFsm> tmp1 = new HashMap<>(this.efsms);
				tmp1.put(r1, succ1);
				tmp1.put(r2, succ2);
				SBuffers tmp2;
				if (((a1.isRequest() && a2.isAccept()) || (a1.isAccept() && a2.isRequest())))
						//&& this.buffs.canConnect(r1, r2))
				{
					tmp2 = this.buffs.connect(r1, r2);
				}
				else if (((a1.isClientWrap() && a2.isServerWrap()) || (a1.isServerWrap() && a2.isClientWrap())))
				{
					tmp2 = this.buffs;  // OK, immutable?
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
				/*Set<IOAction> duals = this.buffs.get(r).entrySet().stream()
						.filter((e) -> e.getValue() != null)
						.map((e) -> e.getValue().toDual(e.getKey()))
						.collect(Collectors.toSet());
				if (duals.stream().anyMatch((a) -> s.isAcceptable(a)))
				{
					break;
				}*/
				Role peer = s.curr.getActions().iterator().next().peer;
				ESend send = this.buffs.get(r).get(peer);
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
			if (a.isReceive() && this.buffs.get(r).get(peer) == null)
			{
				//return peer;
			}*/
			if (a.isReceive())
			{
				Set<Role> peers = all.stream().map((x) -> x.peer).collect(Collectors.toSet());
				if (peers.stream().noneMatch((p) -> this.buffs.get(r).get(p) != null))
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
					if (this.buffs.get(r).get(p) != null)
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
									//if (peera.equals(c.toDual(r)) && this.buffs.canConnect(r, c))
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
				Set<ESend> orphs = this.buffs.get(r).values().stream().filter(v -> v != null).collect(Collectors.toSet());
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
						if (!this.buffs.isConnected(r, rr))
						{
							ESend send = this.buffs.get(r).get(rr);
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
		for (Role r : this.efsms.keySet())
		{
			//EndpointState s = this.states.get(r);
			EFsm fsm = this.efsms.get(r);
			switch (fsm.curr.getStateKind())  // Choice subject enabling needed for non-mixed states (mixed states would be needed for async. permutations though)
			{
				case OUTPUT:
				{
					List<EAction> as = fsm.curr.getActions();
					for (EAction a : as)
					{
						if (a.isSend())
						{
							if (this.buffs.canSend(r, (ESend) a))
							{
								List<EAction> tmp = res.get(r);  // FIXME: factor out
								if (tmp == null)
								{
									tmp = new LinkedList<>();
									res.put(r, tmp);
								}
								tmp.add(a);
							}
						}
						else if (a.isRequest())
						{
							// FIXME: factor out
							EReq c = (EReq) a;
							//EndpointState speer = this.states.get(c.peer);
							EFsm speer = this.efsms.get(c.peer);
							//if (speer.getStateKind() == Kind.UNARY_INPUT)
							{
								List<EAction> peeras = speer.curr.getActions();
								for (EAction peera : peeras)
								{
									if (peera.equals(c.toDual(r)) && this.buffs.canConnect(r, c))  // Cf. isWaitingFor
									{
										List<EAction> tmp = res.get(r);
										if (tmp == null)
										{
											tmp = new LinkedList<>();
											res.put(r, tmp);
										}
										tmp.add(a);
									}
								}
							}
						}
						else if (a.isDisconnect())
						{
							// Duplicated from Send
							if (this.buffs.canDisconnect(r, (EDisconnect) a))
							{
								List<EAction> tmp = res.get(r);  // FIXME: factor out
								if (tmp == null)
								{
									tmp = new LinkedList<>();
									res.put(r, tmp);
								}
								tmp.add(a);
							}
						}
						else if (a.isClientWrap())
						{
							// FIXME: factor out
							EClientWrap wc = (EClientWrap) a;
							EFsm speer = this.efsms.get(wc.peer);
							List<EAction> peeras = speer.curr.getActions();
							for (EAction peera : peeras)
							{
								if (peera.equals(wc.toDual(r)) && this.buffs.canWrapClient(r, wc))  // Cf. isWaitingFor
								{
									List<EAction> tmp = res.get(r);
									if (tmp == null)
									{
										tmp = new LinkedList<>();
										res.put(r, tmp);
									}
									tmp.add(a);
								}
							}
						}
						else
						{
							throw new RuntimeException("Shouldn't get in here: " + a);
						}
					}
					break;
				}
				case UNARY_INPUT:
				case POLY_INPUT:
				{
					for (EAction a : this.buffs.inputable(r))
					{
						if (a.isReceive())
						{
							if (fsm.curr.hasAction(a))
							{
								List<EAction> tmp = res.get(r);
								if (tmp == null)
								{
									tmp = new LinkedList<>();
									res.put(r, tmp);
								}
								tmp.add(a);
							}
						}
						/*else if (a.isAccept())
						{
							// FIXME: factor out
							Accept c = (Accept) a;
							EndpointState speer = this.states.get(c.peer);
							//if (speer.getStateKind() == Kind.OUTPUT)
							{
								List<IOAction> peeras = speer.getAllAcceptable();
								for (IOAction peera : peeras)
								{
									if (peera.equals(c.toDual(r)) && this.buffs.canAccept(r, c))
									{
										List<IOAction> tmp = res.get(r);
										if (tmp == null)
										{
											tmp = new LinkedList<>();
											res.put(r, tmp);
										}
										tmp.add(a);
										//break;  // Add all of them
									}
								}
							}
						}*/
						else
						{
							throw new RuntimeException("Shouldn't get in here: " + a);
						}
					}
					break;
				}
				case TERMINAL:
				{
					break;
				}
				/*case CONNECT:
				{
					List<IOAction> as = s.getAllTakeable();
					for (IOAction a : as)
					{
						if (a.isConnect())  ..// FIXME: could be send actions
						{
							// FIXME: factor out
							Connect c = (Connect) a;
							EndpointState speer = this.states.get(c.peer);
							//if (speer.getStateKind() == Kind.UNARY_INPUT)
							{
								List<IOAction> peeras = speer.getAllTakeable();
								for (IOAction peera : peeras)
								{
									if (peera.equals(c.toDual(r)) && this.buffs.canConnect(r, c))
									{
										List<IOAction> tmp = res.get(r);
										if (tmp == null)
										{
											tmp = new LinkedList<>();
											res.put(r, tmp);
										}
										tmp.add(a);
									}
								}
							}
						}
						else
						{
							throw new RuntimeException("Shouldn't get in here: " + s);
						}	
					}
					break;
				}*/
				case ACCEPT:
				{
					for (EAction a : this.buffs.acceptable(r, fsm.curr))
					{
						if (a.isAccept())
						{
							// FIXME: factor out
							EAcc c = (EAcc) a;
							//EndpointState speer = this.states.get(c.peer);
							EFsm speer = this.efsms.get(c.peer);
							//if (speer.getStateKind() == Kind.OUTPUT)
							{
								List<EAction> peeras = speer.curr.getActions();
								for (EAction peera : peeras)
								{
									if (peera.equals(c.toDual(r)) && this.buffs.canAccept(r, c))
									{
										List<EAction> tmp = res.get(r);
										if (tmp == null)
										{
											tmp = new LinkedList<>();
											res.put(r, tmp);
										}
										tmp.add(a);
										//break;  // Add all of them
									}
								}
							}
						}
						else
						{
							throw new RuntimeException("Shouldn't get in here: " + a);
						}
					}
					break;
				}
				case SERVER_WRAP:
				{
					for (EAction a : this.buffs.wrapable(r))
					{
						if (a.isServerWrap())
						{
							EServerWrap ws = (EServerWrap) a;
							EFsm speer = this.efsms.get(ws.peer);
							{
								List<EAction> peeras = speer.curr.getActions();
								for (EAction peera : peeras)
								{
									if (peera.equals(ws.toDual(r)) && this.buffs.canWrapServer(r, ws))
									{
										List<EAction> tmp = res.get(r);
										if (tmp == null)
										{
											tmp = new LinkedList<>();
											res.put(r, tmp);
										}
										tmp.add(a);
									}
								}
							}
						}
						else
						{
							throw new RuntimeException("Shouldn't get in here: " + a);
						}
					}
					break;
				}
				default:
				{
					throw new RuntimeException("Shouldn't get in here: " + fsm);
				}
			}
		}
		return res;
	}

	@Override
	public int hashCode()
	{
		int hash = 71;
		hash = 31 * hash + this.efsms.hashCode();
		hash = 31 * hash + this.buffs.hashCode();
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
		return this.efsms.equals(c.efsms) && this.buffs.equals(c.buffs);
	}
	
	@Override
	public String toString()
	{
		return "(" + this.efsms + ", " + this.buffs + ")";
	}
}
