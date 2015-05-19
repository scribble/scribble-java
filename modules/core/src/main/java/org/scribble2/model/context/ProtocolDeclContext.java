package org.scribble2.model.context;

import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.util.DependencyMap;

// Make abstract with Global/Local extensions?
//public abstract class ProtocolDeclContext<N extends ProtocolName<K>, K extends ProtocolKind>
//public abstract class ProtocolDeclContext<K extends ProtocolKind>
public interface ProtocolDeclContext<K extends ProtocolKind>
{
	/*// cache of dependencies, cleared on entering each root global protocol
	// protocol name is full name of global protocol dependencies
	//private Map<Role, Map<GProtocolName, Set<Role>>> dependencies;  // All the potential dependencies from this protocol decl as the root
	//private Map<Role, Map<N, Set<Role>>> dependencies;  // All the potential dependencies from this protocol decl as the root
	//private Map<Role, Map<? extends ProtocolName<K>, Set<Role>>> dependencies;  // All the potential dependencies from this protocol decl as the root
			// FIXME: generalise to support locals
	//protected DependencyMap<? extends ProtocolName<K>, K> deps;* /
	protected DependencyMap<? extends ProtocolName<K>> deps;
	
	//public ProtocolDeclContext(Map<Role, Map<GProtocolName, Set<Role>>> dependencies)
	//public ProtocolDeclContext(Map<Role, Map<N, Set<Role>>> dependencies)
	//public ProtocolDeclContext(Map<Role, Map<? extends ProtocolName<K>, Set<Role>>> deps)
	//public ProtocolDeclContext(DependencyMap<? extends ProtocolName<K>, K> deps)
	public ProtocolDeclContext(DependencyMap<? extends ProtocolName<K>> deps)
	//public ProtocolDeclContext(Map<Role, Map<ProtocolName<? extends ProtocolKind>, Set<Role>>> deps)
	{
		this.deps = deps;
	}
	
	//public Map<Role, Map<GProtocolName, Set<Role>>> getDependencies()
	//public Map<Role, Map<N, Set<Role>>> getDependencies()
	//public Map<Role, Map<? extends ProtocolName<K>, Set<Role>>> getDependencies()
	//public DependencyMap<? extends ProtocolName<K>, K> getDependencies()
	//public DependencyMap<? extends ProtocolName<K>, K> getDependencies()
	public DependencyMap<? extends ProtocolName<K>> getDependencies()
	{
		return this.deps;
	}
	
	/* //private static Map<Role, Map<? extends ProtocolName<Global>, Set<Role>>> cast(Map<Role, Map<GProtocolName, Set<Role>>> map)
	public static <N extends ProtocolName<X>, X extends ProtocolKind> Map<Role, Map<? extends ProtocolName<X>, Set<Role>>> cast(Map<Role, Map<N, Set<Role>>> map)
	{
		return map.keySet().stream().collect(Collectors.toMap((r) -> r, (r) -> castAux(map.get(r))));
	}

	private static <N extends ProtocolName<X>, X extends ProtocolKind> Map<? extends ProtocolName<X>, Set<Role>> castAux(Map<N, Set<Role>> map)
	{
		return map.keySet().stream().collect(Collectors.toMap((k) -> k, (k) -> map.get(k)));
	}*/
	
	//public abstract Map<Role, Map<? extends ProtocolName<K>, Set<Role>>> getDependencies();*/
	DependencyMap<? extends ProtocolName<K>> getDependencyMap();
	//Map<Role, Map<? extends ProtocolName<K>, Set<Role>>> getDependencies();
}
