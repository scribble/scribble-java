package org.scribble2.model.context;

import java.util.Map;
import java.util.Set;

import org.scribble2.sesstype.kind.ProtocolKind;
import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.Role;

// Make abstract with Global/Local extensions?
//public abstract class ProtocolDeclContext<N extends ProtocolName<K>, K extends ProtocolKind>
public abstract class ProtocolDeclContext<K extends ProtocolKind>
{
	// cache of dependencies, cleared on entering each root global protocol
	// protocol name is full name of global protocol dependencies
	//private Map<Role, Map<GProtocolName, Set<Role>>> dependencies;  // All the potential dependencies from this protocol decl as the root
	//private Map<Role, Map<N, Set<Role>>> dependencies;  // All the potential dependencies from this protocol decl as the root
	private Map<Role, Map<? extends ProtocolName<K>, Set<Role>>> dependencies;  // All the potential dependencies from this protocol decl as the root
			// FIXME: generalise to support locals
	
	//public ProtocolDeclContext(Map<Role, Map<GProtocolName, Set<Role>>> dependencies)
	//public ProtocolDeclContext(Map<Role, Map<N, Set<Role>>> dependencies)
	public ProtocolDeclContext(Map<Role, Map<? extends ProtocolName<K>, Set<Role>>> dependencies)
	{
		this.dependencies = dependencies;
	}
	
	//public Map<Role, Map<GProtocolName, Set<Role>>> getDependencies()
	//public Map<Role, Map<N, Set<Role>>> getDependencies()
	public Map<Role, Map<? extends ProtocolName<K>, Set<Role>>> getDependencies()
	{
		return this.dependencies;
	}
	
	/*//private static Map<Role, Map<? extends ProtocolName<Global>, Set<Role>>> cast(Map<Role, Map<GProtocolName, Set<Role>>> map)
	public static <N extends ProtocolName<X>, X extends ProtocolKind> Map<Role, Map<? extends ProtocolName<X>, Set<Role>>> cast(Map<Role, Map<N, Set<Role>>> map)
	{
		return map.keySet().stream().collect(Collectors.toMap((r) -> r, (r) -> castAux(map.get(r))));
	}

	private static <N extends ProtocolName<X>, X extends ProtocolKind> Map<? extends ProtocolName<X>, Set<Role>> castAux(Map<N, Set<Role>> map)
	{
		return map.keySet().stream().collect(Collectors.toMap((k) -> k, (k) -> map.get(k)));
	}*/
}
