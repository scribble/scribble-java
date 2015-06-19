package org.scribble.ast.context;

import org.scribble.sesstype.kind.Local;
import org.scribble.sesstype.name.LProtocolName;
import org.scribble.util.DependencyMap;

public class LProtocolDeclContext implements ProtocolDeclContext<Local>
{
	// cache of dependencies, cleared on entering each root global protocol
	// protocol name is full name of global protocol dependencies
	private final DependencyMap<LProtocolName> deps;  // All the potential dependencies from this protocol decl as the root
	
	public LProtocolDeclContext(DependencyMap<LProtocolName> deps)
	{
		this.deps = new DependencyMap<>(deps);
	}
	
	@Override
	public DependencyMap<LProtocolName> getDependencyMap()
	{
		return this.deps;
	}
}
