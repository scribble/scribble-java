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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.scribble.core.model.endpoint.actions.EAcc;
import org.scribble.core.model.endpoint.actions.EClientWrap;
import org.scribble.core.model.endpoint.actions.EDisconnect;
import org.scribble.core.model.endpoint.actions.ERecv;
import org.scribble.core.model.endpoint.actions.EReq;
import org.scribble.core.model.endpoint.actions.ESend;
import org.scribble.core.model.endpoint.actions.EServerWrap;
import org.scribble.core.type.name.Role;

public class SBuffers
{
	private final Map<Role, Map<Role, Boolean>> connected = new HashMap<>();
	private final Map<Role, Map<Role, ESend>> buffs = new HashMap<>();  // dest -> src -> msg

	public SBuffers(Set<Role> roles, boolean implicit)
	{
		this(roles);
		
		if (implicit)
		{
			roles.forEach(k -> 
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

	public SBuffers(Set<Role> roles)
	{
		// FIXME: do the same for connected
		roles.forEach(k -> 
		{
			HashMap<Role, ESend> tmp = new HashMap<>();
			this.buffs.put(k, tmp);
			roles.forEach(k2 -> 
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
		Set<Role> roles = buffs.buffs.keySet();
		roles.forEach((k) ->
		{
			Map<Role, Boolean> tmp = buffs.connected.get(k);
			if (tmp != null)
			{
				this.connected.put(k, new HashMap<>(tmp));
			}
		});
		roles.forEach(k -> 
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

	public boolean canSend(Role self, ESend a)
	{
		if (!isConnected(self, a.peer))
		{
			return false;
		}
		return isConnected(self, a.peer)
				&& this.buffs.get(a.peer).get(self) == null;
	}

	public boolean canReceive(Role self, ERecv a)
	{
		if (!isConnected(self, a.peer))
		{
			return false;
		}
		ESend send = this.buffs.get(self).get(a.peer);
		return send != null && send.toDual(a.peer).equals(a);
	}

	public boolean canAccept(Role self, EAcc a)
	{
		return !isConnected(self, a.peer);
	}

	// N.B. only considers the self side, i.e., to actually fire (sync), must also explicitly check canAccept
	public boolean canRequest(Role self, EReq c)
	{
		return !isConnected(self, c.peer);
	}

	// N.B. only considers the self side, i.e., to actually fire (sync), must also explicitly check peer canDisconnect
	public boolean canDisconnect(Role self, EDisconnect d)
	{
		return isConnected(self, d.peer);
	}

	// N.B. only considers the self side, i.e., to actually fire (sync), must also explicitly check canServerWrap
	public boolean canClientWrap(Role self, EClientWrap cw)
	{
		return isConnected(self, cw.peer);
	}

	// N.B. only considers the self side, i.e., to actually fire (sync), must also explicitly check canClientWrap
	public boolean canServerWrap(Role self, EServerWrap sw)
	{
		return isConnected(self, sw.peer);
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

	public SBuffers send(Role self, ESend a)
	{
		SBuffers copy = new SBuffers(this);
		copy.buffs.get(a.peer).put(self, a);
		return copy;
	}

	public SBuffers receive(Role self, ERecv a)
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
		return this.buffs.entrySet().stream()
				.filter(e -> e.getValue().values().stream().anyMatch(v -> v != null))
				.collect(Collectors.toMap(
						e -> e.getKey(),
						e -> e.getValue().entrySet().stream()
								.filter(f -> f.getValue() != null)
								.collect(Collectors.toMap(f -> f.getKey(), f -> f.getValue()))
				)).toString();
	}
}
