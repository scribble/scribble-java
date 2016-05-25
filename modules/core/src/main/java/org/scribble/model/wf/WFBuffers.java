package org.scribble.model.wf;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.model.local.Accept;
import org.scribble.model.local.Connect;
import org.scribble.model.local.IOAction;
import org.scribble.model.local.Receive;
import org.scribble.model.local.Send;
import org.scribble.sesstype.name.Role;

public class WFBuffers
{
	private final Map<Role, Map<Role, Boolean>> connected = new HashMap<>();
	private final Map<Role, Map<Role, Send>> buffs = new HashMap<>();  // dest -> src -> msg

	public WFBuffers(Set<Role> roles)
	{
		// FIXME: do the same for connected
		roles.forEach((k) -> 
		{
			HashMap<Role, Send> tmp = new HashMap<>();
			this.buffs.put(k, tmp);
			roles.forEach((k2) -> 
			{
				if (!k.equals(k2))
				{
					tmp.put(k2, null);
				}
			});
		});
	}

	public WFBuffers(WFBuffers buffs)
	{
		Set<Role> roles = buffs.buffs.keySet();
		roles.forEach((k) ->
		{
			Map<Role, Boolean> tmp = buffs.connected.get(k);
			if (tmp != null)
			{
				this.connected.put(k, new HashMap<>(tmp));
			}
		});
		roles.forEach((k) -> 
		{
			this.buffs.put(k, new HashMap<>(buffs.buffs.get(k)));
		});
	}
	
	public Map<Role, Send> get(Role r)
	{
		return Collections.unmodifiableMap(this.buffs.get(r));
	}
	
	/*public boolean isEmpty()
	{
		return this.buffs.values().stream().flatMap((m) -> m.values().stream()).allMatch((v) -> v == null);
	}*/
	public boolean isEmpty(Role r)
	{
		return this.buffs.get(r).values().stream().allMatch((v) -> v == null);
	}

	public boolean isConnected(Role self, Role peer)
	{
		Map<Role, Boolean> tmp = this.connected.get(self);
		if (tmp == null)
		{
			return false;
		}
		Boolean b = tmp.get(peer);
		return b != null && b;
	}

	public boolean canAccept(Role self, Accept a)
	//public boolean canAccept(Role r1, Role r2)
	{
		return !isConnected(self, a.peer);
		//return canConnect(r2, r1);
	}

	public boolean canConnect(Role self, Connect c)
	//public boolean canConnect(Role r1, Role r2)
	{
		return !isConnected(self, c.peer);
		//return !isConnected(r1, r2);
	}
	
	//public WFBuffers connect(Role src, Connect c, Role dest)
	public WFBuffers connect(Role src, Role dest)
	{
		WFBuffers copy = new WFBuffers(this);
		Map<Role, Boolean> tmp1 = copy.connected.get(src);
		if (tmp1 == null)
		{
			tmp1 = new HashMap<>();
			copy.connected.put(src, tmp1);
		}
		tmp1.put(dest, true);
		Map<Role, Boolean> tmp2 = copy.connected.get(dest);
		if (tmp2 == null)
		{
			tmp2 = new HashMap<>();
			copy.connected.put(dest, tmp2);
		}
		tmp2.put(src, true);
		//copy.buffs.get(c.peer).put(src, new Send(c.peer, c.mid, c.payload));
		return copy;
	}

	public boolean canSend(Role self, Send a)
	{
		return isConnected(self, a.peer) && (this.buffs.get(a.peer).get(self) == null);
	}

	public WFBuffers send(Role self, Send a)
	{
		WFBuffers copy = new WFBuffers(this);
		copy.buffs.get(a.peer).put(self, a);
		return copy;
	}

	/*public boolean canReceive(Role self, Receive a)
	{
		Send send = this.buffs.get(self).get(a.peer);
		return send != null && send.toDual(a.peer).equals(a);
	}*/

	//public Set<Receive> receivable(Role r) 
	public Set<IOAction> inputable(Role r)   // FIXME: IAction  // FIXME: OAction version?
	{
		Set<IOAction> res = this.buffs.get(r).entrySet().stream()
				.filter((e) -> e.getValue() != null)
				.map((e) -> e.getValue().toDual(e.getKey()))
				.collect(Collectors.toSet());
		Map<Role, Boolean> tmp = this.connected.get(r);
		if (tmp == null)
		{
			this.buffs.get(r).keySet().forEach((x) -> res.add(new Accept(x)));
		}
		else
		{
			tmp.entrySet().stream()
					.filter((e) -> !e.getValue())
					.forEach((e) -> res.add(new Accept(e.getKey())));
		}
		return res;
	}
	
	/*//public Map<Role, Receive> receivable() 
	public Map<Role, IOAction> inputable() 
	{
		//Map<Role, Receive> tmp = new HashMap<>();
		Map<Role, IOAction> tmp = new HashMap<>();
		for (Role r : this.buffs.keySet())
		{
			Map<Role, Send> tmp2 = this.buffs.get(r);
			for (Role r2: tmp2.keySet())
			{
				tmp.put(r, tmp2.get(r2).toDual(r2));
			}
		}
		return tmp;
	}*/

	public WFBuffers receive(Role self, Receive a)
	{
		WFBuffers copy = new WFBuffers(this);
		copy.buffs.get(self).put(a.peer, null);
		return copy;
	}

	@Override
	public final int hashCode()
	{
		int hash = 131;
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
		if (!(o instanceof WFBuffers))
		{
			return false;
		}
		WFBuffers b = (WFBuffers) o;
		return this.buffs.equals(b.buffs);
	}
	
	@Override
	public String toString()
	{
		//return this.buffs.toString();
		return this.buffs.entrySet().stream()
				.filter((e) -> e.getValue().values().stream().anyMatch((v) -> v != null))
				.collect(Collectors.toMap((e) -> e.getKey(),
						(e) -> (e.getValue().entrySet().stream()
								.filter((f) -> f.getValue() != null)
								//.collect(Collectors.toMap((f) -> f.getKey(), (f) -> f.getValue())))  // Inference not working?
								.collect(Collectors.toMap((Entry<Role, Send> f) -> f.getKey(), (f) -> f.getValue())))
					)).toString();
	}
}
