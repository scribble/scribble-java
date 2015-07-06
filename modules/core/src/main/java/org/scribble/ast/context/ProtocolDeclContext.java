package org.scribble.ast.context;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.util.DependencyMap;

public abstract class ProtocolDeclContext<K extends ProtocolKind>
{
	private Set<Role> roles;
	
	protected ProtocolDeclContext(Set<Role> roles)
	{
		this.roles = new HashSet<>(roles);
	}
	
	protected abstract ProtocolDeclContext<K> copy();
	
	public Set<Role> getRoleOccurrences()
	{
		return this.roles;
	}

	public ProtocolDeclContext<K> setRoleOccurrences(Collection<Role> roles)
	{
		ProtocolDeclContext<K> copy = copy();
		copy.roles = new HashSet<>(roles);
		return copy;
	}

	public abstract DependencyMap<? extends ProtocolName<K>> getDependencyMap();
}
