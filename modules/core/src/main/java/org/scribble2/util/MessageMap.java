package org.scribble2.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble2.sesstype.Message;
import org.scribble2.sesstype.name.Role;

// Mutable
public class MessageMap<T extends Message>
{
	private Map<Role, Map<Role, Set<T>>> map = new HashMap<>();
	
	//public final Set<Role> sources = map.keySet();

	public MessageMap()
	{
	}

	public MessageMap(MessageMap<T> map)
	{
		for (Role left : map.getLeftKeys())
		{
			for (Role right : map.map.get(left).keySet())
			{
				putMessages(left, right, map.getMessages(left, right));
			}
		}
	}
	
	public void merge(MessageMap<T> map)
	{
		for (Role left : map.getLeftKeys())
		{
			for (Role right : map.getRightKeys(left))
			{
				putMessages(left, right, map.getMessages(left, right));
			}
		}
	}

	public void putMessage(Role left, Role right, T msg)
	{
		addRolePair(left, right);
		this.map.get(left).get(right).add(msg);
	}

	public void putMessages(Role left, Role right, Set<T> msgs)
	{
		addRolePair(left, right);
		this.map.get(left).get(right).addAll(msgs);
	}
	
	public Set<Role> getLeftKeys()
	{
		return this.map.keySet();
	}

	public boolean containsLeftKey(Role left)
	{
		return this.map.containsKey(left);
	}

	/*public boolean containsRightKey(Role right)
	{
		return getAllRightKeys().contains(right);
	}*/
	
	public Set<Role> getRightKeys(Role left)
	{
		return this.map.get(left).keySet();
	}
	
	public Set<Role> getAllRightKeys()
	{
		Set<Role> rights = new HashSet<>();
		this.map.keySet().forEach((left) -> rights.addAll(this.map.get(left).keySet()));
		return rights;
	}
	
	public Set<T> getMessages(Role left, Role right)
	{
		return this.map.get(left).get(right);
	}

	public Set<T> getMessages(Role left)
	{
		Set<T> tmp = new HashSet<>();
		getRightKeys(left).forEach((right) -> tmp.addAll(getMessages(left, right)));
		return tmp;
	}

	public boolean containsRolePair(Role left, Role right)
	{
		return this.map.keySet().contains(left) && this.map.get(left).containsKey(right);
	}

	public boolean containsMessageSignature(Role left, Role right, Message msg)
	{
		return containsRolePair(left, right) && getMessages(left, right).contains(msg);
	}
	
	//public void clearAll()
	public void clear()
	{
		this.map.clear();
	}
	
	private void addRolePair(Role left, Role right)
	{
		if (!this.map.containsKey(left))
		{
			Map<Role, Set<T>> map = new HashMap<>();
			this.map.put(left, map);
			map.put(right, new HashSet<>());
		}
		else if (!this.map.get(left).containsKey(right))
		{
			this.map.get(left).put(right, new HashSet<>());
		}
	}
	
	@Override
	public String toString()
	{
		return this.map.toString();
	}
}
