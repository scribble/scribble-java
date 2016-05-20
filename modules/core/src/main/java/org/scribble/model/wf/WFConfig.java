package org.scribble.model.wf;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
		return this.states.values().stream().allMatch((s) -> s.isTerminal()) && this.buffs.isEmpty();
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
			else
			{
				tmp2 = this.buffs.receive(r, (Receive) a);
			}
			res.add(new WFConfig(tmp1, tmp2));
		}

		return res;
	}
	
	public Map<Role, List<IOAction>> getAcceptable()
	{
		Map<Role, List<IOAction>> res = new HashMap<>();
		for (Role r : this.states.keySet())
		{
			EndpointState s = this.states.get(r);
			switch (s.getStateKind())
			{
				case OUTPUT:
				{
					List<IOAction> as = s.getAllAcceptable();
					for (IOAction a : as)
					{
						if (this.buffs.canSend(r, (Send) a))
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
					break;
				}
				case UNARY_INPUT:
				case POLY_INPUT:
				{
					for (Receive e : this.buffs.receivable(r))
					{
						if (s.isAcceptable(e))
						{
							List<IOAction> tmp = res.get(r);
							if (tmp == null)
							{
								tmp = new LinkedList<>();
								res.put(r, tmp);
							}
							tmp.add(e);
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
