package org.scribble.model.wf;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.scribble.model.local.Accept;
import org.scribble.model.local.Connect;
import org.scribble.model.local.EndpointState;
import org.scribble.model.local.IOAction;
import org.scribble.model.local.Receive;
import org.scribble.model.local.Send;
import org.scribble.sesstype.name.Role;

public class WFConfig
{
	public final Map<Role, EndpointState> states;
	//public final Map<Role, Map<Role, Send>> buffs;  // dest -> src -> msg
	public final WFBuffers buffs;
	
	//public WFConfig(Map<Role, EndpointState> state, Map<Role, Map<Role, Send>> buff)
	public WFConfig(Map<Role, EndpointState> state, WFBuffers buffs)
	{
		this.states = Collections.unmodifiableMap(state);
		//this.buffs = Collections.unmodifiableMap(buff.keySet().stream() .collect(Collectors.toMap((k) -> k, (k) -> Collections.unmodifiableMap(buff.get(k)))));
		//this.buffs = Collections.unmodifiableMap(buff);
		this.buffs = buffs;
	}

	// Means successful termination
	public boolean isEnd()
	{
		//return this.states.values().stream().allMatch((s) -> s.isTerminal()) && this.buffs.isEmpty();
		for (Role r : this.states.keySet())
		{
			EndpointState s = this.states.get(r);
			if ((!s.isTerminal() && this.states.keySet().stream().anyMatch((rr) -> !r.equals(rr) && this.buffs.isConnected(r, rr)))
							// Above assumes initial is not terminal (holds for EFSMs), and doesn't check buffer is empty (i.e. for orphan messages)
					|| (s.isTerminal() && !this.buffs.isEmpty(r)))
			{
				return false;
			}
		}
		return true;
	}
	
	public List<WFConfig> accept(Role r, IOAction a)
	{
		List<WFConfig> res = new LinkedList<>();
		
		List<EndpointState> succs = this.states.get(r).acceptAll(a);
		for (EndpointState succ : succs)
		{
			Map<Role, EndpointState> tmp1 = new HashMap<>(this.states);
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
			WFBuffers tmp2;
			if (a.isSend())
			{
				tmp2 = this.buffs.send(r, (Send) a);
			}
			else if (a.isReceive())
			{
				tmp2 = this.buffs.receive(r, (Receive) a);
			}
			else
			{
				throw new RuntimeException("Shouldn't get in here: " + a);
			}
			res.add(new WFConfig(tmp1, tmp2));
		}

		return res;
	}

	public List<WFConfig> sync(Role r1, IOAction a1, Role r2, IOAction a2)
	{
		List<WFConfig> res = new LinkedList<>();
		
		List<EndpointState> succs1 = this.states.get(r1).acceptAll(a1);
		List<EndpointState> succs2 = this.states.get(r2).acceptAll(a2);
		for (EndpointState succ1 : succs1)
		{
			for (EndpointState succ2 : succs2)
			{
				Map<Role, EndpointState> tmp1 = new HashMap<>(this.states);
				tmp1.put(r1, succ1);
				tmp1.put(r2, succ2);
				WFBuffers tmp2;
				if (((a1.isConnect() && a2.isAccept()) || (a1.isAccept() && a2.isConnect())))
						//&& this.buffs.canConnect(r1, r2))
				{
					tmp2 = this.buffs.connect(r1, r2);
				}
				else
				{
					throw new RuntimeException("Shouldn't get in here: " + a1 + ", " + a2);
				}
				res.add(new WFConfig(tmp1, tmp2));
			}
		}

		return res;
	}
	
	public Map<Role, List<IOAction>> getAcceptable()
	{
		Map<Role, List<IOAction>> res = new HashMap<>();
		for (Role r : this.states.keySet())
		{
			EndpointState s = this.states.get(r);
			switch (s.getStateKind())  // Choice subject enabling needed for non-mixed states (mixed states would be needed for async. permutations though)
			{
				case OUTPUT:
				{
					List<IOAction> as = s.getAllAcceptable();
					for (IOAction a : as)
					{
						if (a.isSend())
						{
							if (this.buffs.canSend(r, (Send) a))
							{
								List<IOAction> tmp = res.get(r);  // FIXME: factor out
								if (tmp == null)
								{
									tmp = new LinkedList<>();
									res.put(r, tmp);
								}
								tmp.add(a);
							}
						}
						else //if (a.isConnect())
						{
							// FIXME: factor out
							Connect c = (Connect) a;
							EndpointState speer = this.states.get(c.peer);
							//if (speer.getStateKind() == Kind.UNARY_INPUT)
							{
								List<IOAction> peeras = speer.getAllAcceptable();
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
					}
					break;
				}
				case UNARY_INPUT:
				case POLY_INPUT:
				{
					for (IOAction a : this.buffs.inputable(r))
					{
						if (a.isReceive())
						{
							if (s.isAcceptable(a))
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
						else //if (a.isAccept())
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
						}
					}
					break;
				}
				case TERMINAL:
				{
					break;
				}
			}
		}
		return res;
	}

	@Override
	public final int hashCode()
	{
		int hash = 71;
		hash = 31 * hash + this.states.hashCode();
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
		if (!(o instanceof WFConfig))
		{
			return false;
		}
		WFConfig c = (WFConfig) o;
		return this.states.equals(c.states) && this.buffs.equals(c.buffs);
	}
	
	@Override
	public String toString()
	{
		return "(" + this.states + ", " + this.buffs + ")";
	}
}
