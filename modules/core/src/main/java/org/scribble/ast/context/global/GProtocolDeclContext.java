package org.scribble.ast.context.global;

import java.util.Collections;
import java.util.Set;

import org.scribble.ast.context.ProtocolDeclContext;
import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.sesstype.name.Role;
import org.scribble.util.DependencyMap;

public class GProtocolDeclContext extends ProtocolDeclContext<Global>
{
	// cache of dependencies, cleared on entering each root global protocol
	// protocol name is full name of global protocol dependencies
	private final DependencyMap<GProtocolName> deps;  // All the potential dependencies from this protocol decl as the root
	
	public GProtocolDeclContext(DependencyMap<GProtocolName> deps)
	{
		this(Collections.emptySet(), deps);
	}

	protected GProtocolDeclContext(Set<Role> roles, DependencyMap<GProtocolName> deps)
	{
		super(roles);
		this.deps = new DependencyMap<>(deps);
	}
	
	@Override
	public GProtocolDeclContext copy()
	{
		return new GProtocolDeclContext(getRoleOccurrences(), getDependencyMap());
	}
	
	@Override
	public DependencyMap<GProtocolName> getDependencyMap()
	{
		return this.deps;
	}
}
