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

import org.scribble.type.name.Role;

// FIXME: factor out with MessageIdMap
// FIXME: cf, WFBuffers.connected
// Mutable
public class ConnectedMap
{
	public static enum ConnectedStatus { TRUE, FALSE, AMBIG, }
	
	// dest -> (src -> isConnected)
	private Map<Role, Map<Role, ConnectedStatus>> map = new HashMap<>();
	
	private static ConnectedStatus convertStatus(boolean b)
	{
		return b ? ConnectedStatus.TRUE : ConnectedStatus.FALSE;
	}
	
	public ConnectedMap(Set<Role> roles, boolean implicit)
	{
		roles.forEach((k) -> 
		{
			HashMap<Role, ConnectedStatus> tmp = new HashMap<>();
			this.map.put(k, tmp);
			roles.forEach((k2) -> 
			{
				if (!k.equals(k2))
				{
					tmp.put(k2, convertStatus(implicit));
				}
			});
		});
	}

	public ConnectedMap(ConnectedMap map)
	{
		for (Role dest : map.getDestinations())
		{
			for (Role src : map.map.get(dest).keySet())
			{
				setConnected(dest, src, map.isConnected(dest, src));
			}
		}
	}
	
	// FIXME: refactor
	public ConnectedMap merge(ConnectedMap them)
	{
		Map<Role, Map<Role, ConnectedStatus>> ours = this.map;
		Map<Role, Map<Role, ConnectedStatus>> theirs = them.map;
		ConnectedMap res = new ConnectedMap(Collections.emptySet(), false);
		Set<Role> rs = new HashSet<>(ours.keySet());  // keySet should be sufficient
		rs.addAll(theirs.keySet());
		for (Role r1 : rs)
		{
			for (Role r2 : rs)
			{
				if (this.containsRolePair(r1, r2))
				{
					ConnectedStatus c1 = ours.get(r1).get(r2);
					if (them.containsRolePair(r1, r2))
					{
						ConnectedStatus c2 = theirs.get(r1).get(r2);
						ConnectedStatus c = (c1 == c2) ? c1 : ConnectedStatus.AMBIG;
						res.setConnected(r1, r2, c);
					}
					else
					{
						res.setConnected(r1, r2, c1);
					}
				}
				else if (them.containsRolePair(r1, r2))
				{
					ConnectedStatus c2 = theirs.get(r1).get(r2);
					res.setConnected(r1, r2, c2);
				}
			}
		}
		/*/
		ConnectedMap res = new ConnectedMap(this);
		for (Role r1 : them.getDestinations())
		{
			for (Role r2 : them.getSources(r1))
			{
				ConnectedStatus c2 = them.map.get(r1).get(r2);
				if (this.containsRolePair(r1, r2))
				{
					ConnectedStatus c1 = res.map.get(r1).get(r2);
					ConnectedStatus c = (c1 == c2) ? c1 : ConnectedStatus.AMBIG;
					res.setConnected(r1, r2, c);
				}
				else
				{
					res.setConnected(r1, r2, c2);
				}
			}
		}
		//*/
		return res;
	}
	
	public void connect(Role dest, Role src)
	{
		setConnected(dest, src, true);
	}

	public void disconnect(Role dest, Role src)
	{
		setConnected(dest, src, false);
	}

	protected void setConnected(Role dest, Role src, ConnectedStatus status)
	{
		addRolePair(dest, src);
		//this.map.get(dest).put(src, isConnected);
		ConnectedStatus b = status;
		this.map.get(dest).put(src, b);
		addRolePair(src, dest);
		this.map.get(src).put(dest, b);
	}

	public void setConnected(Role dest, Role src, boolean isConnected)
	{
		setConnected(dest, src, convertStatus(isConnected));
	}
	
	public Set<Role> getDestinations()
	{
		return this.map.keySet();
	}

	public boolean containsDestination(Role dest)
	{
		return this.map.containsKey(dest);
	}
	
	public Set<Role> getSources(Role dest)
	{
		return this.map.containsKey(dest) ? this.map.get(dest).keySet() : Collections.emptySet();
	}
	
	// FIXME: null guards not needed any more
	public boolean isConnected(Role dest, Role src)
	{
		//Map<Role, Boolean> tmp = this.map.get(dest);
		Map<Role, ConnectedStatus> tmp = this.map.get(dest);
		if (tmp != null)
		{
			/*Boolean b = tmp.get(src);
			return b != null && b;*/
			 ConnectedStatus b = tmp.get(src);
			 return b == ConnectedStatus.TRUE;
		}
		return false;
	}

	public boolean containsRolePair(Role dest, Role src)
	{
		return this.map.keySet().contains(dest) && this.map.get(dest).containsKey(src);
	}
	
	public void clear()
	{
		this.map.clear();
	}
	
	private void addRolePair(Role dest, Role src)
	{
		if (!this.map.containsKey(dest))
		{
			//Map<Role, Boolean> map = new HashMap<>();
			Map<Role, ConnectedStatus> map = new HashMap<>();
			this.map.put(dest, map);
			//map.put(src, false);
			map.put(src, ConnectedStatus.FALSE);
		}
		else if (!this.map.get(dest).containsKey(src))
		{
			//this.map.get(dest).put(src, false);
			//this.map.get(dest).put(src, false);
			this.map.get(dest).put(src, ConnectedStatus.FALSE);
		}
	}
	
	@Override
	public String toString()
	{
		return this.map.toString();
	}
	
	@Override
	public int hashCode()
	{
		int hash = 1033;
		hash = 31 * hash + super.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object o)
	{
		if (o == null)
		{
			return false;
		}
		if (!(o instanceof ConnectedMap))
		{
			return false;
		}
		return this.map.equals(((ConnectedMap) o).map);
	}
}
