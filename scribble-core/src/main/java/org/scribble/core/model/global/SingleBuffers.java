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

// Immutable -- send/receive/etc return updated copies
public class SingleBuffers
{
	private final Map<Role, Map<Role, Boolean>> connected = new HashMap<>();  // local -> peer -> does-local-consider-connected  (symmetric)
			// CHECKME: refactor as Map<Role, Set<Role>> ?  cf. ConnectionChecker

	private final Map<Role, Map<Role, ESend>> buffs = new HashMap<>();  // dest -> src -> msg -- N.B. connected.get(A).get(B) => can send into buffs.get(B).get(A) ("reversed")
			// N.B. hardcoded to capacity one -- SQueues would be the generalisation
			// null ESend for empty queue

	public SingleBuffers(Set<Role> roles, boolean implicit)
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
			this.buffs.put(r1, queues);
		}
	}

	protected SingleBuffers(SingleBuffers queues)
	{
		for (Role r : queues.buffs.keySet())
		{
			this.connected.put(r, new HashMap<>(queues.connected.get(r)));
			this.buffs.put(r, new HashMap<>(queues.buffs.get(r)));
		}
	}

	public boolean canSend(Role self, ESend a)
	{
		return isConnected(self, a.peer) //&& isConnected(a.peer, self)  // CHECKME: only consider local side?
				&& this.buffs.get(a.peer).get(self) == null;
	}

	public boolean canReceive(Role self, ERecv a)
	{
		ESend send = this.buffs.get(self).get(a.peer);
		return isConnected(self, a.peer)  // Other direction doesn't matter, local can still receive after peer disconnected
				&& send != null && send.toDual(a.peer).equals(a);
	}

	// N.B. "sync" action but only considers the self side, i.e., to actually fire, must also explicitly check canAccept
	public boolean canRequest(Role self, EReq c)
	{
		return !isConnected(self, c.peer);
	}

	// N.B. "sync" action but only considers the self side, i.e., to actually fire, must also explicitly check canRequest
	public boolean canAccept(Role self, EAcc a)
	{
		return !isConnected(self, a.peer);
	}

	public boolean canDisconnect(Role self, EDisconnect d)
	{
		return isConnected(self, d.peer);
	}

	// N.B. "sync" action but only considers the self side, i.e., to actually fire, must also explicitly check canServerWrap
	// N.B. doesn't actually change any state
	public boolean canClientWrap(Role self, EClientWrap cw)
	{
		return isConnected(self, cw.peer);
	}

	// N.B. "sync" action but only considers the self side, i.e., to actually fire, must also explicitly check canClientWrap
	// N.B. doesn't actually change queues state
	public boolean canServerWrap(Role self, EServerWrap sw)
	{
		return isConnected(self, sw.peer);
	}

	// Pre: canSend, e.g., via via SConfig.getFireable
	// Return an updated copy
	public SingleBuffers send(Role self, ESend a)
	{
		SingleBuffers copy = new SingleBuffers(this);
		copy.buffs.get(a.peer).put(self, a);
		return copy;
	}

	// Pre: canReceive, e.g., via SConfig.getFireable
	// Return an updated copy
	public SingleBuffers receive(Role self, ERecv a)
	{
		SingleBuffers copy = new SingleBuffers(this);
		copy.buffs.get(self).put(a.peer, null);
		return copy;
	}
	
  // Sync action
	// Pre: canRequest(r1, [[r2]]) and canAccept(r2, [[r1]]), where [[r]] is a matching action with peer r -- e.g., via via SConfig.getFireable
	// Return an updated copy
	public SingleBuffers connect(Role r1, Role r2)  // Role sides and message don't matter
	{
		SingleBuffers copy = new SingleBuffers(this);
		copy.connected.get(r1).put(r2, true);
		copy.connected.get(r2).put(r1, true);
		return copy;
	}

	// Pre: canDisconnect(self, d), e.g., via SConfig.via getFireable
	// Return an updated copy
	public SingleBuffers disconnect(Role self, EDisconnect d)
	{
		SingleBuffers copy = new SingleBuffers(this);
		copy.connected.get(self).put(d.peer, false);  // Didn't update buffs (cf. SConfig.getOrphanMessages)
		return copy;
	}

	// N.B. direction sensitive (viz., after some disconnect)
	public boolean isConnected(Role self, Role peer)
	{
		return this.connected.get(self).get(peer);
	}
	
	public boolean isEmpty(Role r)  // this.connected doesn't matter
	{
		return this.buffs.get(r).values().stream().allMatch(v -> v == null);
	}
	
	// Return a (deep) copy -- currently, checkEventualReception expects a modifiable return
	// N.B. hardcoded to capacity one
	public Map<Role, Map<Role, ESend>> getQueues()
	{
		return this.buffs.entrySet().stream().collect(Collectors.toMap(
				Entry::getKey,
				x -> new HashMap<>(x.getValue())));  // Collections.unmodifiableMap(x.getValue())
	}

	// N.B. hardcoded to capacity one
	public Map<Role, ESend> getQueue(Role r)
	{
		return Collections.unmodifiableMap(this.buffs.get(r));
	}

	@Override
	public final int hashCode()
	{
		int hash = 131;
		hash = 31 * hash + this.connected.hashCode();
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
		if (!(o instanceof SingleBuffers))
		{
			return false;
		}
		SingleBuffers b = (SingleBuffers) o;
		return this.connected.equals(b.connected) && this.buffs.equals(b.buffs);
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
