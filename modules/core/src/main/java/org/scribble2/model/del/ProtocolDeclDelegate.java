package org.scribble2.model.del;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.Role;

public abstract class ProtocolDeclDelegate extends ModelDelegateBase
//public abstract class ProtocolDeclDelegate<T> extends ModelDelegateBase  // T should be the subclass extending ProtocolDeclDelegate
{
	private Map<Role, Map<ProtocolName, Set<Role>>> dependencies;  // All the potential dependencies from this protocol decl as the root

	protected ProtocolDeclDelegate()
	{
		this(new HashMap<>());
	}
	
	protected ProtocolDeclDelegate(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		this.dependencies = cloneDependencyMap(dependencies);
	}
	
	protected abstract ProtocolDeclDelegate reconstruct(Map<Role, Map<ProtocolName, Set<Role>>> dependencies);
	
	public ProtocolDeclDelegate setDependencies(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		return reconstruct(dependencies);
	}
	
	public Map<Role, Map<ProtocolName, Set<Role>>> getProtocolDependencies()
	{
		return this.dependencies;
	}

	private static Map<Role, Map<ProtocolName, Set<Role>>> cloneDependencyMap(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		Map<Role, Map<ProtocolName, Set<Role>>> clone = new HashMap<>();
		for (Role role : dependencies.keySet())
		{
			Map<ProtocolName, Set<Role>> vals = dependencies.get(role);
			Map<ProtocolName, Set<Role>> tmp1 = clone.get(role);
			if (tmp1 == null)
			{
				tmp1 = new HashMap<>();
				clone.put(role, tmp1);
			}
			for (ProtocolName pn : vals.keySet())
			{
				Set<Role> tmp2 = tmp1.get(pn);
				if (tmp2 == null)
				{
					tmp2 = new HashSet<>();
					tmp1.put(pn, tmp2);
				}
				tmp2.addAll(vals.get(pn));
			}
		}
		return clone;
	}
}
