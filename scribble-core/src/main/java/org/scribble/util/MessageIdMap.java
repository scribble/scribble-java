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
package org.scribble.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble.sesstype.name.MessageId;
import org.scribble.sesstype.name.Role;

// Mutable
public class MessageIdMap
{
	// dest -> (src -> mids)
	private Map<Role, Map<Role, Set<MessageId<?>>>> map = new HashMap<>();
	
	public MessageIdMap()
	{

	}

	public MessageIdMap(MessageIdMap map)
	{
		for (Role dest : map.getDestinations())
		{
			for (Role src : map.map.get(dest).keySet())
			{
				putMessages(dest, src, map.getMessages(dest, src));
			}
		}
	}
	
	/*public void merge(MessageIdMap map)
	{
		for (Role dest : map.getDestinations())
		{
			for (Role src : map.getSources(dest))
			{
				putMessages(dest, src, map.getMessages(dest, src));
			}
		}
	}*/

	public void putMessage(Role dest, Role src, MessageId<?> msg)
	{
		addRolePair(dest, src);
		this.map.get(dest).get(src).add(msg);
	}

	/*public void removeMessage(Role dest, Role src, MessageId<?> msg)
	{
		addRolePair(dest, src);
		this.map.get(dest).get(src).remove(msg);
	}*/
	/*public void removeMessages(Role dest)
	{
		Map<Role, Set<MessageId<?>>> tmp = this.map.get(dest);
		for (Role r : tmp.keySet())
		{
			tmp.remove(r);
		}
	}*/

	public void putMessages(Role dest, Role src, Set<MessageId<?>> msgs)
	{
		addRolePair(dest, src);
		this.map.get(dest).get(src).addAll(msgs);
	}
	
	public Set<Role> getDestinations()
	{
		return this.map.keySet();
	}

	public boolean containsDestination(Role dest)
	{
		return this.map.containsKey(dest);
	}

	/*public boolean containsSource(Role src)
	{
		return getAllSources().contains(src);
	}*/
	
	public Set<Role> getSources(Role dest)
	{
		return this.map.containsKey(dest) ? this.map.get(dest).keySet() : Collections.emptySet();
	}
	
	/*public Set<Role> getAllSources()
	{
		Set<Role> srcs = new HashSet<>();
		this.map.keySet().forEach((dest) -> srcs.addAll(this.map.get(dest).keySet()));
		return srcs;
	}*/
	
	public Set<MessageId<?>> getMessages(Role dest, Role src)
	{
		return this.map.get(dest).get(src);
	}

	public Set<MessageId<?>> getMessages(Role dest)
	{
		Set<MessageId<?>> tmp = new HashSet<>();
		getSources(dest).forEach((src) -> tmp.addAll(getMessages(dest, src)));
		return tmp;
	}

	public boolean containsRolePair(Role dest, Role src)
	{
		return this.map.keySet().contains(dest) && this.map.get(dest).containsKey(src);
	}

	/*public boolean containsMessageSignature(Role dest, Role src, Message msg)
	{
		return containsRolePair(dest, src) && getMessages(dest, src).contains(msg);
	}*/
	
	public void clear()
	{
		this.map.clear();
	}
	
	private void addRolePair(Role dest, Role src)
	{
		if (!this.map.containsKey(dest))
		{
			Map<Role, Set<MessageId<?>>> map = new HashMap<>();
			this.map.put(dest, map);
			map.put(src, new HashSet<>());
		}
		else if (!this.map.get(dest).containsKey(src))
		{
			this.map.get(dest).put(src, new HashSet<>());
		}
	}
	
	@Override
	public String toString()
	{
		return this.map.toString();
	}
}
