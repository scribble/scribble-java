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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.model.endpoint.EModelFactory;
import org.scribble.model.endpoint.EState;
import org.scribble.model.endpoint.actions.EAccept;
import org.scribble.model.endpoint.actions.EAction;
import org.scribble.model.endpoint.actions.ERequest;
import org.scribble.model.endpoint.actions.EDisconnect;
import org.scribble.model.endpoint.actions.EReceive;
import org.scribble.model.endpoint.actions.ESend;
import org.scribble.model.endpoint.actions.EWrapClient;
import org.scribble.model.endpoint.actions.EWrapServer;
import org.scribble.type.name.Role;

public class SBuffers
{
	private final EModelFactory ef;  // FIXME: only used by wrapable -- refactor?
	
	private final Map<Role, Map<Role, Boolean>> connected = new HashMap<>();
	private final Map<Role, Map<Role, ESend>> buffs = new HashMap<>();  // dest -> src -> msg

	public SBuffers(EModelFactory ef, Set<Role> roles, boolean implicit)
	{
		this(ef, roles);
		
		if (implicit)
		{
			roles.forEach((k) -> 
			{
				HashMap<Role, Boolean> tmp = new HashMap<>();
				this.connected.put(k, tmp);
				roles.forEach((k2) -> 
				{
					if (!k.equals(k2))
					{
						tmp.put(k2, true);
					}
				});
			});
		}
	}

	public SBuffers(EModelFactory ef, Set<Role> roles)
	{
		this.ef = ef;

		// FIXME: do the same for connected
		roles.forEach((k) -> 
		{
			HashMap<Role, ESend> tmp = new HashMap<>();
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

	public SBuffers(SBuffers buffs)
	{
		this.ef = buffs.ef;

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
	
	// FIXME factor out properly with constructor
	public Map<Role, Map<Role, ESend>> getBuffers()
	{
		//return this.buffs;
		return new SBuffers(this).buffs;
	}

	public Map<Role, ESend> get(Role r)
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

	public boolean canAccept(Role self, EAccept a)
	//public boolean canAccept(Role r1, Role r2)
	{
		return !isConnected(self, a.peer);
		//return canConnect(r2, r1);
	}

	public boolean canConnect(Role self, ERequest c)
	//public boolean canConnect(Role r1, Role r2)
	{
		return !isConnected(self, c.peer);
		//return !isConnected(r1, r2);
	}

	public boolean canDisconnect(Role self, EDisconnect d)
	{
		return isConnected(self, d.peer);
	}

	public boolean canWrapClient(Role self, EWrapClient wc)
	{
		return isConnected(self, wc.peer);
	}

	public boolean canWrapServer(Role self, EWrapServer ws)
	{
		return isConnected(self, ws.peer);
	}
	
	//public WFBuffers connect(Role src, Connect c, Role dest)
	public SBuffers connect(Role src, Role dest)
	{
		SBuffers copy = new SBuffers(this);
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

	public SBuffers disconnect(Role self, EDisconnect d)
	{
		SBuffers copy = new SBuffers(this);
		copy.connected.get(self).put(d.peer, false);
		return copy;
	}

	public boolean canSend(Role self, ESend a)
	{
		return isConnected(self, a.peer) && (this.buffs.get(a.peer).get(self) == null);
	}

	public SBuffers send(Role self, ESend a)
	{
		SBuffers copy = new SBuffers(this);
		copy.buffs.get(a.peer).put(self, a);
		return copy;
	}

	/*public boolean canReceive(Role self, Receive a)
	{
		Send send = this.buffs.get(self).get(a.peer);
		return send != null && send.toDual(a.peer).equals(a);
	}*/

	//public Set<Receive> receivable(Role r) 
	public Set<EAction> inputable(Role r)   // FIXME: IAction  // FIXME: OAction version?
	{
		/*Map<Role, Boolean> tmp = this.connected.get(r); 
		if (tmp == null)
		{
			return Collections.emptySet();  // Not needed, guarded by state kind
		}*/
		Set<EAction> res = this.buffs.get(r).entrySet().stream()
				.filter(e -> e.getValue() != null)
				.map(e -> e.getValue().toDual(e.getKey()))
				.collect(Collectors.toSet());
		/*Map<Role, Boolean> tmp = this.connected.get(r);
		if (tmp == null)
		{
			this.buffs.get(r).keySet().forEach((x) -> res.add(new Accept(x)));
		}
		else
		{
			this.connected.keySet().stream()
				.filter((k) -> !tmp.containsKey(k) || !tmp.get(k))
				.forEach((k) -> res.add(new Accept(k)));
		}*/
		return res;
	}
	
	//public Set<IOAction> acceptable(Role r)  // Means connection accept actions
	// Pre: curr is Accept state, r is accept peer
	public Set<EAction> acceptable(Role r, EState curr)  // Means connection accept actions
	{
		Set<EAction> res = new HashSet<>();
		Map<Role, Boolean> tmp = this.connected.get(r);
		/*if (tmp == null)
		{
			this.buffs.get(r).keySet().forEach((x) -> res.add(new Accept(x)));
		}
		else
		{
			this.connected.keySet().stream()
				.filter((k) -> !tmp.containsKey(k) || !tmp.get(k))
				.forEach((k) -> res.add(new Accept(k)));
		}*/
		if (tmp != null)
		{
			Boolean b = tmp.get(r);
			if (b != null && b)
			{
				return res;
			}
		}
		List<EAction> as = curr.getAllActions();
		for (EAction a : as)
		{
			res.add((EAccept) a);
		}
		return res;
	}

	public Set<EAction> wrapable(Role r)
	{
		Set<EAction> res = new HashSet<>();
		Map<Role, Boolean> tmp = this.connected.get(r);
		if (tmp != null)
		{
			this.connected.keySet().stream()
				.filter(k -> tmp.containsKey(k) && tmp.get(k))
				.forEach(k -> res.add(this.ef.newEWrapServer(k)));
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

	public SBuffers receive(Role self, EReceive a)
	{
		SBuffers copy = new SBuffers(this);
		copy.buffs.get(self).put(a.peer, null);
		return copy;
	}

	@Override
	public final int hashCode()
	{
		int hash = 131;
		hash = 31 * hash + this.buffs.hashCode();
		hash = 31 * hash + this.connected.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof SBuffers))
		{
			return false;
		}
		SBuffers b = (SBuffers) o;
		return this.buffs.equals(b.buffs) && this.connected.equals(b.connected);
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
								.collect(Collectors.toMap((Entry<Role, ESend> f) -> f.getKey(), (f) -> f.getValue())))
					)).toString();
	}
}
