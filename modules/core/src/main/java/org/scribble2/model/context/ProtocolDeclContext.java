package org.scribble2.model.context;

import java.util.Map;
import java.util.Set;

import org.scribble2.sesstype.name.ProtocolName;
import org.scribble2.sesstype.name.Role;

// Make abstract with Global/Local extensions?
public class ProtocolDeclContext
{
	// cache of dependencies, cleared on entering each root global protocol
	// protocol name is full name of global protocol dependencies
	private Map<Role, Map<ProtocolName, Set<Role>>> dependencies;  // All the potential dependencies from this protocol decl as the root
	
	public ProtocolDeclContext(Map<Role, Map<ProtocolName, Set<Role>>> dependencies)
	{
		this.dependencies = dependencies;
	}
	
	public Map<Role, Map<ProtocolName, Set<Role>>> getDependencies()
	{
		return this.dependencies;
	}
}
