package org.scribble2.model.context;

import org.scribble2.sesstype.kind.Local;
import org.scribble2.sesstype.name.LProtocolName;
import org.scribble2.util.DependencyMap;

// Make abstract with Global/Local extensions?
//public class LProtocolDeclContext extends ProtocolDeclContext<LProtocolName, Local>
public class LProtocolDeclContext extends ProtocolDeclContext<Local>
{
	// cache of dependencies, cleared on entering each root global protocol
	// protocol name is full name of global protocol dependencies
	//private Map<Role, Map<GProtocolName, Set<Role>>> dependencies;  // All the potential dependencies from this protocol decl as the root
			// FIXME: generalise to support locals
	//private final DependencyMap<LProtocolName> deps;
	
	//public LProtocolDeclContext(Map<Role, Map<LProtocolName, Set<Role>>> dependencies)
	//public LProtocolDeclContext(Map<Role, Map<? extends ProtocolName<Local>, Set<Role>>> dependencies)
	//public LProtocolDeclContext(Map<Role, Map<LProtocolName, Set<Role>>> dependencies)
	//public LProtocolDeclContext(DependencyMap<LProtocolName, Local> deps)
	public LProtocolDeclContext(DependencyMap<LProtocolName> deps)
	{
		//this.dependencies = dependencies;
		//super(cast(dependencies));
		super(deps);
		//this.deps = deps;
	}
	
	/*private static Map<Role, Map<? extends ProtocolName<Local>, Set<Role>>> cast(Map<Role, Map<LProtocolName, Set<Role>>> map)
	{
		return map.keySet().stream().collect(Collectors.toMap((r) -> r, (r) -> castAux(map.get(r))));
	}

	private static Map<? extends ProtocolName<Local>, Set<Role>> castAux(Map<LProtocolName, Set<Role>> map)
	{
		return map.keySet().stream().collect(Collectors.toMap((k) -> k, (k) -> map.get(k)));
	}*/
	
	@Override
	//public Map<Role, Map<GProtocolName, Set<Role>>> getDependencies()
	public DependencyMap<LProtocolName> getDependencies()
	{
		//return this.deps;
		return (DependencyMap<LProtocolName>) super.getDependencies();  // FIXME
	}
}
