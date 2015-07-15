package org.scribble.ast.context.local;

import java.util.Collections;
import java.util.Set;

import org.scribble.ast.context.ProtocolDeclContext;
import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.util.DependencyMap;

public class LProtocolDeclContext extends ProtocolDeclContext<Local>
{
	// cache of dependencies, cleared on entering each root global protocol
	// protocol name is full name of global protocol dependencies
	private final DependencyMap<LProtocolName> deps;  // All the potential dependencies from this protocol decl as the root
	
	// FIXME: dep map setter
	public LProtocolDeclContext(DependencyMap<LProtocolName> deps)
	{
		this(Collections.emptySet(), deps);
	}

	protected LProtocolDeclContext(Set<Role> roles, DependencyMap<LProtocolName> deps)
	{
		super(roles);
		this.deps = new DependencyMap<>(deps);
	}
	
	@Override
	public LProtocolDeclContext copy()
	{
		return new LProtocolDeclContext(getRoleOccurrences(), getDependencyMap());
	}
	
	@Override
	public DependencyMap<LProtocolName> getDependencyMap()
	{
		return this.deps;
	}
}

