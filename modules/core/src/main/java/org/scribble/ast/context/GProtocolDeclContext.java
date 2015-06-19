package org.scribble.ast.context;

import org.scribble.sesstype.kind.Global;
import org.scribble.sesstype.name.GProtocolName;
import org.scribble.util.DependencyMap;

public class GProtocolDeclContext implements ProtocolDeclContext<Global>
{
	// cache of dependencies, cleared on entering each root global protocol
	// protocol name is full name of global protocol dependencies
	private final DependencyMap<GProtocolName> deps;  // All the potential dependencies from this protocol decl as the root
	
	public GProtocolDeclContext(DependencyMap<GProtocolName> deps)
	{
		this.deps = new DependencyMap<>(deps);
	}
	
	@Override
	public DependencyMap<GProtocolName> getDependencyMap()
	{
		return this.deps;
	}
}
