package org.scribble.ast.context;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.scribble.sesstype.kind.ProtocolKind;
import org.scribble.sesstype.name.ProtocolName;
import org.scribble.sesstype.name.Role;

public abstract class ProtocolDeclContext<K extends ProtocolKind>
{
	private Set<Role> roles;

	// cache of dependencies, cleared on entering each root global protocol
	// protocol name is full name of global/local protocol dependencies
	private DependencyMap<? extends ProtocolName<K>> deps;  
			// All the potential protocol dependencies from this *protocoldecl* as the root -- cf. ModuleContext deps from Module root
	
	protected ProtocolDeclContext(Set<Role> roles, DependencyMap<? extends ProtocolName<K>> deps)
	{
		this.roles = new HashSet<>(roles);
		this.deps = deps.clone();
	}
	
	// Subclass constructor should use the above copy constructor
	protected abstract ProtocolDeclContext<K> copy();
	
	public Set<Role> getRoleOccurrences()
	{
		return Collections.unmodifiableSet(this.roles);
	}

	public ProtocolDeclContext<K> setRoleOccurrences(Collection<Role> roles)
	{
		ProtocolDeclContext<K> copy = copy();
		copy.roles = new HashSet<>(roles);
		copy.deps = this.deps.clone();
		return copy;
	}

	public DependencyMap<? extends ProtocolName<K>> getDependencyMap()
	{
		// FIXME: returned deps view is mutable -- context should be immutable for del to be immutable
		return this.deps;
	}

	/*// Not needed: protocoldeclcontext always has dependencymap on first creation, by context building
	 public ProtocolDeclContext<K> setDependencyMap(DependencyMap<? extends ProtocolName<K>> deps)
	{
		ProtocolDeclContext<K> copy = copy();
		copy.roles = new HashSet<>(this.roles);
		copy.deps = new DependencyMap<>(deps);
		return copy;
	}*/
}
