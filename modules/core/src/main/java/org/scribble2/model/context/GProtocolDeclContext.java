package org.scribble2.model.context;

import org.scribble2.sesstype.kind.Global;
import org.scribble2.sesstype.name.GProtocolName;
import org.scribble2.util.DependencyMap;

// Make abstract with Global/Local extensions?
//public class GProtocolDeclContext extends ProtocolDeclContext<GProtocolName, Global>
public class GProtocolDeclContext extends ProtocolDeclContext<Global>
{
	// cache of dependencies, cleared on entering each root global protocol
	// protocol name is full name of global protocol dependencies
	//private Map<Role, Map<GProtocolName, Set<Role>>> dependencies;  // All the potential dependencies from this protocol decl as the root
			// FIXME: generalise to support locals
	private final DependencyMap<GProtocolName> deps;
	
	//public GProtocolDeclContext(Map<Role, Map<? extends ProtocolName<Global>, Set<Role>>> dependencies)
	//public GProtocolDeclContext(Map<Role, Map<GProtocolName, Set<Role>>> dependencies)
	public GProtocolDeclContext(DependencyMap<GProtocolName> deps)
	{
		//this.dependencies = dependencies;
		//super(cast(dependencies));
		//super(deps);
		this.deps = deps;
	}
	
	/*private static Map<Role, Map<? extends ProtocolName<Global>, Set<Role>>> cast(Map<Role, Map<GProtocolName, Set<Role>>> map)
	{
		return map.keySet().stream().collect(Collectors.toMap((r) -> r, (r) -> castAux(map.get(r))));
	}

	private static Map<? extends ProtocolName<Global>, Set<Role>> castAux(Map<GProtocolName, Set<Role>> map)
	{
		return map.keySet().stream().collect(Collectors.toMap((k) -> k, (k) -> map.get(k)));
	}*/
	
	@Override
	//public Map<Role, Map<GProtocolName, Set<Role>>> getDependencies()
	//public DependencyMap<GProtocolName, Global> getDependencies()
	//public DependencyMap<GProtocolName> getDependencies()
	public DependencyMap<GProtocolName> getDependencies()
	{
		return this.deps;
	}

	/*public Map<Role, Map<GProtocolName, Set<Role>>> getGlobalDependencies()
	{
		DependencyMap<? extends ProtocolName<Global>> map = getDependencies();
	}*/
}
