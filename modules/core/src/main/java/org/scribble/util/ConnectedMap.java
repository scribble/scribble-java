package org.scribble.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.scribble.sesstype.name.Role;

// FIXME: factor out with MessageIdMap
// FIXME: cf, WFBuffers.connected
// Mutable
public class ConnectedMap
{
	// dest -> (src -> isConnected)
	private Map<Role, Map<Role, Boolean>> map = new HashMap<>();
	
	public ConnectedMap(Set<Role> roles, boolean implicit)
	{
		roles.forEach((k) -> 
		{
			HashMap<Role, Boolean> tmp = new HashMap<>();
			this.map.put(k, tmp);
			roles.forEach((k2) -> 
			{
				if (!k.equals(k2))
				{
					tmp.put(k2, implicit);
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
	
	public void connect(Role dest, Role src)
	{
		setConnected(dest, src, true);
	}

	public void disconnect(Role dest, Role src)
	{
		setConnected(dest, src, false);
	}

	public void setConnected(Role dest, Role src, boolean isConnected)
	{
		addRolePair(dest, src);
		this.map.get(dest).put(src, isConnected);
		addRolePair(src, dest);
		this.map.get(src).put(dest, isConnected);
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
		Map<Role, Boolean> tmp = this.map.get(dest);
		if (tmp != null)
		{
			Boolean b = tmp.get(src);
			return b != null && b;
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
			Map<Role, Boolean> map = new HashMap<>();
			this.map.put(dest, map);
			map.put(src, false);
		}
		else if (!this.map.get(dest).containsKey(src))
		{
			this.map.get(dest).put(src, false);
		}
	}
	
	@Override
	public String toString()
	{
		return this.map.toString();
	}
}
