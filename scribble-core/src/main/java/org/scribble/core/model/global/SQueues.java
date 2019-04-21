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
import java.util.Map.Entry;
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

public class SQueues
{
	private final Map<Role, Map<Role, Boolean>> connected = new HashMap<>();  // client -> server -> connected?  (symmetric)
	private final Map<Role, Map<Role, ESend>> queues = new HashMap<>();  // dest -> src -> msg  // null ESend for empty queue

	public SQueues(Set<Role> roles, boolean implicit)
	{
		for(Role r1 : roles)
		{
			HashMap<Role, Boolean> connected = new HashMap<>();
			HashMap<Role, ESend> queues = new HashMap<>();
			for (Role r2 : roles)
			{
				if (!r1.equals(r2)) 
				{
					connected.put(r2, implicit); 
					queues.put(r2, null);  // null for empty queue
				}
			}
			this.connected.put(r1, connected);
			this.queues.put(r1, queues);
		}
	}

	protected SQueues(SQueues queues)
	{
		for (Role r : queues.queues.keySet())
		{
			this.connected.put(r, new HashMap<>(queues.connected.get(r)));
			this.queues.put(r, new HashMap<>(queues.queues.get(r)));
		}
	}

	public boolean canSend(Role self, ESend a)
	{
		return isConnected(self, a.peer)
				&& this.queues.get(a.peer).get(self) == null;
	}

	public boolean canReceive(Role self, ERecv a)
	{
		// N.B. *not* checking isConnected -- can still receive from our side after other side has closed
		ESend send = this.queues.get(self).get(a.peer);
		return send != null && send.toDual(a.peer).equals(a);
	}

	// N.B. only considers the self side, i.e., to actually fire (sync), must also explicitly check canAccept
	public boolean canRequest(Role self, EReq c)
	{
		return !isConnected(self, c.peer);
	}

	// N.B. only considers the self side, i.e., to actually fire (sync), must also explicitly check canAccept
	public boolean canAccept(Role self, EAcc a)
	{
		return !isConnected(self, a.peer);
	}

	// N.B. only considers the self side, i.e., to actually fire (sync), must also explicitly check peer canDisconnect
	public boolean canDisconnect(Role self, EDisconnect d)
	{
		return isConnected(self, d.peer);
	}

	// N.B. only considers the self side, i.e., to actually fire (sync), must also explicitly check canServerWrap
	// N.B. doesn't actually change any state
	public boolean canClientWrap(Role self, EClientWrap cw)
	{
		return isConnected(self, cw.peer);
	}

	// N.B. only considers the self side, i.e., to actually fire (sync), must also explicitly check canClientWrap
	// N.B. doesn't actually change queues state
	public boolean canServerWrap(Role self, EServerWrap sw)
	{
		return isConnected(self, sw.peer);
	}

	public SQueues send(Role self, ESend a)
	{
		SQueues copy = new SQueues(this);
		copy.queues.get(a.peer).put(self, a);
		return copy;
	}

	public SQueues receive(Role self, ERecv a)
	{
		SQueues copy = new SQueues(this);
		copy.queues.get(self).put(a.peer, null);
		return copy;
	}
	
  // Sync action
	public SQueues connect(Role src, Role dest)
	{
		SQueues copy = new SQueues(this);
		copy.connected.get(src).put(dest, true);
		copy.connected.get(dest).put(src, true);
		return copy;
	}

	public SQueues disconnect(Role self, EDisconnect d)
	{
		SQueues copy = new SQueues(this);
		copy.connected.get(self).put(d.peer, false);
		return copy;
	}

	public boolean isConnected(Role self, Role peer)
	{
		return this.connected.get(self).get(peer);
	}
	
	public boolean isEmpty(Role r)  // this.connected doesn't matter
	{
		return this.queues.get(r).values().stream().allMatch(v -> v == null);
	}
	
	// Return a (deep) copy -- currently, checkEventualReception expects a modifiable return
	public Map<Role, Map<Role, ESend>> getQueues()
	{
		return this.queues.entrySet().stream().collect(Collectors.toMap(
				Entry::getKey,
				x -> new HashMap<>(x.getValue())));  // Collections.unmodifiableMap(x.getValue())
	}

	public Map<Role, ESend> getQueue(Role r)
	{
		return Collections.unmodifiableMap(this.queues.get(r));
	}

	@Override
	public final int hashCode()
	{
		int hash = 131;
		hash = 31 * hash + this.connected.hashCode();
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
		if (!(o instanceof SQueues))
		{
			return false;
		}
		SQueues b = (SQueues) o;
		return this.connected.equals(b.connected) && this.queues.equals(b.queues);
	}
	
	@Override
	public String toString()
	{
		return this.queues.entrySet().stream()
				.filter(e -> e.getValue().values().stream().anyMatch(v -> v != null))
				.collect(Collectors.toMap(
						e -> e.getKey(),
						e -> e.getValue().entrySet().stream()
								.filter(f -> f.getValue() != null)
								.collect(Collectors.toMap(f -> f.getKey(), f -> f.getValue()))
				)).toString();
	}
}
