package org.scribble2.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.Role;

// Used for two purposes: one to encapsulate Map structure and add method; second to allow overriding the generic types (cf. nested Map generics)
//public class DependencyMap<N extends ProtocolName<K>, K extends ProtocolKind>
public class DependencyMap<N extends ProtocolName<? extends ProtocolKind>>  // Maybe better to parameterise on Kind only?
{
	private final Map<Role, Map<N, Set<Role>>> deps;// = new HashMap<>();  // All the potential dependencies from this protocol decl as the root

	public DependencyMap()
	{
		this.deps = new HashMap<>();
	}

	public DependencyMap(Map<Role, Map<N, Set<Role>>> deps)
	//public DependencyMap()
	{
		//this.deps = deps;  // FIXME: defensive copy
		this.deps = deps;
	}

	public void addProtocolDependency(Role self, N gpn, Role role)
	{
		Map<N, Set<Role>> tmp1 = this.deps.get(self);
		if (tmp1 == null)
		{
			tmp1 = new HashMap<>();
			this.deps.put(self, tmp1);
		}
		
		Set<Role> tmp2 = tmp1.get(gpn);
		if (tmp2 == null)
		{
			tmp2 = new HashSet<>();
			tmp1.put(gpn, tmp2);
		}
		tmp2.add(role);
	}
	
	public Map<Role, Map<N, Set<Role>>> getDependencies()
	{
		return this.deps;
	}
}
